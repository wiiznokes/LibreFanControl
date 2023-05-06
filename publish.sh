dotnet build ./HardwareLib/FanControlService -c "Release Windows"

cd ./FanControl
./gradlew generateAllProto

./gradlew packageReleaseMsi

cd ..

rm -r -force publish

mkdir publish

cp ./FanControl/app/build/compose/binaries/main-release/msi/FanControl* ./publish
