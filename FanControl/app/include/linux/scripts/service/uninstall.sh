#!/bin/bash

scriptRoot="$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )/"
. "$scriptRoot"/Manager.sh


#if ! checkAdmin; then
#    exit "$needAdminErrorCode"
#fi


if checkRunning; then
    systemctl stop "$serviceFileName"
    echo "service stopped"
fi


if [ -f "$systemdServiceFile" ]; then
    rm -f "$systemdServiceFile"
    echo ".service file removed"
fi

if [ -d "$exeCopyDir" ]; then
    rm -rf "$exeCopyDir"
    echo "service binaries files removed"
fi

systemctl daemon-reload
echo "service uninstalled: success"