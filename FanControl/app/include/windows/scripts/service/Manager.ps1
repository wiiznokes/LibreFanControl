$serviceName = "FanControlService"
$appName = "FanControl"
$DisplayName = "Fan control service"
$Description = "Update fan speed and send sensors value to Fan control app"
$exeName = "FanControlService.exe"

$installPath = "C:\Program Files\$serviceName"
$buildPath = "$PSScriptRoot/../../build/"

$confPath = "C:\ProgramData\$appName"


# 1 is already taken by ExecutionPolicy execption (can't run script on the machine)
$defaultErrorCode = 2
$needAdminErrorCode = 3
$notInstalledErrorCode = 4
$needRuntimeErrorCode = 5

function checkInstall
{

    if (!(Get-Service -Name $serviceName -ErrorAction SilentlyContinue))
    {
        Write-Host "the service don't exist"
        return $notInstalledErrorCode
    }

    if (!(Test-Path $installPath -PathType Container))
    {
        Write-Host "install folder don't exist"
        return $notInstalledErrorCode
    }

    if (!(checkRuntime))
    {
        return $needRuntimeErrorCode
    }


    return 0
}


function checkAdmin
{
    if (!([Security.Principal.WindowsPrincipal] [Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator))
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
            Write-Host "service is running"
            return $true
        }
        else
        {
            return $false
        }
    }
    else
    {
        return $false
    }
}

function removeInstallFolder
{
    if (Test-Path $installPath -PathType Container)
    {
        Remove-Item $installPath -Recurse -Force
        Write-Host "$installPath folder has been removed"
    }
}

function removeConfFolder
{
    if (Test-Path $confPath -PathType Container)
    {
        Remove-Item $confPath -Recurse -Force
        Write-Host "$confPath folder has been removed"
    }
}

function stopService
{
    if (isRunning)
    {
        Stop-Service -Name $serviceName
        Write-Host "$serviceName has been stopped"
    }
}


function deleteService
{
    stopService
    if (Get-Service -Name $serviceName -ErrorAction SilentlyContinue)
    {
        # only on powershell 6
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
    if (isRunning)
    {
        return 0
    }

    $isInstalled = checkInstall
    if ($isInstalled -ne 0)
    {
        return $isInstalled
    }

    if (!(checkAdmin))
    {
        return $needAdminErrorCode
    }

    Start-Service $serviceName
    Write-Host "$serviceName started"

    return 0
}



function checkRuntime
{
    $runtimes = dotnet --list-runtimes
    $isAspNetCore7Installed = $runtimes | Select-String -Pattern "Microsoft.AspNetCore.App 7\.\d+\.\d+" -Quiet

    if ($isAspNetCore7Installed)
    {
        return $true
    }
    else
    {
        Write-Host "Microsoft.AspNetCore.App 7.x is not installed."
        return $false
    }
}