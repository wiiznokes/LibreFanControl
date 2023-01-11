# LibreFanControl (Alpha)

<img src="assets/mainPage.png" alt="app image">

This project aims to recreate the existing [FanControl](https://github.com/Rem0o/FanControl.Releases) application with kotlin to target the JVM and make the app cross-platform.

Windows is currently the only supported OS.
High usage of RAM can be seen for now, but it will be improve in future update I hope

------

## Feature
- Customize UI (data in real time, several kinds of items)
- Control fan (GPU fan don't work for now)
- Linear and Target behavior
- Custom sensor (average, max, ...)
- Save configuration


## Requirements
- Windows PC
- JDK with JAVA_HOME Path variable pointing to it ([JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html), [JDK 19](https://jdk.java.net/19/))


## Build
1) Compile the library (`dotnet build` doesn't work for some reason, so you'll need an editor (I personally use Rider))
2) `.\gradlew run` in FanControl folder.


## Next steps

- [ ] Publish (fix this damn StackOverFlowExecption)
- [ ] Add tests
  - [ ] UI
  - [ ] Update
- [ ] Use expect/acutal functions of Kotlin instead of the current External interface
- [ ] UI
  - [ ] Implement settings (info, help, °C or F°, launch at start up)
  - [ ] Add graph behavior (abscissa -> temp, ordinate -> fan speed)
  - [ ] Add animations
- [ ] Support Linux
- [ ] Support Nvidia GPU
- [ ] Support water cooling


## Library used

#### UI
- [Compose Multiplatform Desktop](https://www.jetbrains.com/lp/compose-mpp/)
#### SENSORS
##### Windows
- [LibreHardwareMonitor](https://github.com/LibreHardwareMonitor/LibreHardwareMonitor)
- [Nvidia api wrapper](https://github.com/falahati/NvAPIWrapper)
##### Linux
- [lm-sensor](https://github.com/lm-sensors/lm-sensors)

## Remove app
On Windows, there is a script you can use to remove libs copied inside the JVM, because they will not be removed automatically.

  
<details>
<summary>Help to develop</summary>
<br/>
  
> LibreHardwareMonitor [implementation](https://github.com/lich426/FanCtrl) in C#

> Github of [compose-desktop](https://github.com/JetBrains/compose-jb)

</details>

