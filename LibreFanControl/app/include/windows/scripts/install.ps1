. $PSScriptRoot/Manager.ps1






if (!(checkAdmin))
{
    exit $needAdminErrorCode
}

$res = checkExeDirInstall
if ($res -eq 0)
{
    exit $installedErrorCode
}


$res = checkServiceInstall
if ($res -eq 0)
{
    exit $installedErrorCode
}

copyServiceFiles

createService