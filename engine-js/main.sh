#!/usr/bin/env bash

../gradlew compileKotlin2Js

BASE_PATH="build/classes/kotlin/main/"
node -e 'require("./'${BASE_PATH}'antme-engine-js").main();'