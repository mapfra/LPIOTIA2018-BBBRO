# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""



import paho.mqtt.client as mqtt
import time
import picamera
serv = "10.0.4.7"
host = 1883

 
def on_connect(client, userdata, flags, rc):
    print('Connected')
    mqtt.subscribe("pres")
 
timestr = time.strftime("%Y%m%d-%H%M%S")

def on_message(client, userdata, msg):
   
    if msg.payload.decode() == "detected":
        print(msg.payload.decode())
        time.sleep(5)
        
        with picamera.PiCamera() as camera:
         camera.resolution = (1024, 768)
         camera.start_preview()
         camera.capture('foo' + timestr + '.jpg')
         time.sleep(30)
         
        
mqtt = mqtt.Client()
mqtt.on_connect = on_connect
mqtt.on_message = on_message
mqtt.connect(serv, host)
mqtt.loop_forever()


