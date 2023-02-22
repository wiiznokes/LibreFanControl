. $PSScriptRoot/Manager.ps1

if(!(checkAdmin)) {
    exit 3
}

deleteService
removeInstallFolder