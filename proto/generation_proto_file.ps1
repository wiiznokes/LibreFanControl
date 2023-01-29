$protocPath = $PSScriptRoot + "/protoc-21.12-win64/bin/protoc.exe"
$protoFilePath = $PSScriptRoot + "/data.proto"


$csharpOutput = $PSScriptRoot + "/../Library/Windows/HardwareLib/HardwareDaemon/"
$kotlinOutput = $PSScriptRoot + "/../FanControl/src/jvmMain/java/"
 
& "$protocPath" "-I" "$PSScriptRoot" "--csharp_out" "$csharpOutput" "--java_out" "$kotlinOutput" "$protoFilePath"