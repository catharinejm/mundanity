#!/usr/bin/env bash

cd `dirname $0`/..
rm -rf out/*
$CLOJURESCRIPT_HOME/bin/cljsc src > main.js

