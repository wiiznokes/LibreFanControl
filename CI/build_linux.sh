#!/bin/bash


rm -rf publish


dotnet build ./HardwareLib/LibreFanControlService -c "Release Linux"
cd ./LibreFanControl
chmod +x ./gradlew
./gradlew generateAllProto
./gradlew packageReleaseAppImage



cd ..
mkdir publish
cp -r ./LibreFanControl/app/build/compose/binaries/main-release/app/LibreFanControl ./publish
echo files copied !
cd publish

chmod +x ./LibreFanControl/lib/app/resources/build/LibreFanControlService
echo set LibreFanControlService executable !
tar -czvf LibreFanControl.tar.gz LibreFanControl/* > /dev/null
echo archive created !
