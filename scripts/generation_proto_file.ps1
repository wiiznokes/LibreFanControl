$protocPath = "/../"
$PSScriptRoot


.\HardwareDaemon\proto\protoc-21.12-win64\bin\protoc.exe .\HardwareDaemon\proto\data.proto --csharp_out=.\HardwareDaemon\proto\ --java_out=.\..\src\jvmMain\kotlin\