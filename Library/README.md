info about service:

3 entity -> Service, App, Share setting/conf

### API

- getControls
- getFans
- getTemps

- getUpdateControls
- getUpdateFans
- getUpdateTemps

- stop
- settingHaveChange
- confHaveChange (id)

### process

if started automatically:
-> see IdConf in setting
-> load Conf if it exist, stop otherwise
-> start update until we kill the process

if started from the app:
-> start service
-> getControls, getFans, getTemps
-> load configuration

-> if conf has change -> notify the service
-> if setting has change -> notify the service




### structure
---Hardware
---api
---logic for control
---configuration (load)
