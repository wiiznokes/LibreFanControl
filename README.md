# LibreFanControl (Work in progress)

This project aims to recreate the existing [FanControl](https://github.com/Rem0o/FanControl.Releases) application with kotlin to target the JVM and make the app cross-platform.

Windows is currently the only supported OS.

To build the project, you first need to compile the Windows library :
- /!\ C# build output must be in the lib folder of the JDK
- Cpp build output will be in "lib" folder of the Kotlin App
- I'm using the [JDK 19](https://jdk.java.net/19/) but other can work to


To run the project, just use `.\gradlew run` in FanControl folder.

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

[aimed design](https://www.figma.com/community/file/1173080950914294355)

