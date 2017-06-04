# raspberry pi libreary for lcd(HD44780) with 12c backpack in java
How to setup:

1. download pi4j from http://pi4j.com/ and insall it on your pi.
2. add librearys
2. put Lcd.java it to your project and use it. (Remmember to change the i2c adress to your module adress)
3. compile it
4. execute it on pi with pi4j classpath, for example: sudo java -cp "example.jar:/opt/pi4j/lib/*" com.example.Main
where example.jar is jour compiled jar and com.example.Main is your main class(sudo is required!)
5. have fun!

Note: exaple is for 4x20 lcd
