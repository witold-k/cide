user_name := "$USER"

current_location := justfile()
current_dir      := justfile_directory()
environment_json := current_dir + "/config/environment.json"
module_name      := file_name(current_dir)

buildscripts_dir := current_dir + "/buildscripts"

bash_exe         := buildscripts_dir + "/bash"
python_exe       := buildscripts_dir + "/python/bin"

scripts_dir      := current_dir + "/scripts"
build_dir        := current_dir + "/meta-layer/build"

# -----------------------------------------------------------------------------

default: build

# -----------------------------------------------------------------------------

info:
	@echo {{current_dir}}

setupscripts:
	@python3 {{scripts_dir}}/setupscripts.py

patchbitbake:
	@bash {{buildscripts_dir}}/bitbake/patchbitbake.sh {{environment_json}}

fetchcompiler:
	@python3 {{python_exe}}/fetchcompiler.py {{environment_json}}

setup: fetchcompiler patchbitbake

fetchgroup group:
	@python3 {{python_exe}}/fetchercmd.py fetch \
		{{current_dir}}/modules/{{group}}

fetch:
	@python3 {{python_exe}}/fetchercmd.py fetch \
		{{current_dir}}/modules/audioformat \
		{{current_dir}}/modules/base \
		{{current_dir}}/modules/compress \
		{{current_dir}}/modules/crypt \
		{{current_dir}}/modules/documents \
		{{current_dir}}/modules/graphicimage \
		{{current_dir}}/modules/graphics \
		{{current_dir}}/modules/ide \
		{{current_dir}}/modules/input \
		{{current_dir}}/modules/llm \
		{{current_dir}}/modules/math \
		{{current_dir}}/modules/net \
		{{current_dir}}/modules/nlp \
		{{current_dir}}/modules/ocr \
		{{current_dir}}/modules/sdr \
		{{current_dir}}/modules/soundsubsystem \
		{{current_dir}}/modules/diffusion \

save:
	@python3 {{python_exe}}/fetchercmd.py save \
		{{current_dir}}/modules/audioformat \
		{{current_dir}}/modules/base \
		{{current_dir}}/modules/compress \
		{{current_dir}}/modules/crypt \
		{{current_dir}}/modules/documents \
		{{current_dir}}/modules/graphicimage \
		{{current_dir}}/modules/graphics \
		{{current_dir}}/modules/ide \
		{{current_dir}}/modules/input \
		{{current_dir}}/modules/llm \
		{{current_dir}}/modules/math \
		{{current_dir}}/modules/net \
		{{current_dir}}/modules/nlp \
		{{current_dir}}/modules/ocr \
		{{current_dir}}/modules/sdr \
		{{current_dir}}/modules/soundsubsystem \
		{{current_dir}}/modules/diffusion \


# -----------------------------------------------------------------------------

clean:
	@python3 {{python_exe}}/build.py bbclean {{environment_json}}

mrproper:
	@bash {{scripts_dir}}/mrproper.sh
	@python3 {{python_exe}}/build.py bbclean {{environment_json}}

tclean target:
	@python3 {{python_exe}}/build.py bbclean {{environment_json}} {{target}}

touch:
	@find {{build_dir}} -iname check*.txt | xargs rm

update: clean
	@python3 {{python_exe}}/fetchercmd.py update \
		{{current_dir}}/modules/audioformat \
		{{current_dir}}/modules/base \
		{{current_dir}}/modules/compress \
		{{current_dir}}/modules/crypt \
		{{current_dir}}/modules/documents \
		{{current_dir}}/modules/graphicimage \
		{{current_dir}}/modules/graphics \
		{{current_dir}}/modules/ide \
		{{current_dir}}/modules/input \
		{{current_dir}}/modules/llm \
		{{current_dir}}/modules/math \
		{{current_dir}}/modules/net \
		{{current_dir}}/modules/nlp \
		{{current_dir}}/modules/ocr \
		{{current_dir}}/modules/sdr \
		{{current_dir}}/modules/soundsubsystem \
		{{current_dir}}/modules/diffusion \


