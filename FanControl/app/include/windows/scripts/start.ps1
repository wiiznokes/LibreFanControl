. $PSScriptRoot/Manager.ps1





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


if (checkRunning)
{
    exit 0
}


if (!(checkAdmin))
{
    exit $needAdminErrorCode
}

$res = startService

exit $res