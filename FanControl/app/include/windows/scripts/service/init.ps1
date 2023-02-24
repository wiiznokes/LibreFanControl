. $PSScriptRoot/Manager.ps1



if ($args.Count -gt 1) {
    Write-Output "Error: need one argument, Manual or Automatic"
    exit 1
}
elseif ($args.Count -eq 0) {
    $startMode = "Manual"
}
elseif ($args.Count -eq 1 -and ($args[0] -eq "Manual" -or $args[0] -eq "Automatic")) {
    $startMode = $args[0]
}
else {
    Write-Output "Error: need one argument, Manual or Automatic"
    exit 1
}


Write-Output "Start mode: $startMode"



if ($startMode -eq "debug")
{
    Write-Host "service debug mode"
    return 0
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