. $PSScriptRoot/Manager.ps1


if ($args.Count -ne 1)
{
    Write-Output "Error: need one argument, Manual or Automatic"
    exit $defaultErrorCode
}
elseif ($args[0] -ne "Manual" -and $args[0] -ne "Automatic" -and $args[0] -ne "Debug")
{
    Write-Output "Error: need one argument, Manual or Automatic"
    exit $defaultErrorCode
}


$startMode = $args[0]

Write-Output "begin change start mode to $startMode"

if ($startMode -eq "Debug")
{
    exit 0
}

if (!(checkAdmin))
{
    exit $needAdminErrorCode
}

$isInstalled = checkInstall
if ($isInstalled -ne 0)
{
    exit $isInstalled
}

if ($startMode -eq "auto")
{
    addRegistry
}

Set-Service -Name $serviceName -StartupType $startMode
Write-Host "$serviceName set to $startMode"