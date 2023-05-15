#!/bin/bash


# option:
#   Manual
#   Automatic
#   Debug



scriptRoot="$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )/"
. "$scriptRoot"/Manager.sh


if [ "$#" -eq 0 ]; then
    startMode="Manual"
elif [ "$#" -eq 1 ] && [[ "$1" = "Manual" || "$1" = "Automatic" || "$1" = "Debug" ]]; then
    startMode="$1"
else
    echo "Error: need one argument, Manual or Automatic"
    exit $defaultErrorCode
fi

if [[ "$startMode" = "Debug" ]]; then
    exit 0
fi





if ! checkAdmin; then
    exit "$needAdminErrorCode"
fi

echo "begin change mode: $startMode"

setServiceMode "$startMode"

exit $?