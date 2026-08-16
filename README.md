# CIDE

### Historically: Console IDE

CIDE is a modular software build environment for building complete software stacks independently of the host system.

It is **not a Linux distribution** and it does not try to build an operating system.

Instead, CIDE focuses on the software running on top of an existing operating system — while keeping platform- and hardware-specific dependencies at clearly defined boundaries.

> **Build the software, not the operating system.**

---

## Current Status

> [!WARNING]
> CIDE is currently a development project and is not yet buildable from a fresh checkout on an arbitrary machine.

The current build requires an external **crosstool-ng** installation that is maintained outside this repository and is currently only available in the author's development environment.

Therefore, cloning the repository alone is not sufficient to reproduce the build.

Making the complete build environment self-contained and reproducible is an important future goal.

The current state should therefore be considered **experimental**.

---

## Why CIDE?

The project started with a simple problem:

I wanted to build a cross-development environment without having to build an entire Linux system.

When building software for another target, the host distribution often does not provide all required development packages — or provides versions that are not suitable for the target environment.

For example, a library may need to be built against a specific target libc rather than the libc of the host system.

The goal was therefore to build only what was actually needed:

```text
                Host system
                    │
                    │
                    ▼
             ┌─────────────┐
             │    CIDE     │
             │             │
             │  libraries  │
             │  tools      │
             │  compilers  │
             │  applications│
             └──────┬──────┘
                    │
                    ▼
             Target platform
```

CIDE does not replace the host operating system.

It builds the software stack that sits on top of it.

---

## The Core Idea

CIDE tries to keep dependencies as small and explicit as possible.

A module should depend on the minimum environment required to build it, rather than implicitly depending on whatever happens to be installed on the host system.

The boundary is not necessarily libc.

Depending on the software, the relevant boundary might be:

* libc / runtime ABI
* graphics APIs such as Wayland
* GPU APIs such as CUDA
* other platform-specific interfaces

The important principle is:

> **Platform and hardware dependencies should remain at the boundary instead of propagating through the entire software stack.**

For example:

```text
Application
     │
     ├── Wayland ───── graphics boundary
     │
     ├── CUDA ──────── GPU boundary
     │
     └── libc ───────── runtime boundary
```

This allows the software above these boundaries to remain as hardware- and platform-independent as reasonably possible.

---

## Not a Linux Distribution

CIDE deliberately does not attempt to provide:

* a Linux distribution
* a kernel
* a root filesystem
* a package repository
* a complete operating system

If the operating system already provides something, CIDE does not rebuild it just because an application depends on it.

**The operating system is the platform.**

**CIDE builds the software.**

---

## From Console IDE to Build Environment

The name CIDE originally stood for **Console IDE**.

The initial goal was to create a complete console-based development environment around Neovim.

Building Neovim and its dependencies turned out to be a much bigger problem than expected. The dependency graph grew, cross-compilation became important, and the project needed a more structured way of describing and building software.

CIDE gradually evolved from:

```text
Console IDE
     │
     ▼
Cross-development environment
     │
     ▼
Modular software build system
     │
     ▼
General-purpose software stack
```

The Console IDE is therefore the origin of the name, not the current scope of the project.

Today, the focus is primarily on PC software and larger software stacks, including:

* development tools
* graphics
* multimedia
* NLP/OCR
* increasingly, AI/LLM software

---

## AI and LLM

AI and LLM software adds another interesting dimension to the original idea.

Modern AI software often combines:

* large native dependency graphs
* C/C++ libraries
* numerical libraries
* CPU-specific optimizations
* GPU acceleration
* CUDA and other hardware-specific interfaces

CIDE is intended to handle these dependencies without making the entire software stack dependent on a specific hardware implementation.

Hardware-specific functionality is treated as another boundary in the dependency graph.

---

## Build System

CIDE uses **BitBake** as its underlying build engine.

The project provides its own module definitions and build metadata, with **Just** as the main command-line interface.

The repository is organized roughly around:

```text
cide/
├── config/
├── modules/
├── meta-layer/
├── modulerefs/
├── scripts/
└── Justfile
```

The `modules/` directory contains the software definitions.

The BitBake metadata and CIDE-specific configuration live in `meta-layer/`.

The build configuration contains multiple target environments, including:

* native
* ARM
* MinGW
* ESP32-related targets

The default build builds the complete configured target set. Selecting a single target is currently not a supported or tested workflow.

---

## Building

The intended workflow is:

```bash
git clone --recurse-submodules https://github.com/witold-k/cide.git
cd cide

just setup
just fetch
just build
```

However, the commands above currently assume that the required external crosstool-ng installation is already available.

The build produces the configured software stack rather than a complete operating system.

---

## What CIDE Tries to Achieve

CIDE is built around a few simple principles:

* **Build software, not an operating system.**
* **Keep dependencies explicit.**
* **Keep dependencies minimal.**
* **Do not make the host distribution part of the software dependency graph unnecessarily.**
* **Keep platform-specific functionality at explicit boundaries.**
* **Keep hardware-specific functionality isolated.**
* **Build complex software stacks in a controlled environment.**

The long-term goal is a reproducible build environment where the complete toolchain and build infrastructure can be bootstrapped from the project itself.

---

## License

CIDE is licensed under the **Apache License 2.0**.

See [`LICENSE`](LICENSE) for the full license text.

