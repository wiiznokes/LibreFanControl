#!/bin/bash

scriptRoot="$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )/"
. "$scriptRoot"/Manager.sh



if [ "$#" -eq 0 ]; then
    startMode="Manual"
elif [ "$#" -eq 1 ] && [[ "$1" = "Manual" || "$1" = "Automatic" || "$1" = "Debug" ]]; then
    startMode="$1"
else
    echo "Error: need one argument, Manual, Automatic or Debug"
    exit "$defaultErrorCode"
fi


echo "init: start mode: $startMode"



if [ "$startMode" = "Debug" ]; then
    exit 0
fi


#if ! checkRuntime; then
#    echo "dotnet runtime not found, please install it"
#    exit "$needRuntimeErrorCode"
#fi


if ! checkServiceFile; then
    copyServiceFile
fi


if ! checkExecutableCopy; then
    copyExecutable
fi

setServiceMode "$startMode"


if ! checkRunning; then
    echo "Service is not active"
    startService
fi