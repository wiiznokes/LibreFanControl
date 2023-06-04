# LibreFanControl FAQ


> 什么是 `Disable control value` ?

这是一个只会在 Linux 上有效果的设置。风扇控制可以有多种模式。 LibreFanControl 将他设为手动模式，但当禁用手动控制时，用户可以选择他们期望的控制模式。

举个例子，这是我的芯片的驱动[网站](https://www.kernel.org/doc/html/next/hwmon/nct6775.html).
```
pwm[1-7]_enable
        这个文件控制着 风扇/温度 控件的模式:
                0 禁用风扇控制 (风扇被设为满速运行)
                1 手动模式, 向 pwm[0-5] 写入0~255的任何值
                2 "热巡航" 模式
                3 "风扇速度巡航" 模式
                4 "智能风扇 III" 模式 (仅NCT6775F)
                5 "智能风扇 IV" 模式
```

