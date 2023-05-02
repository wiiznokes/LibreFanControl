#!/bin/bash

scriptRoot="$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )/"
. "$scriptRoot"/Manager.sh


if [ "$#" -eq 0 ]; then
    startMode="Manual"
elif [ "$#" -eq 1 ] && [[ "$1" = "Manual" || "$1" = "Automatic" ]]; then
    startMode="$1"
else
    echo "Error: need one argument, Manual or Automatic"
    exit 10
fi


echo "begin change mode: $startMode"

setServiceMode "$startMode"