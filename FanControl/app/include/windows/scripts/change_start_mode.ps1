. $PSScriptRoot/Manager.ps1

# option:
#   Manual
#   Automatic

if ($args.Count -eq 0)
{
    $startMode = "Manual"
}
elseif ($args.Count -eq 1 -and ($args[0] -eq "Manual" -or $args[0] -eq "Automatic")) {
    $startMode = $args[0]
} else {
    Write-Output "Error: need one argument, Manual or Automatic"
    exit $defaultErrorCode
}



Write-Output "begin change start mode to $startMode"



if (!(checkAdmin))
{
    exit $needAdminErrorCode
}

$res = checkExeDirInstall
if ($res -ne 0)
{
    exit $notInstalledErrorCode
}


$res = checkServiceInstall
if ($res -ne 0)
{
    exit $notInstalledErrorCode
}




Set-Service -Name $serviceName -StartupType $startMode