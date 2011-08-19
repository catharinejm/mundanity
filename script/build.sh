#!/usr/bin/env bash

cd `dirname $0`/..
rm -rf out/*
$CLOJURESCRIPT_HOME/bin/cljsc src > main.js

HEADER_LINE_COUNT=$(grep -E '^goog\.(provide|require)' out/mundanity.js | wc -l | awk '{print $1}')

head -n$HEADER_LINE_COUNT out/mundanity.js > out/load.js
tail -n+$[$HEADER_LINE_COUNT+1] out/mundanity.js > /tmp/__mundanity.js
cp /tmp/__mundanity.js out/mundanity.js
