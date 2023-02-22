. .\ServiceManagement.ps1

$startMode = $args[0]

if(!(checkInstall)) {
    exit 2
}

if($startMode -eq "auto") {
    addRegistry
}

Set-Service -Name $serviceName -StartupType $startMode
Write-Output "$serviceName set to $startMode"