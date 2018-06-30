#!/usr/bin/env bash

./gradlew compileKotlin2Js || exit 1
BASE_PATH="build/js"
node -e 'require("./'${BASE_PATH}'/antjs-engine").main();'
#node "$BASE_PATH/antjs-engine.js"