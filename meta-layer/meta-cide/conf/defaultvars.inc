python() {
    import bbdefaultvars
    bbdefaultvars.bbdefaultvars(bb, d);
}

DEPENDS = ""
DEPLOY_DIR = "${TMPDIR}/deploy"
DEPLOY_DIR_IMAGE = "${DEPLOY_DIR}/images"
FILESDIR = "${@bb.utils.which(d.getVar('FILESPATH', True), '.')}"
FILESPATH = "${FILE_DIRNAME}/${PF}:${FILE_DIRNAME}/${P}:${FILE_DIRNAME}/${PN}:${FILE_DIRNAME}/files:${FILE_DIRNAME}"
FILE_DIRNAME = "${@os.path.dirname(d.getVar('FILE', False))}"
IMAGE_ROOTFS = "${TMPDIR}/rootfs"
OVERRIDES = "local:${MACHINE}:${TARGET_OS}:${TARGET_ARCH}"
P = "${PN}-${PV}"
PERSISTENT_DIR = "${TMPDIR}/cache"
PF = "${PN}-${PV}-${PR}"
PN = "${@bb.parse.vars_from_file(d.getVar('FILE', False),d)[0] or 'defaultpkgname'}"
PR = "${@bb.parse.vars_from_file(d.getVar('FILE', False),d)[2] or 'r0'}"
PROVIDES = ""
PV = "${@bb.parse.vars_from_file(d.getVar('FILE', False),d)[1] or '1.0'}"
SRC_URI = "file://${FILE}"
STAMP = "${TMPDIR}/stamps/${PF}"
T = "${WORKDIR}/temp"
TARGET_ARCH = "${BUILD_ARCH}"
TMPDIR = "${TOPDIR}/tmp"
UPDATECOMMAND = ""
WORKDIR = "${TMPDIR}/work/${PF}"
PERSISTENT_DIR = "${TMPDIR}/cache"

CACHE = "${TMPDIR}/cache/default"

ROOT_DIR            = "${@os.path.normpath('${TOPDIR}/../..')}"
BUILDTMP_DIR        = "${TMPDIR}/globalbuild-tmp/${CURRENT_TARGET}"
BUILDTOOLS_ROOTFS   = "${TMPDIR}/buildtools-rootfs"
BUILD_DIR           = "${WORKDIR}/build/${CURRENT_TARGET}"
CONFIG_DIR          = "${ROOT_DIR}/configs"
DEPLOY_DIR          = "${TMPDIR}/deploy/${CURRENT_TARGET}"
IMAGE_ROOTFS        = "${TMPDIR}/rootfs/${CURRENT_TARGET}"
INSTALL_DIR         = "${WORKDIR}/install/${CURRENT_TARGET}"
MODULE_DIR          = "${ROOT_DIR}/modules"
SOURCE_DIR          = "${MODULE_DIR}/${MODULE}"
PREFIX_BASE         = "/opt/cide"

S = "${SOURCE_DIR}"
B = "${BUILD_DIR}"
SRC_URI = "file://${SOURCE_DIR}"

SKIP_TEST = 'True'

#BBCLASSEXTEND += "aarch64-unknown-linux-gnu linux_mingw64"
BBCLASSEXTEND = "x86_64-unknown-linux-gnu aarch64-unknown-linux-gnu"
