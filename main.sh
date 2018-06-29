#!/usr/bin/env bash

./gradlew compileKotlin2Js || exit 1

BASE_PATH="build/classes/kotlin/main/"
node -e 'require("./'${BASE_PATH}'antme-engine-js").main();'