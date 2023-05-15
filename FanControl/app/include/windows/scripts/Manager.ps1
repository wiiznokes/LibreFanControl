
$appName = "LibreFanControl"
$serviceName = "${appName}Service"

$DisplayName = "Libre Fan control service"
$Description = "Update fan speed and send sensors value to LibreFanControl app"
$exeName = "${serviceName}.exe"

$exeDir = "C:\Program Files\${serviceName}\"
$localExeDir = "$PSScriptRoot\..\build\"

$confDir = "C:\ProgramData\${appName}\"


# 1 is already taken by ExecutionPolicy execption (can't run script on the machine)
$defaultErrorCode = 101
$needAdminErrorCode = 102
$notInstalledErrorCode = 103
$needRuntimeErrorCode = 104
$installedErrorCode=105


function checkExeDirInstall
{

    if (!(Test-Path $exeDir -PathType Container))
    {
        Write-Host "dir ${exeDir} don't exist"
        return $notInstalledErrorCode
    }


    return 0
}


function checkServiceInstall
{

    if (!(Get-Service -Name $serviceName -ErrorAction SilentlyContinue))
    {
        Write-Host "${serviceName} don't exist"
        return $notInstalledErrorCode
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
    New-Item -ItemType Directory -Path $exeDir -Force | Out-Null
    Write-Host "$exeDir dir created"
    Copy-Item -Path "${localExeDir}*" -Destination $exeDir -Recurse -Force
    Write-Host "exe files copied"
}

function checkRunning
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
    if (Test-Path $exeDir -PathType Container)
    {
        Remove-Item $exeDir -Recurse -Force
        Write-Host "$exeDir folder has been removed"
    }
}

function removeConfFolder
{
    if (Test-Path $confDir -PathType Container)
    {
        Remove-Item $confDir -Recurse -Force
        Write-Host "$confDir folder has been removed"
    }
}

function stopService
{
    Stop-Service -Name $serviceName
    Write-Host "$serviceName has been stopped"
}


function deleteService
{
    if (Get-Service -Name $serviceName -ErrorAction SilentlyContinue)
    {
        # only on powershell 6
        #Remove-Service -Name $serviceName
        sc.exe delete $serviceName
    }
}


function createService
{

    $params = @{
        Name = $serviceName
        BinaryPathName = "${exeDir}${exeName}"
        DisplayName = $DisplayName
        StartupType = "Manual"
        Description = $Description
    }

    New-Service @params
    Write-Host "$serviceName created"
}



function startService
{
    if (checkRunning)
    {
        return 0
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