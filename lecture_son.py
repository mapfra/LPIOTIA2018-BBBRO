# -*- coding: utf-8 -*-
"""
Éditeur de Spyder

Ceci est un script temporaire.
"""

from bluetooth import*
from pygame import mixer
import socket
import time
i = 0
mixer.init()
sound = mixer.Sound('/home/pi/Music/287.wav')
x = "\r\n"
data = b''
client_socket=BluetoothSocket(RFCOMM)
client_socket.connect(("80:7D:3A:C8:22:62",1))
while True:    
    size = 1
    data = data + client_socket.recv(size)   
    if len(data.decode().split("\r\n")) > 1:
        print(data.decode().split("\r\n")[-2])
        data=b''
        sound.play()
client_socket.close() 