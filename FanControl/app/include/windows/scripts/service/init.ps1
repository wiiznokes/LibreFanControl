. $PSScriptRoot/Manager.ps1



if ($args.Count -gt 1)
{
    Write-Output "Error: need one argument, Manual or Automatic"
    exit $defaultErrorCode
}
elseif ($args.Count -eq 0)
{
    $startMode = "Manual"
}
elseif ($args.Count -eq 1 -and ($args[0] -eq "Manual" -or $args[0] -eq "Automatic" -or $args[0] -eq "Debug"))
{
    $startMode = $args[0]
}
else
{
    Write-Output "Error: need one argument, Manual or Automatic"
    exit $defaultErrorCode
}

Write-Output "init: start mode: $startMode"

if ($startMode -eq "Debug")
{
    exit 0
}


$isInstalled = checkInstall

if (isRunning -and $isInstalled -eq 0)
{
    exit 0
}


if (!(checkAdmin))
{
    exit $needAdminErrorCode
}




if ($isInstalled -ne 0)
{
    if ($isInstalled -eq $needRuntimeErrorCode)
    {
        exit $needRuntimeErrorCode
    }

    deleteService
    removeInstallFolder
    copyServiceFiles
    createService($startMode)
}


startService