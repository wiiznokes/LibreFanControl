# LibreFanControl (Alpha)

------

## Notes
- Linux is not supported for now

## Why you should use it
- open source
- service very low on ressource (~30mb)
- display sensor data on real time
- control fans based with custom behavior
- save configuration



## Build (in this order)
- Lib windows (dotnet7)
```
dotnet build
```
- proto
```
.\gradlew generateAllProto
```
- App
```
.\gradlew run 
```

## Plans

- [ ] Use strongly typed resources, maybe Kotlin dataframe
- [ ] Add workflow to the project (CI/CD, ect...)
- [ ] Support Linux
- [ ] Implement settings (info, help)
- [ ] Add graph behavior (abscissa -> temp, ordinate -> fan speed)


## Library used

#### UI
- [Compose Multiplatform Desktop](https://www.jetbrains.com/lp/compose-mpp/)
- [setting-sliding-windows](https://github.com/wiiznokes/setting-sliding-windows)
#### SENSORS
##### Windows
- [LibreHardwareMonitor](https://github.com/LibreHardwareMonitor/LibreHardwareMonitor)
##### Linux
- [lm-sensor](https://github.com/lm-sensors/lm-sensors)

  
<details>
<summary>Help to develop</summary>
<br/>
  
> LibreHardwareMonitor [implementation](https://github.com/lich426/FanCtrl) in C#

> Github of [compose-desktop](https://github.com/JetBrains/compose-jb)

</details>

