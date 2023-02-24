$serviceName = "FanControlService"
$DisplayName = "Fan control service"
$Description = "Update fan speed and send sensors value to Fan control app"
$exeName = "HardwareDaemon.exe"

$installPath = "C:\Program Files\$serviceName"
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
    Write-Host "copy in $installPath finished"
}

function isRunning
{
    $info = Get-Service -Name $serviceName -ErrorAction SilentlyContinue
    if ($info)
    {
        $status = $info.Status
        if ($status -eq "Running")
        {
            return $true
        }
    }
    return $false
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
    if (isRunning)
    {
        Stop-Service -Name $serviceName
        Write-Host "$serviceName has been stopped"
    }
    return $true
}


function deleteService
{
    stopService
    if (Get-Service -Name $serviceName -ErrorAction SilentlyContinue)
    {
        # only if powershell v6
        #Remove-Service -Name $serviceName
        sc.exe delete $serviceName
    }
}


function createService
{
    param([string]$startMode)

    deleteService

    $params = @{
        Name = $serviceName
        BinaryPathName = "$installPath\$exeName"
        DisplayName = $DisplayName
        StartupType = $startMode
        Description = $Description
    }

    New-Service @params
    Write-Host "$serviceName created"
}



function startService
{
    if (!(checkInstall))
    {
        Write-Host "start service canceled"
        return $false
    }

    if (!(isRunning))
    {
        Start-Service $serviceName
        Write-Host "$serviceName started"
    }
    else
    {
        Write-Host "$serviceName already running."
    }
    return $true
}