#!/usr/bin/env bash

set -e

echo "Clean source under src/main/java"
rm -rf src/main/java/io

echo "Generate source code"
mvn clean generate-sources

echo "Move generate code to source folder"
cp -r target/generated-sources/swagger/src/main/java/*  src/main/java/

echo "Clean up generated folder"
rm -rf target