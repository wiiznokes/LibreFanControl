# LibreFanControl

**
This project is deprecated. I'm working on a new version practically rewritten from scratch in Rust.
It will be more efficient, will not use services, and will better support Linux.
This is the repo: https://github.com/wiiznokes/fan-control
**


<details>
<summary><h3>Translation</h3></summary>

- [简体中文](./assets/zh_CN/README_zh-CN.md)

</details>


- Open source
- Service very low on resource (~30mb)
- Display sensors data on real time
- Control fans based on custom behaviors
- Save configuration
- Multiplatform (Linux/Windows)


![mainPageV1](https://github.com/wiiznokes/LibreFanControl/assets/78230769/543af76c-137c-456d-a04e-8ebfed323178)



## Requirements
You will need some component of [dotnet 7](https://dotnet.microsoft.com/en-us/download/dotnet/7.0)

- `ASP.NET Core Runtime`
- `.NET Runtime`

You will need to run the app with admin privilege on both Linux and Windows.

If you have any troubles, check out the [FAQ](./FAQ.md).

**Issues, enhancement requests and PR are welcomed!**


## Configurations files

#### Windows
- `C:\ProgramData\LibreFanControl`
#### Linux
- `/etc/LibreFanControl`

On Linux, maybe you will need to download `lm-sensors` and execute `sensors-detect`.

## Plans (not in this order)

- [x] Support Linux
- [ ] Use type safe strings and icons ([moko-resources](https://github.com/icerockdev/moko-resources) ?)
- [ ] Add a system to automatically receive updates
- [ ] Compile with container ([docker](https://docs.docker.com/), [podman](https://podman.io/))
- [ ] Implement settings (info, help)
- [ ] Add graph behavior (abscissa -> temp, ordinate -> fan speed)
- [ ] Write an intelligent program to bind controls to their fans, and make a nice first config
- [ ] Support [Flatpak](https://docs.flatpak.org/en/latest/first-build.html)
- [ ] Upgrade service (packaging, tray icon with actions)

## Library used

### UI
- [Compose Multiplatform Desktop](https://www.jetbrains.com/lp/compose-mpp/)
### SENSORS
##### Windows
- [LibreHardwareMonitor](https://github.com/LibreHardwareMonitor/LibreHardwareMonitor)
##### Linux
- [lm-sensors](https://github.com/lm-sensors/lm-sensors)


## Licence

Can be discuss. I just want my code keep being open source.
