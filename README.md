# LibreFanControl (Alpha)

<img src="assets/mainPage.png" alt="app image">

------


## Notes
Windows is currently the only supported OS. I will support Linux soon, but for now, I'm not able to control the fans of my pc.

I intend to produce a v2 with a service system, which will therefore be much lighter on resources. The service will be in charge of fetching datas, calculate the % for fans controlling, based on the configuration made with the app and set controls values. Communication between the service and the app will be powered by gRPC, a powerfull protocol used in microservice.

## v2 roadMap
- [x] upgrade setting apparence (switch to compose 1.3)
- [x] calcule % locally (in the app)
- [x] generate kotlin code from .proto files
- [x] serialize, deserialize and store confs/setting using proto in Kotlin
- [x] implement setting and config management in Kotlin
- [x] generate C# code from .proto files
- [x] deserialize confs/setting in C#
- [ ] communicate between C# and Kotlin (using "service" in .proto)
- [x] find a way to store conf file in Kotlin, and being able to find that path in C# (must work on Linux either)
- [ ] find a way to start the service automatically
- [ ] think about the structure of the service, and define the stable API
- [ ] integrate the service with Kotlin

## Feature
- Customize UI (data in real time, several kinds of items)
- Control fans
- Linear and Target behavior
- Custom sensor (average, max, ...)
- Save configuration



## Build
- proto
```
.\gradlew generateProto
```
```
.\gradlew copyGeneratedFiles
```

- Lib windows (dotnet7)
```
dotnet build
```
- App
```
.\gradlew run 
```

## Next steps

- [x] Publish
- [ ] Use strongly typed resources, maybe Kotlin dataframe
- [ ] Add workflow to the project (CI/CD, ect...)
- [ ] Add a program to automatically bind controls with his fan (may change the architecture so I will do it later)
- [ ] Support Linux
- [ ] UI
  - [ ] Implement settings (info, help, launch at start up)
  - [x] Implement controls just like the rest of the app
  - [ ] Add graph behavior (abscissa -> temp, ordinate -> fan speed)
  - [x] Change the size of items, upgrade settings and add items looks
  - [x] Add animations
- [ ] Add tests
  - [ ] UI
  - [ ] Update
- [ ] Use expect/acutal functions of Kotlin instead of the current External interface (Kotlin don't support it for now)
- [ ] Support water cooling


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

> Video about grpc https://www.youtube.com/watch?v=8C-mRgffoFQ 

> grpc guide https://github.com/grpc-ecosystem/awesome-grpc#lang-java

</details>

