
# [SMIMS](http://www.smims.nrw.de) Controlly

The project files for the 2024's NRW SMIMS week, in which 100 students from all over NRW worked on different mathematic or informatic projects, that reach far above what is covered in school.

## The project
The task of our project was to develop a game controller. And that was the **only** thing we were told to do.  
We were allowed to use all the things we wanted to use and make anything we wanted, as long as it fitted the project's name.

## The plan
### Hardware
For the hardware we chose to go with the [Arduino](https://www.arduino.cc/) platform. Arduinos are small microcontrollers, that can read data from sensors, run small displays, motors and even more complex things such as a whole robot. They have to be custom programmed in a language derived from [C](https://en.wikipedia.org/wiki/C_(programming_language)), called the [Arduino Programming Language](https://www.arduino.cc/reference/en/). Very pragmatic, surely.  
You connect the Arduino to your PC via USB, which has another quite handy feature: **The serial monitor.** For our controller to work, we had to transfer the input data from our joysticks and buttons (which we connected to the Arduino) to the PC to play games with it. And to send data back, e.g. for LEDs or vibration signals, as well. We were able to accomplish that by sending data between the two devices as strings with the help of the serial connection. This allowed for fast and reliable data transfer, which is the basis for a good controller.

### Software
For the software, after long-enduring discussions (there was the idea to write a Mineraft Mod) we settled on using the Java [SaS library](http://dingemann.de/sas/) based on Swing as graphics library. SaS is a *really* simple game / general graphics library developed for German students to learn Java with. Although it is quite limited, SaS in the end offered all we needed for a simple game framework with some simple games.  
To handle the serial connection between the computer and the Arduino we used the [JSSC library](https://github.com/java-native/jssc).


## How it went...
We were quite unlucky. Although the game development process ran very smoothly and we had a working framework soon, the hardware side of things was our end boss. We decided to switch to **ESP8266** modules as the controller's heart, because they have built-in 2.4GHz communication for us to use. Be honest: A selfmade wireless controller is much cooler than one with a USB 2.0 type B cable...  
It was not really hard to get the wireless connection to work, but only then we found out our joysticks would not work with these modules - so we had to switch to their bigger brothers, known under the name **ESP32**. They come with a stronger and more capable processor, as well as more analog input pins (which we had way too few of before). It took a huge amount of time to transfer the "wireless code" to the new modules, mainly due to differences in programming and our boards somewhat identifying as a display. That is also why we had to re-flash the firmware to the ESPs, in a software consisting 50% of chinese. *Yay.*  
The time ran like Usain Bolt himself, leaving us really stressed and working until 5 o'clock in the morning soldering our components together. But the worst thing was yet to come: After destroying one ESP while soldering and somehow finding another working one amongst all the "dead", that one died as well - ***right before our presentation***.  


# And now?
Maybe the project will be finished someday. The games work already, but one can clearly see we did not have the time to spend on design, at least for some part of the game. That is also something to be fixed in the future.  

**But until then: Stay healthy, coders!**
