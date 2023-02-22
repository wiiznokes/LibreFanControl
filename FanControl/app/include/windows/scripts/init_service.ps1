$installPath = "C:\Program Files\FanControlService"
$serviceName = "FanControlService"
$exeName = "HardwareDaemon.exe"
$startMode = $args[0]
$buildPath = "$PSScriptRoot/../build/"


if (-not (Get-Service -Name $serviceName -ErrorAction SilentlyContinue)) {
    Write-Output "the service don't exist"

    if (Test-Path $installPath -PathType Container) {
        Remove-Item $installPath -Recurse -Force
        Write-Output "remove previous install folder $installPath"
    }

    if (Test-Path "HKLM:\SYSTEM\CurrentControlSet\Services\$serviceName") {
        Remove-Item -Path "HKLM:\SYSTEM\CurrentControlSet\Services\$serviceName" -Force -Recurse
        Write-Output "remove previous registry key for $serviceName"
    }


    New-Item -ItemType Directory -Path $installPath -Force | Out-Null
    Write-Output "$installPath created"
    Copy-Item -Path "$buildPath\*" -Destination $installPath -Recurse -Force
    Write-Output "copy of $buildPath in $installPath finished"

    if (Test-Path "HKLM:\SYSTEM\CurrentControlSet\Services\$serviceName") {
        Remove-Item -Path "HKLM:\SYSTEM\CurrentControlSet\Services\$serviceName" -Force -Recurse
        Write-Output "Registry key for $serviceName has been removed."
    }

    if ($startMode -eq "auto") {
        New-Item -Path "HKLM:\SYSTEM\CurrentControlSet\Services\$serviceName" -Force | Out-Null
        Set-ItemProperty -Path "HKLM:\SYSTEM\CurrentControlSet\Services\$serviceName" -Name "ImagePath" -Value "$installPath\$exeName"
        Set-ItemProperty -Path "HKLM:\SYSTEM\CurrentControlSet\Services\$serviceName" -Name "DisplayName" -Value $serviceName
        Set-ItemProperty -Path "HKLM:\SYSTEM\CurrentControlSet\Services\$serviceName" -Name "Start" -Value "2"

        Write-Output "Registry key for $serviceName has been added"

        sc.exe create $serviceName binPath= "$installPath\$exeName" start= auto | Out-Null

        Write-Output "service created with auto mode"
    }
    else {
        sc.exe create $serviceName binPath= "$installPath\$exeName" | Out-Null
        Write-Output "service created with manuel mode"
    }
}

# todo si serviceVersion < appVersion -> reinstall service


$serviceStatus = (Get-Service -Name $serviceName).Status

if (-not ($serviceStatus -eq "Running")) {
    Start-Service $serviceName
    Write-Output "$serviceName started"
} else {
    Write-Output "$serviceName already running."
}