SUMMARY = "util-linux"
LICENSE = "CLOSED"
MODULE = "base/util-linux"
SRCPV = "1"

inherit simplemeson encapsulate

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, "meson")
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

NOWARN = "\
    -Wno-implicit-function-declaration \
    -Wno-nested-externs \
"

MESON_OPTIONS_FEATURES = "\
    -Dtinfo=disabled \
    -Dncursesw=disabled \
    -Dncurses=disabled \
    -Dslang=disabled \
    -Dcryptsetup=disabled \
    -Dcryptsetup-dlopen=false \
    -Dzlib=disabled \
    -Dreadline=disabled \
    -Dnls=disabled \
    -Dlibutil=disabled \
    -Dlibutempter=disabled \
    -Dlibpcre2-posix=disabled \
    -Dlibuser=disabled \
    -Dselinux=disabled \
    -Daudit=disabled \
    -Dsmack=disabled \
    -Dmagic=disabled \
    -Deconf=disabled \
    -Dsystemd=disabled \
    -Dsysvinit=disabled \
    -Dbtrfs=disabled \
    -Dwidechar=disabled \
    -Dtranslate-docs=disabled \
"

MESON_OPTIONS_BUILD_FEATURES = "\
    -Dbuild-python=disabled \
    -Dbuild-libblkid=disabled \
    -Dbuild-libuuid=enabled \
    -Dbuild-liblastlog2=disabled \
    -Dbuild-pam-lastlog2=disabled \
    -Dbuild-libmount=disabled \
    -Dbuild-libsmartcols=disabled \
    -Dbuild-libfdisk=disabled \
    -Dbuild-fdisks=disabled \
    -Dbuild-mount=disabled \
    -Dbuild-swapon=disabled \
    -Dbuild-swapoff=disabled \
    -Dbuild-chcpu=disabled \
    -Dbuild-losetup=disabled \
    -Dbuild-zramctl=disabled \
    -Dbuild-lsns=disabled \
    -Dbuild-mkfs=disabled \
    -Dbuild-fsck=disabled \
    -Dbuild-partx=disabled \
    -Dbuild-script=disabled \
    -Dbuild-scriptutils=disabled \
    -Dbuild-bits=disabled \
    -Dbuild-col=disabled \
    -Dbuild-colcrt=disabled \
    -Dbuild-colrm=disabled \
    -Dbuild-rev=disabled \
    -Dbuild-uuidd=enabled \
    -Dbuild-choom=disabled \
    -Dbuild-isosize=disabled \
    -Dbuild-waitpid=disabled \
    -Dbuild-wipefs=disabled \
    -Dbuild-mountpoint=disabled \
    -Dbuild-fallocate=disabled \
    -Dbuild-unshare=disabled \
    -Dbuild-nsenter=disabled \
    -Dbuild-setpriv=disabled \
    -Dbuild-hardlink=disabled \
    -Dbuild-eject=disabled \
    -Dbuild-agetty=disabled \
    -Dbuild-cramfs=disabled \
    -Dbuild-bfs=disabled \
    -Dbuild-minix=disabled \
    -Dbuild-fdformat=disabled \
    -Dbuild-blockdev=disabled \
    -Dbuild-hwclock=disabled \
    -Dbuild-lslogins=disabled \
    -Dbuild-wdctl=disabled \
    -Dbuild-cal=disabled \
    -Dbuild-logger=disabled \
    -Dbuild-look=disabled \
    -Dbuild-mcookie=disabled \
    -Dbuild-namei=disabled \
    -Dbuild-whereis=disabled \
    -Dbuild-lsblk=disabled \
    -Dbuild-lslocks=disabled \
    -Dbuild-findmnt=disabled \
    -Dbuild-lsfd=disabled \
    -Dbuild-switch_root=disabled \
    -Dbuild-pivot_root=disabled \
    -Dbuild-flock=disabled \
    -Dbuild-lsmem=disabled \
    -Dbuild-lsirq=disabled \
    -Dbuild-irqtop=disabled \
    -Dbuild-chmem=disabled \
    -Dbuild-ipcmk=disabled \
    -Dbuild-ipcrm=disabled \
    -Dbuild-ipcs=disabled \
    -Dbuild-rfkill=disabled \
    -Dbuild-tunelp=disabled \
    -Dbuild-fstrim=disabled \
    -Dbuild-dmesg=disabled \
    -Dbuild-ctrlaltdel=disabled \
    -Dbuild-exch=disabled \
    -Dbuild-fsfreeze=disabled \
    -Dbuild-blkdiscard=disabled \
    -Dbuild-blkzone=disabled \
    -Dbuild-blkpr=disabled \
    -Dbuild-ldattach=disabled \
    -Dbuild-rtcwake=disabled \
    -Dbuild-setarch=disabled \
    -Dbuild-kill=disabled \
    -Dbuild-last=disabled \
    -Dbuild-utmpdump=disabled \
    -Dbuild-line=disabled \
    -Dbuild-mesg=disabled \
    -Dbuild-raw=disabled \
    -Dbuild-rename=disabled \
    -Dbuild-vipw=disabled \
    -Dbuild-newgrp=disabled \
    -Dbuild-chfn-chsh=disabled \
    -Dbuild-login=disabled \
    -Dbuild-nologin=disabled \
    -Dbuild-sulogin=disabled \
    -Dbuild-su=disabled \
    -Dbuild-runuser=disabled \
    -Dbuild-ul=disabled \
    -Dbuild-more=disabled \
    -Dbuild-pg=disabled \
    -Dbuild-pipesz=disabled \
    -Dbuild-fadvise=disabled \
    -Dbuild-enosys=disabled \
    -Dbuild-lsclocks=disabled \
    -Dbuild-getopt=disabled \
    -Dbuild-setterm=disabled \
    -Dbuild-schedutils=disabled \
    -Dbuild-wall=disabled \
    -Dbuild-write=disabled \
    -Dbuild-bash-completion=disabled \
    -Dbuild-pylibmount=disabled \
    -Dbuild-hexdump=disabled \
    -Dbuild-findfs=disabled \
"

MESON_OPTIONS = "${MESON_OPTIONS_BUILD_FEATURES} ${MESON_OPTIONS_FEATURES}"

