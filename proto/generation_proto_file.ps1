$protocPath = $PSScriptRoot + "/protoc-21.12-win64/bin/protoc.exe"

$protoFiles = Get-ChildItem -Path . -Filter *.proto -File | Select-Object -ExpandProperty FullName

$csharpOutput = $PSScriptRoot + "/../Library/Windows/HardwareLib/HardwareDaemon/Proto"
$kotlinOutput = $PSScriptRoot + "/../FanControl/src/jvmMain/java/"


# create C# output dir if it doesn't exist
if (!(Test-Path -Path $csharpOutput -PathType Container)) {
    New-Item -ItemType Directory -Path $csharpOutput
}


# generation
foreach ($protoFile in $protoFiles) {
    & "$protocPath" "-I" "$PSScriptRoot" "--csharp_out" "$csharpOutput" "--java_out" "$kotlinOutput" "$protoFile"
}
