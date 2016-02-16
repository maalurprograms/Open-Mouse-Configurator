# Open-Mouse-Configurator
A mousebutton configuration program. Only for Linux

You need to install the following tools:
```sudo apt-get install xbindkeys xautomation xev```
  
Link to the Website:
  http://askubuntu.com/questions/152297/how-to-configure-extra-buttons-in-logitech-mouse

The configuration  File is ```~/.xbindkeysrc```.

#Documentation for xbindkey

Use Python2. Will maybe upgraded to Python3

You need (obviously) ```xbindkey``` and ```xautomation```.

Use: ```sudo apt-get install xbindkey xautomation```

Get mouse button number with ```"xev |grep button"```

Example:
```
python /home/jonas/.xbindkeyconf/sendkey.py Control_L+S
  b:1
```

This will make Ctrl + S if clicked on left mouse button.

After editing kill process with ```killall xbindkey```
and then restart with ```xbindkey```

Documentation: http://linux.die.net/man/1/xte
