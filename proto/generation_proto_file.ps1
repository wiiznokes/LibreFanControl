$protocPath = $PSScriptRoot + "/protoc-21.12-win64/bin/protoc.exe"

$protoFiles = Get-ChildItem -Path . -Filter *.proto -File | Select-Object -ExpandProperty FullName

$csharpOutput = $PSScriptRoot + "/../Library/Windows/HardwareLib/HardwareDaemon/"
$kotlinOutput = $PSScriptRoot + "/../FanControl/src/jvmMain/java/"


foreach ($protoFile in $protoFiles) {
    & "$protocPath" "-I" "$PSScriptRoot" "--csharp_out" "$csharpOutput" "--java_out" "$kotlinOutput" "$protoFile"
}
