SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

cd $SCRIPT_DIR

SRC=$(realpath $SCRIPT_DIR/../../buildscripts/python/bin/setupscripts.py)
echo "UPDATE: $SRC"
cp $SRC .
./setupscripts.py

