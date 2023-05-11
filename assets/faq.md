# LibreFanControl FAQ


> What is `Disable control value ?

This is a setting that will only have an effect on Linux. Fan control can have several modes. FanControl puts it in manual mode, but when disabling manual control, the user can choose the desired control mode.

For example, here is the driver [site](https://www.kernel.org/doc/html/next/hwmon/nct6775.html) for my chip.
```
pwm[1-7]_enable
        this file controls mode of fan/temperature control:
                0 Fan control disabled (fans set to maximum speed)
                1 Manual mode, write to pwm[0-5] any value 0-255
                2 "Thermal Cruise" mode
                3 "Fan Speed Cruise" mode
                4 "Smart Fan III" mode (NCT6775F only)
                5 "Smart Fan IV" mode
```

