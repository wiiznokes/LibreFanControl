#!/bin/bash
set -e

rm -rf publish

dotnet build ./HardwareLib/LibreFanControlService -c "Release Windows"

cd ./LibreFanControl
./gradlew generateAllProto
./gradlew packageReleaseMsi

cd ..
mkdir publish
cp ./LibreFanControl/app/build/compose/binaries/main-release/msi/LibreFanControl* ./publish
echo files copied !