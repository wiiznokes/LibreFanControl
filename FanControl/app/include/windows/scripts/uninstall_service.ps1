$serviceName = "FanControlService"
$installPath = "C:\Program Files\FanControlService"


Stop-Service -Name $serviceName
Write-Output "$serviceName has been stoped"
sc.exe delete $serviceName
Write-Output "$serviceName has been deleted"

if (Test-Path "HKLM:\SYSTEM\CurrentControlSet\Services\$serviceName") {
    Remove-Item -Path "HKLM:\SYSTEM\CurrentControlSet\Services\$serviceName" -Force -Recurse
    Write-Output "Registry key for $serviceName has been removed"
}


if (Test-Path $installPath -PathType Container) {
    Remove-Item $installPath -Recurse -Force
    Write-Output "$installPath folder has been removed"
}