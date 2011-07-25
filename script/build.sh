#!/usr/bin/env zsh

cd `dirname $0`/..
$CLOJURESCRIPT_HOME/bin/cljsc src > main.js
