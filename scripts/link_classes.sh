SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
cd $SCRIPT_DIR
./setupscripts.sh

cd $SCRIPT_DIR/../meta-layer/meta-cide || exit
ls
rm -f classes/*
cd classes
$SCRIPT_DIR/../buildscripts/bitbake/link_classes.sh
