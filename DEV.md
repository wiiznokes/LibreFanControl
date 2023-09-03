The app is stuctured this way:

An UI made with compose multiplatform
A service made in C#

The main problem with Fan control apps is that there is one lib on Windows whitch can control fans, and it is written in C#.
This is very limiting because C# in a manager language (garbage collector), so it's really hard to interop with it.

Ui on C# is really bad imo. I could have used Alvalonia, but after some try, i can tell this is really a pain after comming from jetpack Compose on Android.

As i knowed Kotlin, i choosed to use compose multiplatfrom.
I first tried to interop directly with C# thanks to JNI, but this is really not easy to debug/scale
So i created a service instead.

This as several drowbacks:

- lot of complexicity is added
- packaging is hard
- the service is always listening is the app want info
- lot of techno is added (C#, grpc, proto)

But really, there is no good solution for this kind of project.

Compose has also some drowbacks
- use lot of memomy
- Gradle O:

But is fast and simple to use

After this project, i discovered Rust, and i can tell it would have been much better suited.
simple build system, no GC, crates, cargo, types system, efficient, .......
I think that for windows, this is the best we can get, using C# to make such a low level lib is very dumb imo; but for Linux, anoter project could be created using Rust and C interop for lm-sensors. I plan to maybe do that latter. Probably with just a tool to create config file, and an applet icon.


Neverless here is some infos about this app:

lm-sensor is build using a special fork on one maintener of the repo, with pwm support:
https://github.com/Wer-Wolf/lm-sensors/tree/pwm

- model: Model use to describe Items on the screen.
- settingSlidingWindows: (lib) to make a setting with sliding windows, as the name said
- proto: helper functions to use proto generated files


when the app is launch, we start the service(with a script), then we call open() (using grpc)

we get the hardware using getHardware() function

then we call startUpdate() to set a stream of values on this hardware.

when we save a configuration, we notify the service with settingsAndConfChange()

The service is responsible to calculate his values, and the app is also responsible to calculate his values (yes, i know this i bad, but as i said, this app is bad).

well, if you make it this far, thank you. But otherwise, the app is not that bad xD