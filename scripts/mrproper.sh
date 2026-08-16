SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
cd $SCRIPT_DIR

rm -rf meta-layer/build/cache
rm -rf meta-layer/build/tmp
rm -rf meta-layer/build/bitbake*

git clean -xdf
git reset --hard
git checkout .

