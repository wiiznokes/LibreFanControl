$installPath = "C:\Program Files\FanControlService"
$serviceName = "FanControlService"
$exeName = "HardwareDaemon.exe"
$buildPath = "$PSScriptRoot/../../build/"


function checkInstall
{
    if (-not(Get-Service -Name $serviceName -ErrorAction SilentlyContinue))
    {
        Write-Host "the service don't exist"
        return $false
    }

    if (-not(Test-Path $installPath -PathType Container))
    {
        Write-Host "install folder don't exist"
        return $false
    }

    Write-Host "all good"
    return $true
}




function checkAdmin
{
    if (-not ([Security.Principal.WindowsPrincipal] [Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator))
    {
        Write-Host "not in admin mode"
        return $false
    }
    return $true
}



function copyServiceFiles
{
    removeInstallFolder

    New-Item -ItemType Directory -Path $installPath -Force | Out-Null
    Write-Host "$installPath created"
    Copy-Item -Path "$buildPath\*" -Destination $installPath -Recurse -Force
    Write-Host "copy of $buildPath in $installPath finished"
}

function getStatus
{
    $info = Get-Service -Name $serviceName -ErrorAction SilentlyContinue
    if ($info)
    {
        return $info.Status
    }
    return ""
}

function removeInstallFolder
{
    if (Test-Path $installPath -PathType Container)
    {
        Remove-Item $installPath -Recurse -Force
        Write-Host "$installPath folder has been removed"
    }
}

function stopService
{
    if (getStatus -eq "Running")
    {
        Stop-Service -Name $serviceName
        Write-Host "$serviceName has been stopped"
    }
}

function removeRegistry
{
    if (Test-Path "HKLM:\SYSTEM\CurrentControlSet\Services\$serviceName")
    {
        Remove-Item -Path "HKLM:\SYSTEM\CurrentControlSet\Services\$serviceName" -Force -Recurse
        Write-Host "Registry key for $serviceName has been removed."
    }
}

function deleteService
{
    stopService
    removeRegistry
    if (Get-Service -Name $serviceName -ErrorAction SilentlyContinue)
    {
        sc.exe delete $serviceName
    }

}


function addRegistry
{
    New-Item -Path "HKLM:\SYSTEM\CurrentControlSet\Services\$serviceName" -Force | Out-Null
    Set-ItemProperty -Path "HKLM:\SYSTEM\CurrentControlSet\Services\$serviceName" -Name "ImagePath" -Value "$installPath\$exeName"
    Set-ItemProperty -Path "HKLM:\SYSTEM\CurrentControlSet\Services\$serviceName" -Name "DisplayName" -Value $serviceName
    Set-ItemProperty -Path "HKLM:\SYSTEM\CurrentControlSet\Services\$serviceName" -Name "Start" -Value "2"
    Write-Host "Registry key for $serviceName has been added"
}


function createService
{
    param([string]$startMode)
    deleteService

    if ($startMode -eq "auto")
    {
        addRegistry

        sc.exe create $serviceName binPath = "$installPath\$exeName" start = auto | Out-Null
        Write-Host "service created with auto mode"
    }
    else
    {
        sc.exe create $serviceName binPath = "$installPath\$exeName" | Out-Null
        Write-Host "service created with manuel mode"
    }
}



function startService
{
    if (-not(getStatus -eq "Running"))
    {
        Start-Service $serviceName
        Write-Host "$serviceName started"
    }
    else
    {
        Write-Host "$serviceName already running."
    }
}