cleanlog:
	@bash {{scripts_dir}}/cleanlog.sh

# -----------------------------------------------------------------------------

build: cleanlog setup
	@bash {{bash_exe}}/rundocker_pybuild.sh {{current_dir}} bbbuild {{environment_json}} {{build_dir}} world

target target:
	@bash {{bash_exe}}/rundocker_pybuild.sh {{current_dir}} bbbuild {{environment_json}} {{build_dir}} {{target}}-x86_64-unknown-linux-gnu
	@bash {{bash_exe}}/rundocker_pybuild.sh {{current_dir}} bbbuild {{environment_json}} {{build_dir}} {{target}}-aarch64-unknown-linux-gnu

native target:
	@bash {{bash_exe}}/rundocker_pybuild.sh {{current_dir}} bbbuild {{environment_json}} {{build_dir}} {{target}}-x86_64-unknown-linux-gnu

cross target:
	@bash {{bash_exe}}/rundocker_pybuild.sh {{current_dir}} bbbuild {{environment_json}} {{build_dir}} {{target}}-aarch64-unknown-linux-gnu

cbuild: cleanlog setup
	@bash {{bash_exe}}/rundocker_pybuild.sh {{current_dir}} bbkbuild {{environment_json}} {{build_dir}} world

vbuild: cleanlog setup
	@bash {{bash_exe}}/rundocker_pybuild.sh {{current_dir}} bbvbuild {{environment_json}} {{build_dir}} world

vtarget target:
	@bash {{bash_exe}}/rundocker_pybuild.sh {{current_dir}} bbvbuild {{environment_json}} {{build_dir}} {{target}}-x86_64-unknown-linux-gnu
	@bash {{bash_exe}}/rundocker_pybuild.sh {{current_dir}} bbvbuild {{environment_json}} {{build_dir}} {{target}}-aarch64-unknown-linux-gnu

encapsulate:
    @bash {{bash_exe}}/rundocker_pybuild.sh {{current_dir}} bbfbuild {{environment_json}} {{build_dir}} encapsulate

# -----------------------------------------------------------------------------

libtasn1:
	@bash {{bash_exe}}/rundocker_pybuild.sh {{current_dir}} bbbuild {{environment_json}} {{build_dir}} libtasn1-x86_64-unknown-linux-gnu

libidn2:
	@bash {{bash_exe}}/rundocker_pybuild.sh {{current_dir}} bbbuild {{environment_json}} {{build_dir}} libidn2-x86_64-unknown-linux-gnu

wayland:
	@bash {{bash_exe}}/rundocker_pybuild.sh {{current_dir}} bbbuild {{environment_json}} {{build_dir}} wayland-x86_64-unknown-linux-gnu

minnative: cleanlog setup libtasn1 libidn2 wayland

# -----------------------------------------------------------------------------

install:
	@bash {{scripts_dir}}/install.sh

#run: target
#    ~/svn/cide/meta-layer/build/tmp/rootfs/x86_64-unknown-linux-gnu/wrapper/bin/{{target}}

login:
	@bash {{bash_exe}}/rundocker_pybuild.sh {{current_dir}} shell {{environment_json}}

dlogin:
	@bash {{bash_exe}}/rundocker_pybuild.sh {{current_dir}} dshell {{environment_json}}

# - secret repository

keygen:
	gpg --full-generate-key

keyexport:
	gpg --armor --export -a Witold > gpg.pub
	gpg --armor --export-secret-keys -a Witold > gpg.priv

keyimport:
	gpg --import gpg.pub
	gpg --import gpg.priv

# github.com/spwhitton/git-remote-gcrypt
addsecret:
	git remote add cryptremote gcrypt::rsync://vsrv/home/{{user_name}}/depots/{{module_name}}

removesecret:
	git remote remove cryptremote

pushsecret:
	git push cryptremote master

pullsecret:
	git pull cryptremote master

helpsecret:
    gpg --list-keys

initpullsecret: helpsecret removesecret addsecret pushsecret

