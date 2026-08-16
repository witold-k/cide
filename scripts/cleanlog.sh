SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

if [ -d ${SCRIPT_DIR}/../meta-layer/build/tmp ];
then
    find ${SCRIPT_DIR}/../meta-layer/build/tmp -name "log.*" -or -name "run.*" | xargs rm -rf
fi
