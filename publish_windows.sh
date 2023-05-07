#!/bin/bash

rm -rf publish

dotnet build ./HardwareLib/FanControlService -c "Release Windows"

cd ./FanControl
./gradlew generateAllProto

./gradlew packageReleaseMsi

cd ..


mkdir publish

cp ./FanControl/app/build/compose/binaries/main-release/msi/FanControl* ./publish
