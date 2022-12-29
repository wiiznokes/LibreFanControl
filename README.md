# LibreFanControl (Work in progress)

This project aims to recreate the existing [FanControl](https://github.com/Rem0o/FanControl.Releases) application with kotlin to target the JVM and make the app cross-platform.

Windows is currently the only supported OS.

To build the project, you first need to compile the Windows library :
- /!\ C# build output must be in the lib folder of the JDK
- I'm using the [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) but other can work too. [JDK 19](https://jdk.java.net/19/)


To run the project, just use `.\gradlew run` in FanControl folder.

Next steps :
- fixes bugs
- fixe Windows Lib (the current way of fetching String may cause memory leaks)
- publish (add lib folder to executable and conf folder)
- implement settings (language, time between update, theme, support the project, ...)
- finalize UI (colors, size, fan speed visible on behaviors, ...)
- support Linux
- support Nvidia GPU
- add behaviors(graph, ...)
- add some tests


<details>
  <summary>Library</summary>

#### &emsp;UI
- [Compose Multiplatform Desktop](https://www.jetbrains.com/lp/compose-mpp/)
  
#### &emsp;SENSORS

##### &emsp;Windows
- [LibreHardwareMonitor](https://github.com/LibreHardwareMonitor/LibreHardwareMonitor)
- [Nvidia api wrapper](https://github.com/falahati/NvAPIWrapper)

##### &emsp;Linux
- [lm-sensor](https://github.com/lm-sensors/lm-sensors)

</details>
  
<details>
<summary>Help to develop</summary>
<br/>
  
> LibreHardwareMonitor [implementation](https://github.com/lich426/FanCtrl) in C#

> Github of [compose-desktop](https://github.com/JetBrains/compose-jb)

</details>

