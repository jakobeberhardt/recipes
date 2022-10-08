# recipes
## Installation (Ubuntu) 
64bit  Base Libraries

    sudo apt-get install libc6:i386 libncurses5:i386 libstdc++6:i386 lib32z1 libbz2-1.0:i386

Anleitung fuer [Android Studio](https://developer.android.com/studio/install#linux)

[Android Studio Download](https://developer.android.com/studio#downloads)

    sudo tar -xzvf ~/Downloads/android-studio-2021.3.1.16-linux.tar.gz -C /usr/local/

    cd /usr/local/android-studio/bin
    sudo bash studio.sh

Vorerst [Android SDK 33.0.3](https://android-sdk.en.softonic.com/)

Problem mit adb (error deamon not running):
   
    adb kill-servers
    sudo cp ~/Android/Sdk/platform-tools/adb /usr/bin/adb
    sudo chmod +x /usr/bin/adb
    adb start-server

## Android Studio 
Emulator anlegen unter *Tools* > *Device Manager* 

    Pixel Pro 6 mit API Level 33

Logs unter *View* > *Tool Windows* > *Logcat* > *Verbose*