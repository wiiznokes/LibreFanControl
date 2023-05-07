#!/bin/bash

rm -rf publish

dotnet build ./HardwareLib/FanControlService -c "Release Linux"

cd ./FanControl
./gradlew generateAllProto

#./gradlew packageReleaseAppImage

cd ..


mkdir publish

cp -r ./FanControl/app/build/compose/binaries/main-release/app/FanControl ./publish

cd publish

tar -czvf FanControl.tar.gz FanControl/*