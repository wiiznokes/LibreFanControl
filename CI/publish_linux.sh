#!/bin/bash

scriptRoot="$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )/"
cd scriptRoot/..

rm -rf publish


dotnet build ./HardwareLib/LibreFanControlService -c "Release Linux"

cd ./FanControl
./gradlew generateAllProto

./gradlew packageReleaseAppImage

sudo chmod +x ./app/build/compose/binaries/main-release/app/LibreFanControl/lib/app/resources/build/LibreFanControlService


cd ..


mkdir publish

cp -r ./FanControl/app/build/compose/binaries/main-release/app/LibreFanControl ./publish

cd publish

tar -czvf LibreFanControl.tar.gz LibreFanControl/*
