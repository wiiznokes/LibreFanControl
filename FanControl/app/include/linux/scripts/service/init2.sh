#!/bin/bash

# this script need admin right, call after init.sh, if needed


scriptRoot="$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )/"
. "$scriptRoot"/Manager.sh



if ! checkAdmin; then
    exit "$needAdminErrorCode"
fi


if ! checkServiceFile; then
    copyServiceFile
fi


if ! checkExecutableCopy; then
    copyExecutable
fi

setServiceMode "$1"


if ! checkRunning; then
    echo "Service is not active"
    startService
fi
