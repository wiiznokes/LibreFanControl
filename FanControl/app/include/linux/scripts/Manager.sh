#!/bin/bash

scriptRoot="$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )/"

appName="LibreFanControl"
serviceName="${appName}Service"

confDir="/etc/${appName}/"

systemdDir="/etc/systemd/system/"
systemdFileName="libre-fan-control.service"

exeDir="/usr/local/bin/${serviceName}/"
localExeDir="$scriptRoot../build/"





# 1 is already taken by ExecutionPolicy exception (can't run script on the machine (on Windows of course)
defaultErrorCode=101
needAdminErrorCode=102
notInstalledErrorCode=103
needRuntimeErrorCode=104
installedErrorCode=105




checkInstall() {
    if [ ! -f $systemdDir$systemdFileName ]; then
		echo "file $systemdDir$systemdFileName don't exist"
		return $notInstalledErrorCode
    fi

    if [ ! -d $exeDir ]; then
		echo "dir $exeDir don't exist"
		return $notInstalledErrorCode
    fi

    return 0
}

checkRunning() {
    if [[ "$(systemctl is-active $systemdFileName)" = "active" ]]; then
        echo "Service is active"
        return 0
    else
        echo "Service is not active"
        return 1
    fi

}

checkAdmin() {
    if [[ $(id -u) -ne 0 ]]; then
        echo "not in admin mode"
        return 1
    fi
    return 0
}

startService() {
    systemctl start $systemdFileName
    echo "Service started"
    return 0
}



removeConfDir() {
    if [ -d $confDir ]; then
        rm -rf $confDir
        echo "$confDir removed"
    fi
}

removeServiceFiles() {
    if [ -f $systemdDir$systemdFileName ]; then
        rm $systemdDir$systemdFileName
        echo "$systemdDir$systemdFileName removed"
    fi

    if [ -d $exeDir ]; then
        rm -rf $exeDir
        echo "$exeDir removed"
    fi

    return 0
}

copyServiceFiles() {
    cp -f $scriptRoot$systemdFileName $systemdDir
    echo "$systemdFileName copied"

    mkdir $exeDir
    echo "$exeDir dir created"

    cp -r "${localExeDir}"* $exeDir
    echo "exe files copied"

    return 0
}





setServiceMode() {

    isEnabled="$(systemctl is-enabled $serviceFileName)"

    if [[ "$isEnabled" != "enabled" && "$isEnabled" != "disabled" ]]; then
        echo "can't set service mode: service is likely to be uninstalled"
        return $defaultErrorCode
    fi


    if [ "$1" = "Automatic" ]; then
        if [[ $isEnabled = "enabled" ]]; then
            echo "service already set to auto"
            return 0
        fi
        systemctl enable "$systemdFileName"
        echo "service set to auto"
        return 0
    else
        if [[ $isEnabled = "disabled" ]]; then
            echo "service already set to manual"
            return 0
        fi
        systemctl disable "$systemdFileName"
        echo "service set to manual"
        return 0
    fi

}














checkRuntime() {
    if [[ "$(which dotnet)" == "" ]]; then
        return $needRuntimeErrorCode
    fi
    echo "dotnet runtime found"
    return 0
}

