Created by: Simon Krüger Pedersen, Sam Emamverdikhan Hoffmann Bahadori & Marc Holmbach Henriksen

Disclaimer:
Since both server and client are in the same project, it is important to remember to open the correct project.

*Since the server dosn't have a manifest file, it cannot be run in android studio, so you should run it in an IDE such as IntelliJ.

Server:
To run the server, open the “androidServer” folder in your preferred IDE, for this example we will be using IntelliJ. Locate the main file “android_Server.kt”, which is located in src/main/kotlin, and run it.
A small window will pop up, with a “reveal” button. Once the button is clicked, it will reveal your IP which is needed for the connection.

** If the window doesn’t show the correct IPV4 address, open up the CMD, and write “ipconfig”. It will be the IPv4 address under the Ethernet adapter Ethernet. If you have multiple IPv4 addresses due to virtuel adapters. If it is confusing go to https://www.whatismyip.com/, and pick the LOCAL IPv4.

Client:
To run the app, open the “Client” folder in Android Studio and run the “app” configuration on your preferred device. Once launched, input the IP from the server and connect. If successful a TOAST will display “Connected”. 

You can now proceed to close the soft input mode and access one of the three menu buttons.
