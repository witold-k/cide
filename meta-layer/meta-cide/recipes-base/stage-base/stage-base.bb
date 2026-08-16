inherit stage

SUMMARY = "wait till recipes-base is finished"
LICENSE = "CLOSED"
MODULE = "(none)"
SRCPV = "1"

DEPEND_LIST = " \
    clipper2 \
    cmph \
    curl \
    e2fsprogs \
    gmp \
    gnutls \
    googletest \
    http-parser \
    jsonc \
    libcpptest \
    libev4 \
    libevent \
    libexpat \
    libiio \
    libmpack \
    libmpack-lua \
    libncurses \
    libpng \
    libreadline \
    libsixel \
    libtasn1 \
    libtermkey \
    libubox \
    libubus \
    libunistring \
    liburiparser \
    libuv \
    libvterm \
    libxml2 \
    lpeg \
    lua \
    luabitop \
    luajit \
    luv \
    msgpack-c \
    openssl \
    p11-kit \
    pcre2 \
    poly2tri \
    slang \
    sysfsutils \
    tree-sitter \
    unibilium \
    utf8proc \
    xxhash3 \
"

python () {
    from bbdepend import BBDepend as BD
    BD.depends(d, d.get("DEPEND_LIST"))
}
do_check_need_build[depends] += "${DEPEND_TARGETS}"

