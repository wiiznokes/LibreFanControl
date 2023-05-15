#!/bin/bash


# option:
#   Manual
#   Automatic



scriptRoot="$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )/"
. "$scriptRoot"/Manager.sh


if [ "$#" -eq 0 ]; then
    startMode="Manual"
elif [ "$#" -eq 1 ] && [[ "$1" = "Manual" || "$1" = "Automatic" ]]; then
    startMode="$1"
else
    echo "Error: need one argument, Manual or Automatic"
    exit $defaultErrorCode
fi


echo "begin change mode: $startMode"


if ! checkAdmin; then
    exit "$needAdminErrorCode"
fi


setServiceMode "$startMode"

exit $?