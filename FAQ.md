# LibreFanControl FAQ

*Translation*: [简体中文](./assets/zh_CN/FAQ_zh-CN.md)


<details>
<summary><h3>What is `Disable control value` ?</h3></summary>

This is a setting that will only have an effect on Linux. Fan control can have several modes. LibreFanControl puts it in manual mode, but when disabling manual control, the user can choose the desired control mode.

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

</details>


<details>
<summary><h3>Error while upgrading on Windows</h3></summary>

Try to kill `LibreFanControlService` with the task manager and launch .msi file. Choose install or repair option.

</details>

<details>
<summary><h3>How to install on Linux</h3></summary>

[See](./INSTALL.md).

</details>

<details>
<summary><h3>How to build</h3></summary>

[See](./BUILD.md).

</details>


