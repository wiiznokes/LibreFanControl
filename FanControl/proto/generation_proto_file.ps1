$protocPath = $PSScriptRoot + "/protoc-21.12-win64/bin/protoc.exe"


$csharpOutput = "$PSScriptRoot/../../Library/Windows/HardwareLib/HardwareDaemon/Proto"
$kotlinOutput = "$PSScriptRoot/../src/jvmMain/java/"


# create C# output dir if it doesn't exist
if (!(Test-Path -Path $csharpOutput -PathType Container)) {
    New-Item -ItemType Directory -Path $csharpOutput
}


if (!(Test-Path -Path $csharpOutput -PathType Container)) {
    Write-Output bisous
}


$protoFiles = Get-ChildItem -Path "$PSScriptRoot/src/proto/" | Select-Object -ExpandProperty FullName

# generation
foreach ($protoFile in $protoFiles) {
    & "$protocPath" "-I" "$PSScriptRoot/src/proto/" "-I" "$PSScriptRoot" "--csharp_out" "$csharpOutput" "--java_out" "$kotlinOutput" "$protoFile"
}
