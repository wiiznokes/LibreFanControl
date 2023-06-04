#!/bin/bash

# option:
#   -c remove conf folder


scriptRoot="$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )/"
. "$scriptRoot"/Manager.sh


if [ "$#" -gt 1 ]; then
    echo "need 0 or 1 argument (-c for remove conf folder)"
    exit "$defaultErrorCode"
fi

if ! checkAdmin; then
    exit "$needAdminErrorCode"
fi

if [ "$#" -eq 1 ]; then
    if [ "$1" = "-c" ]; then
        removeConfDir
    else
        echo "need 0 or 1 argument (-c for remove conf folder)"
        exit "$defaultErrorCode"
    fi
fi




if checkRunning; then
    systemctl stop $systemdFileName
    echo "service stopped"
fi


removeServiceFiles

systemctl daemon-reload