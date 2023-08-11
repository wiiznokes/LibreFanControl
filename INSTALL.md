# Linux Installation

0. Use the right package manager install command for **your distrubution**:
 - Debian/Ubuntu : `sudo apt install <packages>`
 - Arch Linux : `sudo pacman -S <packages>`
 - Centos/Redhat/Fedora : `sudo dnf install <packages>`
Replace `(install command)` below to your command above.

1. Open terminal and change directory to a place that you want to **place LibreFanControl under it**, move tar.gz archive here.  
( And make sure you have its rw permission )
```
# e.g.
cd /opt
mv /path/to/downloaded/LibreFanControl.tar.gz ./
```

2. Extract archive in the current directory
```
tar -xzf LibreFanControl.tar.gz -C ./
```
3. Install dependencies
```
(install command) aspnetcore-runtime-7.0
```

4. (optional) install `lm_sensors` and execute `sensors-detect`.
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

5. You all good, you can execute the app in the place you went to it at step 0.
```
sudo ./LibreFanControl/bin/LibreFanControl
```
