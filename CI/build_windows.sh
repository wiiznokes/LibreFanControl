#!/bin/bash


rm -rf publish

dotnet build ./HardwareLib/LibreFanControlService -c "Release Windows"

cd ./FanControl
./gradlew generateAllProto
./gradlew packageReleaseMsi

cd ..
mkdir publish
cp ./FanControl/app/build/compose/binaries/main-release/msi/LibreFanControl* ./publish
echo files copied !