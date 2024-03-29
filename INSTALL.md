# Linux Installation

<details>
<summary><h3>Translation</h3></summary>

- [简体中文](./assets/zh_CN/INSTALL_zh-CN.md)

</details>


### Requirements
Use the right package manager to install command for **your distrubution**:
 - Debian/Ubuntu : `sudo apt install <packages>`
 - Arch Linux : `sudo pacman -S <packages>`
 - Centos/Redhat/Fedora : `sudo dnf install <packages>`
 
Replace `(install command)` below to your command above.


### 1. Extract archive in the directory you want to place LibreFanControl, ex: `/opt`
```
sudo tar -xzf LibreFanControl.tar.gz -C /opt
```
Note: If you had already installed the app before, make sure to remove the directory before to avoid problem:
```
sudo rm -rf /opt/LibreFanControl
```
### 2. Install dependencies
```
(install command) aspnetcore-runtime-7.0
```

### 3. (optional) install `lm_sensors` and execute `sensors-detect`.
```
(install command) lm_sensors
```
```
sudo sensors-detect
```
This will generate a config file to tell LibreFanControl app how to talk to sensors.  
It will also automatically found modules (drivers) you need to load.  
Personally, I answer YES to all the questions and I have no problem.  

Notes: 
- The uppercase answer is the default one.
- After this step on my PC, I go from 3 to 25 sensors detected.

### 4. You all good, you can execute the app that in the place you selected first.
```
sudo /opt/LibreFanControl/bin/LibreFanControl
```
