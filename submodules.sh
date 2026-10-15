#!/usr/bin/env bash

# TODO: Integrate into fetcher

dirs=(
    modules/base/p11-kit
    modules/base/libpsl
    modules/diffusion/stable-diffusion.cpp
)

current=$(pwd)

for dir in "${dirs[@]}"; do
    cd "$current/$dir" || exit 1
    echo "current dir: $(pwd)"
    git clean -xdf
    git submodule foreach --recursive git clean -xdf
    git submodule foreach --recursive git reset --hard
    git submodule update --init --recursive || exit 1
done
