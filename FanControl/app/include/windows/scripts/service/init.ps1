. $PSScriptRoot/Manager.ps1



if ($args.Count -gt 1)
{
    Write-Output "Error: need one argument, Manual or Automatic"
    exit 1
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
    exit 1
}

Write-Output "init: start mode: $startMode"

if ($startMode -eq "Debug")
{
    return 0
}


if (!(isRunning) -and !(checkAdmin))
{
    exit 3
}


if (!(checkInstall))
{
    if (!(checkAdmin))
    {
        exit 3
    }
    deleteService
    removeInstallFolder
    copyServiceFiles
    createService($startMode)
}


startService