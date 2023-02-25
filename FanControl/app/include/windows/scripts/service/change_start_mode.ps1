. $PSScriptRoot/Manager.ps1


if ($args.Count -ne 1)
{
    Write-Output "Error: need one argument, Manual or Automatic"
    exit 1
}
elseif ($args[0] -ne "Manual" -and $args[0] -ne "Automatic")
{
    Write-Output "Error: need one argument, Manual or Automatic"
    exit 1
}


$startMode = $args[0]

Write-Output "begin change start mode to $startMode"

if (!(checkAdmin))
{
    exit 3
}

if (!(checkInstall))
{
    exit 2
}

if ($startMode -eq "auto")
{
    addRegistry
}

Set-Service -Name $serviceName -StartupType $startMode
Write-Host "$serviceName set to $startMode"