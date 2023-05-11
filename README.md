# LibreFanControl


- Open source
- Service very low on ressource (~30mb)
- Display sensors data on real time
- Control fans based on custom behaviors
- Save configuration
- Multiplatform (Linux/Windows)



![mainPageV1](https://github.com/wiiznokes/LibreFanControl/assets/78230769/543af76c-137c-456d-a04e-8ebfed323178)



## Requirements
You will need some component of [dotnet 7](https://dotnet.microsoft.com/en-us/download/dotnet/7.0)

- `ASP.NET Core Runtime`
- `.NET Runtime`

If you want to build the project, you will just need the `SDK`. The needed commands can be found in CI directory but some may not work directly on your machine.

You will need to run the app with admin privilege on both Linux and Windows.

If you have any troubles, check out the [faq](./assets/faq.md).

Issues, enhancement requests and PR are welcomed!

## Configurations files

#### Windows
- `C:\ProgramData\FanControl`
#### Linux
- `/etc/FanControl`

On Linux, maybe you will need to download libsensors and execute sensors-detect.

## Plans (not in this order)

- [x] Support Linux
- [ ] Use strongly typed resources for strings and icons
- [ ] Add a system to automatically receive updates
- [ ] Add workflow to the project (CI/CD, ect...) (docker ?)
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
- [lm-sensors](https://github.com/lm-sensors/lm-sensors)
