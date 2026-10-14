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
    git submodule update --init --recursive || exit 1
done
