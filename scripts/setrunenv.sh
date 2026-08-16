SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

export LD_LIBRARY_PATH=$SCRIPT_DIR/../meta-layer/build/tmp/rootfs/native/lib
export PATH=$PATH:$SCRIPT_DIR/../meta-layer/build/tmp/rootfs/native/bin
