#!/bin/bash

scriptRoot="$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )/"
cd scriptRoot/..

rm -rf publish

cd HardwareLib/FanControlService/External/LmSensors/
make release
cd ../../../..

dotnet build ./HardwareLib/FanControlService -c "Release Linux"

cd ./FanControl
./gradlew generateAllProto

#./gradlew packageReleaseAppImage

cd ..


mkdir publish

cp -r ./FanControl/app/build/compose/binaries/main-release/app/FanControl ./publish

cd publish

tar -czvf FanControl.tar.gz FanControl/*