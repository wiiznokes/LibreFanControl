. $PSScriptRoot/Manager.ps1

# option:
#   -c remove conf folder





if ($args.Count -gt 1)
{
    Write-Output "Error: To many arguments. need optional one argument, -c or -conf"
    exit $defaultErrorCode
}

if (!(checkAdmin))
{
    exit $needAdminErrorCode
}

if ($args.Count -eq 1)
{
    if ($args[0] -ne "-c")
    {
        Write-Output "need 0 or 1 argument (-c for remove conf folder)"
        exit $defaultErrorCode
    }

    removeConfFolder
}

if (checkRunning)
{
    stopService
}

deleteService

removeInstallFolder