SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

PY_SCRIPTS_DIR=$SCRIPT_DIR/../buildscripts/python/bin
env_vars=$(python3 $PY_SCRIPTS_DIR/get_config_environment.py $SCRIPT_DIR/../config/environment.json)
eval $env_vars

if [ -z $PROJECT_ROOT ] || [ ! -d $PROJECT_ROOT ];
then
    echo "PROJECT_ROOT $PROJECT_ROOT does not exist"
    exit
fi
if [ -z $NEXT_VERSION ];
then
    echo "NEXT_VERSION $NEXT_VERSION does not exist"
    exit
fi

if [ -z $ROOTFS_DIR ] || [ ! -d $ROOTFS_DIR ];
then
    echo "ROOTFS_DIR $ROOTFS_DIR does not exist"
    exit
fi

PROJECT_NAME=$(basename "$PROJECT_ROOT")

echo "## PROJECT_NAME: $PROJECT_NAME"
echo "## PROJECT_ROOT: $PROJECT_ROOT"
echo "## ROOTFS_DIR:   $ROOTFS_DIR"
echo "## NEXT_VERSION: $NEXT_VERSION"

TAG=$(git describe --tags --abbrev=0)
DATE=$(date +"%Y-%m-%d")
#RELEASE="$TAG-$DATE"
git tag $NEXT_VERSION || exit
RELEASE=$NEXT_VERSION
echo "## TAG:          $TAG"
echo "## RELEASE:      $RELEASE"

COMMITID=$(git log -1 | grep commit)

tmp_release="tmp/${PROJECT_NAME}-release"

if [ -d $tmp_release ];
then
    rm -rf $tmp_release
fi

echo "## clone $REPOSITORY_RELEASE"
git clone --depth 1 $REPOSITORY_RELEASE $tmp_release || exit
cd $tmp_release || exit

GIT_NAME=$(git config user.name)
GIT_EMAIL=$(git config user.email)
if [ -z "$GIT_NAME" ];
then
    git config --global user.name "$USER"
fi
if [ -z "$GIT_EMAIL" ];
then
    git config --global user.email "$USER@localhost"
fi

echo "## branch $RELEASE"
git branch "$RELEASE" || exit
echo "## checkout $RELEASE"
git checkout "$RELEASE" || exit

echo "## setup-stream $RELEASE"
git push --set-upstream origin "$RELEASE" || exit

mkdir -p $RELEASE
cd $RELEASE
echo "## rsync $ROOTFS_DIR => $(pwd)"
rsync -ax --exclude .git $ROOTFS_DIR/* ./ || exit

cd $PROJECT_ROOT/$tmp_release || exit
git add "$RELEASE" || exit
git commit -m "$PORJECT_NAME Release $RELEASE: COMMIT: $COMMITID" || exit
if [ -z "$DO_NOT_PUSH" ];
then
    git push || exit
fi
