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

	isInstalled=0
    if [ ! -f $systemdDir$systemdFileName ]; then
		echo "file $systemdDir$systemdFileName don't exist"
		isInstalled=$notInstalledErrorCode
    fi

    if [ ! -d $exeDir ]; then
		echo "dir $exeDir don't exist"
		isInstalled=$notInstalledErrorCode
    fi

    return $isInstalled
}

checkRunning() {
	systemctl is-active $systemdFileName > /dev/null

	return $?
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

    systemctl is-enabled $systemdFileName > /dev/null
    enabledCode=$?

	if [ $enabledCode -eq 4 ]; then
        echo "$systemdFileName file not found"
		return $notInstalledErrorCode
	fi

	if [[ $enabledCode -ne 0 && $enabledCode -ne 1 ]]; then
		return $defaultErrorCode
	fi


    if [ "$1" = "Automatic" ]; then
        if [ $enabledCode -eq 0 ]; then
            echo "service already set to auto"
            return 0
        fi
        systemctl enable $systemdFileName
        echo "service set to auto"
        return 0
    else
        if [ $enabledCode -eq 1 ]; then
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

