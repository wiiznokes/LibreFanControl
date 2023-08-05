1. extract archive in the current directory
```
tar -xzf LibreFanControl.tar.gz -C ./
```
2. install dependencies
```
sudo dnf install aspnetcore-runtime-7.0
```

3. (optional) install lm_sensors
```
sudo dnf install lm_sensors
```
execute sensors-detect.
```
sudo sensors-detect
```
This will generate a config file to tell LibreFanControl app how to talk to sensors.
It will also automatically found modules (drivers) you need to load.
Personally, I answer YES to all the questions and I have no problem.
(Note: The uppercase answer is the default one)

4. You all good, you can execute the app
```
sudo ./LibreFanControl/bin/LibreFanControl
```
