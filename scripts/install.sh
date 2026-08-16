SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

PY_SCRIPTS_DIR=$SCRIPT_DIR/../buildscripts/python/bin
env_vars=$(python3 $PY_SCRIPTS_DIR/get_config_environment.py $SCRIPT_DIR/../config/environment.json)
eval $env_vars

if [ -z $PROJECT_ROOT ] || [ ! -d $PROJECT_ROOT ];
then
    echo "PROJECT_ROOT $PROJECT_ROOT does not exist"
    exit
fi
if [ -z $ROOTFS_DIR ] || [ ! -d $ROOTFS_DIR ];
then
    echo "ROOTFS_DIR $ROOTFS_DIR does not exist"
    exit
fi

PROJECT_NAME=$(basename "$PROJECT_ROOT")
INSTALL_DIR=/opt/$PROJECT_NAME/$NEXT_VERSION/$NEXT_VERSION

echo "PROJECT_NAME: $PROJECT_NAME"
echo "PROJECT_ROOT: $PROJECT_ROOT"
echo "ROOTFS_DIR:   $ROOTFS_DIR"
echo "INSTALL_DIR:  $INSTALL_DIR"

# if [ "$USER" != "root" ];
# then
#     echo "not super user changing to root..."
#     sudo bash $0
#     exit
# fi

cd $ROOTFS_DIR || exit
mkdir -p $INSTALL_DIR || exit
rsync -a -r --delete  * $INSTALL_DIR/
diff -rq $INSTALL_DIR .
cd $INSTALL_DIR || exit

