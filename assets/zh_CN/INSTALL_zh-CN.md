# 在Linux上安装

### 需求
使用**你的Linux发行版的**包管理器安装命令:
 - Debian/Ubuntu : `sudo apt install <包名>`
 - Arch Linux : `sudo pacman -S <包名>`
 - Centos/Redhat/Fedora : `sudo dnf install <包名>`  

将下文`(安装命令)`替换为对应命令

### 1. 打开终端，切换到压缩包所在目录，将`LibreFanControl`解压到你想安装的地方,如 `/opt`  
(确保你有这个目录的读/写权限)
```
# e.g.
tar -xzf LibreFanControl.tar.gz -C /opt
```
注：如果你以前安装过此应用，请先给卸载（删除）了：
```
# e.g.
rm -rf /opt/LibreFanControl
```

### 2. 安装依赖
```
(安装命令) aspnetcore-runtime-7.0
```

### 3. (optional) 安装 `lm_sensors` 并执行 `sensors-detect`.
```
(安装命令) lm_sensors
```
```
sudo sensors-detect
```
这将生成一个能告诉`LibreFanControl`电脑上有哪些传感器、如何读取它们 的配置文件  
它将自动寻找需要加载的模块（驱动程序）  
对作者个人来说，他执行`sensors-detect`时询问全选了`YES`并且无逝发生。

提示: 
- 英文大写的回答(`YES`)是默认选项
- 作者在他电脑上做了这步后，他能检测到的传感器从三个到了25个。

### 4. 好了，你可以运行安装在你选的目录的软件了:
```
sudo /opt/LibreFanControl/bin/LibreFanControl
```
