#!/bin/bash
set -e

./gradlew shadowJar
../java-windows-exe-template/scripts/build-exe.sh \
Calculator \
1.0.0 \
build/libs/calculator-1.0.0-all.jar \
io.github.ahansantra.calculator.Main \
icons/calculator.ico

