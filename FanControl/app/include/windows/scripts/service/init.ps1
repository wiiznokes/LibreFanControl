. $PSScriptRoot/Manager.ps1


$startMode = $args[0]

if ($startMode -eq "debug") {
    Write-Host "service debug mode"
    return 0
}

if(!(checkInstall)) {
    if(!(checkAdmin)) {
        exit 3
    }
    deleteService
    removeInstallFolder
    copyServiceFiles
    createService($startMode)
}
startService