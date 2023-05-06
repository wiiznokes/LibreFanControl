# LibreFanControl

## Notes
- You can see a screenshot of the app [here](https://github.com/wiiznokes/LibreFanControl/blob/main/assets/mainPageV1.png)
- Linux is not supported for now

## Why you should use it
- open source
- service very low on ressource (~30mb)
- display sensors data on real time
- control fans based on custom behaviors
- save configuration



## Requirements
You will need some component of [dotnet 7](https://dotnet.microsoft.com/en-us/download/dotnet/7.0)

- `ASP.NET Core Runtime`
- `.NET Runtime`

If you want to build the project, you will just need the `SDK`.

## Plans (not in this order)

- [ ] Use strongly typed resources for strings and icons
- [ ] Add a system to automatically receive updates
- [ ] Add workflow to the project (CI/CD, ect...) (docker ?)
- [ ] Support Linux (maybe reuse some parts of C# ?)
- [ ] Implement settings (info, help)
- [ ] Add graph behavior (abscissa -> temp, ordinate -> fan speed)
- [ ] Write an intelligent program to bind controls to their fans, and make a nice first config

## Library used

### UI
- [Compose Multiplatform Desktop](https://www.jetbrains.com/lp/compose-mpp/)
- [setting-sliding-windows](https://github.com/wiiznokes/setting-sliding-windows)
### SENSORS
##### Windows
- [LibreHardwareMonitor](https://github.com/LibreHardwareMonitor/LibreHardwareMonitor)
##### Linux
- [lm-sensor](https://github.com/lm-sensors/lm-sensors)
