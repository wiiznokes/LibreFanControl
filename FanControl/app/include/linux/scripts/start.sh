#!/bin/bash

scriptRoot="$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )/"
. "$scriptRoot"/Manager.sh




if ! checkInstall; then
    exit $notInstalledErrorCode
fi


if checkRunning; then
    exit 0
fi

if ! checkAdmin; then
    exit $needAdminErrorCode
fi


systemctl daemon-reload

startService

exit 0