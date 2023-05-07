#!/bin/bash



dotnet build ./HardwareLib/FanControlService -c "Release Windows"

cd ./FanControl
./gradlew generateAllProto

./gradlew packageReleaseMsi

cd ..

rm -rf publish

mkdir publish

cp ./FanControl/app/build/compose/binaries/main-release/msi/FanControl* ./publish
