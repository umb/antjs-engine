#!/usr/bin/env bash

BASE_PATH="build/js"
node -e 'require("./'${BASE_PATH}'/antjs-engine").main();'
#node "$BASE_PATH/antjs-engine.js"