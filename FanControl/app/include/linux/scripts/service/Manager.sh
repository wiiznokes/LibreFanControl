#!/bin/bash

scriptRoot="$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )/"


serviceName="FanControlService"

buildDir="$scriptRoot../../build/"

serviceFileName="fan-control.service"

localServiceFile="$scriptRoot$serviceFileName"

systemdDir="/etc/systemd/system/"
systemdServiceFile="$systemdDir$serviceFileName"
exeCopyDir="/usr/local/bin/$serviceName"

confPath="/etc/FanControl/"

# 1 is already taken by ExecutionPolicy exception (can't run script on the machine (on Windows of course)
defaultErrorCode=101
needAdminErrorCode=102
notInstalledErrorCode=103
needRuntimeErrorCode=104

replaceInServiceFile() {
    local name="$1"
    local value="$2"
    sed -i "s|$name=.*|$name=$value|" "${scriptRoot}${serviceFileName}"
}




setServiceMode() {

    isEnabled="$(systemctl is-enabled $serviceFileName)"

    if [[ "$isEnabled" != "enabled" && "$isEnabled" != "disabled" ]]; then
        echo "can't set service mode: service is likely to be uninstalled"
        return 1
    fi

    if [ "$#" -ne 1 ] || [[ "$1" != "Manual" && "$1" != "Automatic" ]]; then
        echo "Wrong arguments for setServiceMode"
        exit 1
    fi


    if [ "$1" = "Automatic" ]; then
        if [[ $isEnabled = "enabled" ]]; then
            echo "service already set to auto"
            return 0
        fi
        systemctl enable "$serviceFileName"
        echo "service set to auto"
        return 0
    else
        if [[ $isEnabled = "disabled" ]]; then
            echo "service already set to manual"
            return 0
        fi
        systemctl disable "$serviceFileName"
        echo "service set to manual"
        return 0
    fi

}

checkServiceFile() {
    if [[ ! -f "$systemdServiceFile" ]]; then
        echo "$serviceFileName not found"
        return 1
    fi

    diff_output=$(diff -q "$localServiceFile" "$systemdServiceFile")

    if [ -z "$diff_output" ]; then
        echo "Service file up to date"
        return 0
    else
        echo "$serviceFileName not up to date"
        return 1
    fi
}

copyServiceFile(){
    if ! checkAdmin; then
        exit $needAdminErrorCode
    fi
    pkexec cp -f "$scriptRoot"$serviceFileName $systemdDir
    systemctl daemon-reload
    echo "Service file up to date"
}


checkRunning() {
    if [[ "$(systemctl is-active $serviceFileName)" = "active" ]]; then
        echo "Service is active"
        return 0
    fi

    return 1
}

startService() {
    if checkRunning; then
        echo "Service already running"
        return 0
    fi
    systemctl start $serviceFileName
    echo "Service started"
    return 0
}





checkExecutableCopy() {
    if [ ! -d "$exeCopyDir" ]; then
        echo "$exeCopyDir directory don't exist"
        return 1
    fi

    diff_output=$(diff -q -r "$exeCopyDir" "$buildDir")

    if [ -z "$diff_output" ]; then
        echo "binaries dir is up to date"
        return 0
    else
        echo "$exeCopyDir directory is not up to date"
        return 1
    fi
}

copyExecutable() {
    if ! checkAdmin; then
        exit $needAdminErrorCode
    fi

    rm -rf "$exeCopyDir"
    mkdir "$exeCopyDir"
    cp -r "$buildDir"* "$exeCopyDir"
    echo "binaries dir up to date"
}






checkRuntime() {
    if [[ "$(which dotnet)" == "" ]]; then
        return $needRuntimeErrorCode
    fi
    echo "dotnet runtime found"
    return 0
}




checkAdmin() {
    if [[ $(id -u) -ne 0 ]]; then
        echo "not in admin mode"
        return 1
    fi
    return 0
}

