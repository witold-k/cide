# CIDE Redesign – Architektur und aktueller Stand

Wir wollen CIDE vereinfachen und BitBake möglichst vollständig entfernen.
Ziel ist **kein neues Yocto**, sondern ein überschaubares, modulares
Buildsystem für Pakete und unterschiedliche Zielarchitekturen.

## Grundlegende Architektur

1. **Fetcher:** Existiert bereits, muss aber noch überarbeitet werden.
   Beschafft Quellen.
2. **Dependency-Resolver:** Liest explizite JSON-Abhängigkeiten, erstellt
   den Dependency-Graphen und entscheidet, welche Pakete gebaut werden.
3. **Builder:** Führt Python-Recipes aus und verwendet möglichst die
   vorhandenen `cbuildconfigure_classes` aus `buildscripts`.
   Die eigentliche Arbeit erledigen CMake, Ninja, Meson, Make usw.
4. **Paketartefakte:** Jeder Paket-Build erzeugt ein definiertes Ergebnis,
   beispielsweise `paket.tar`, aus seinem Staging-Verzeichnis.
5. **Installer:** Eigenständiges Werkzeug, das fertige Paketartefakte
   inkrementell in ein gemeinsames Rootfs installiert.

## Wichtige Entscheidungen

- **BitBake soll entfallen.**
- Meson wurde als übergeordneter Orchestrator erwogen, aber vermutlich
  nicht benötigt.
- Python-Dateien ersetzen `.bb`-Recipes.
- Abhängigkeiten werden ausdrücklich in **JSON** gespeichert, damit
  externe Werkzeuge sie ohne Python-Ausführung auswerten können.
- Native und Target-Abhängigkeiten müssen unterschieden werden.
- Der Resolver liefert nur die tatsächlich auszuführenden Pakete
  beziehungsweise einen entsprechenden Build-Plan.
- Der Installer kennt die verwendeten Buildsysteme nicht. Er verarbeitet
  ausschließlich fertige Pakete.
- Der Installer soll **inkrementell** arbeiten, nicht jedes Mal das
  gesamte Rootfs neu erzeugen.
- Die erste Version muss nicht perfekt sein. Einfachheit und
  Nachvollziehbarkeit haben Vorrang.
- Lieber gelegentlich zu viel bauen als notwendige Builds übersehen.
- Die vorhandenen Buildsysteme übernehmen weiterhin die inkrementelle
  Kompilierung innerhalb eines Pakets.
- Zunächst keine unnötige Komplexität durch parallele Paket-Builds,
  Transaktionen oder ausgefeilte Optimierung.

## Änderungsdetektion – noch offen

Diskutierter Ansatz:

`Quellen + Konfiguration + Abhängigkeiten → paket.tar`

Der Resolver prüft, ob ein Paketartefakt noch zu seinen Eingaben passt.

Mögliche Kriterien:

- Zeitstempel und Hashes der Quellen
- Änderungen an der Build-Konfiguration
- Änderungen an Abhängigkeiten
- Existenz und Integrität des Paketartefakts

Eine wichtige Unterscheidung: Der Hash des Archivs allein beweist nicht,
dass es zu den aktuellen Quellen passt. Dafür ist ein Fingerprint der
Eingaben erforderlich.

Wird eine Abhängigkeit neu gebaut, darf zunächst auch das abhängige Paket
erneut gebaut werden, selbst wenn dies gelegentlich unnötig ist.

**Die genaue Dirty-Detection-Strategie ist noch nicht entschieden.**

## Vorhandener Code

GitHub-Repositories:

- https://github.com/witold-k/cide
- https://github.com/witold-k/buildscripts

In `buildscripts` existieren bereits die `cbuildconfigure_classes` für
die Konfiguration verschiedener Buildsysteme. Diese sollen möglichst
wiederverwendet und von BitBake entkoppelt werden.

Die bisherige Architektur enthält unter anderem:

- `base.bbclass`
- `simplecmake.bbclass`
- `simpleninja.bbclass`
- `populate.bbclass`
- `dirtycheck.py`

Die bisherige `populate`-Funktionalität ist ein möglicher Ausgangspunkt
für den Installer.

## Erkenntnisse aus der bisherigen CIDE-Untersuchung

Ein Neovim-Build hatte zeitweise fehlende Lua-Dateien im installierten
Rootfs. Nach dem Bereinigen und erneuten Bauen funktionierte die
Installation wieder.

**Die eigentliche Ursache wurde nicht geklärt.** Insbesondere darf der
Fehler nicht einfach auf die DirtyCheck-Logik zurückgeführt werden,
da er ursprünglich bei einem sauberen Build auftrat.

Beim Code-Review wurden unabhängig davon mögliche Schwächen erkannt:

- DirtyCheck prüft Eingaben, aber nicht vollständig die erzeugten
  Artefakte.
- Fehlende Staging-Verzeichnisse können beim Populate unbemerkt bleiben.
- Persistente Installationsverzeichnisse können veraltete Dateien
  enthalten.
- Fehlgeschlagene oder unvollständige Installationen sollten nicht
  als erfolgreich gelten.

Der doppelte Versionspfad unter `/opt/cide` ist beabsichtigt und
kein Fehler.

## Arbeitsweise

Wir befinden uns derzeit in der **Architektur- und Konzeptphase**.

Noch keine vorschnelle Implementierung oder Git-Änderungen.
Erst die Schnittstellen und das Datenmodell klären, dann schrittweise
umbauen und testen.

Der Nutzer möchte Git-Merges selbst durchführen.

**Nächster möglicher Schritt:** Das minimale Datenmodell für
JSON-Abhängigkeiten, Python-Recipes, Build-Kontext und Paketartefakte
entwerfen. Dabei prüfen, welche bestehenden `buildscripts`-Komponenten
unmittelbar wiederverwendbar sind.
