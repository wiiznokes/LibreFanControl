#!/bin/bash

scriptRoot="$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )/"
cd scriptRoot/..


rm -rf publish

dotnet build ./HardwareLib/LibreFanControlService -c "Release Windows"

cd ./FanControl
./gradlew generateAllProto

./gradlew packageReleaseMsi

cd ..


mkdir publish

cp ./FanControl/app/build/compose/binaries/main-release/msi/LibreFanControl* ./publish
