SUMMARY = "bazel"
LICENSE = "CLOSED"
MODULE = "buildsystems/bazel"
SRCPV = "1"

inherit populate

python do_install() {
    s = d.getVar('S')
    os.chdir(s)

    # env EXTRA_BAZEL_ARGS="--tool_java_runtime_version=local_jdk" bash ./compile.sh
}
