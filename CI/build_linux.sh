#!/bin/bash


rm -rf publish


dotnet build ./HardwareLib/LibreFanControlService -c "Release Linux"
cd ./FanControl
./gradlew generateAllProto
./gradlew packageReleaseAppImage



cd ..
mkdir publish
cp -r ./FanControl/app/build/compose/binaries/main-release/app/LibreFanControl ./publish
cd publish

chmod +x ./LibreFanControl/lib/app/resources/build/LibreFanControlService
tar -czvf LibreFanControl.tar.gz LibreFanControl/*
