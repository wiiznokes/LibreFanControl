. .\Manager.ps1

$startMode = $args[0]

if(!(checkAdmin)) {
    exit 3
}

if(!(checkInstall)) {
    exit 2
}

if($startMode -eq "auto") {
    addRegistry
}

Set-Service -Name $serviceName -StartupType $startMode
Write-Host "$serviceName set to $startMode"