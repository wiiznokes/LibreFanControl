#!/bin/bash

scriptRoot="$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )/"
. "$scriptRoot"/Manager.sh






if ! checkAdmin; then
    exit $needAdminErrorCode
fi


if checkInstall; then
    exit $installedErrorCode
fi


copyServiceFiles

