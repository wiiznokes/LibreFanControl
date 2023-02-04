$protocPath = $PSScriptRoot + "/protoc-21.12-win64/bin/protoc.exe"


$csharpOutput = $PSScriptRoot + "/../Library/Windows/HardwareLib/HardwareDaemon/Proto"
$kotlinOutput = $PSScriptRoot + "/../FanControl/src/jvmMain/java/"


# create C# output dir if it doesn't exist
if (!(Test-Path -Path $csharpOutput -PathType Container)) {
    New-Item -ItemType Directory -Path $csharpOutput
}



$srcDir = "$PSScriptRoot/src/example/proto"
$protoFiles = Get-ChildItem -Path $srcDir | Select-Object -ExpandProperty FullName

# generation
foreach ($protoFile in $protoFiles) {
    & "$protocPath" "-I" "$srcDir" "--csharp_out" "$csharpOutput" "--java_out" "$kotlinOutput" "$protoFile"
}
