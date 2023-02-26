. $PSScriptRoot/Manager.ps1


if (!(checkAdmin))
{
    exit 3
}


if ($args.Count -gt 1)
{
    Write-Output "Error: need optional one argument, -c or -conf"
    exit 1
}

if ($args.Count -eq 1)
{
    if ($args[0] -ne "-c" -and $args[0] -ne "-conf")
    {
        Write-Output "Error: need optional one argument, -c or -conf"
        exit 1
    }

    removeConfFolder
}


deleteService
removeInstallFolder