$OldPath = Get-Location
Set-Location $PSScriptRoot


$protocPath = "./protoc-21.12-win64/bin/protoc.exe"
$protoFilePath = "./data.proto"


$csharpOutput = "./../Library/Windows/HardwareLib/HardwareDaemon/"
$kotlinOutput = "./../FanControl/src/jvmMain/kotlin/"
 
& "$protocPath" "$protoFilePath" "--csharp_out" "$csharpOutput" "--java_out" "$kotlinOutput" "--kotlin_out" "$kotlinOutput"


Set-Location $OldPath