# LibreFanControl

 [English](../../README.md) README.

- 开源
- 服务资源占用小 (约30mb)
- 实时显示传感器数据
- 基于 自定义行为 来控制风扇
- 保存配置
- 多平台支持(Linux/Windows)


![mainPageV1](https://github.com/wiiznokes/LibreFanControl/assets/78230769/543af76c-137c-456d-a04e-8ebfed323178)



## 需求
你需要 [.NET 7](https://dotnet.microsoft.com/en-us/download/dotnet/7.0) 的一些组件:

- `ASP.NET Core Runtime`
- `.NET Runtime`

在 `Windows` 和 `Linux` 上，你都需要以管理员权限运行此应用。

如果有其它问题，请去往 [常见问题 - FAQ](./FAQ_zh-CN.md) 。

**欢迎提 Issues，功能建议 和 PR!**


## 配置文件

#### Windows
- `C:\ProgramData\LibreFanControl`
#### Linux
- `/etc/LibreFanControl`

在Linux上，你也许需要下载`libsensors`并执行 `sensors-detect`。


## 构建
- 在Windows上，如果你想构建这个项目，你只需要 [.NET 7](https://dotnet.microsoft.com/en-us/download/dotnet/7.0) 的`SDK`。 
- 在Linux上，你需要一个[JDK](https://jdk.java.net/java-se-ri/17).

构建所需命令可以在 `CI` 目录里找到。


## 未来计划 (无顺序)

- [x] 支持 Linux
- [ ] 使用类型安全字符串和图标 ([moko-resources](https://github.com/icerockdev/moko-resources) ?)
- [ ] 添加自动接收更新的系统
- [x] 添加工作流到项目 (CI/CD,etc.) ([docker](https://docs.docker.com/) ?) ( GitHub Actions √)
- [ ] 完全实现 `设置` (信息，帮助)
- [ ] 添加「图表」行为 (横坐标 -> 温度，纵坐标 -> 风扇速度)
- [ ] 自动将控制绑定到对应风扇速度的程序，并预写一个好的初始配置。
- [ ] 支持 [Flatpak](https://docs.flatpak.org/en/latest/first-build.html)

## 使用的库

### UI
- [Compose Multiplatform Desktop](https://www.jetbrains.com/lp/compose-mpp/)
- [setting-sliding-windows](https://github.com/wiiznokes/setting-sliding-windows)
### 传感器
##### Windows
- [LibreHardwareMonitor](https://github.com/LibreHardwareMonitor/LibreHardwareMonitor)
##### Linux
- [lm-sensors](https://github.com/lm-sensors/lm-sensors)
