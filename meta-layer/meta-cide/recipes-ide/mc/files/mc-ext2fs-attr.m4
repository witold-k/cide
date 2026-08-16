AC_DEFUN([mc_EXT2FS_ATTR],
[
    AC_DEFINE(ENABLE_EXT2FS_ATTR, 1, [Define to enable support for ext2fs attributes])
    AC_SUBST(EXT2FS_CFLAGS)
    AC_SUBST(E2P_CFLAGS)
    MCLIBS="$MCLIBS $E2P_LIBS"
    ext2fs_attr_msg="yes"
])
