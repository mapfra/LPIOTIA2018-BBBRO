import paho.mqtt.client as mqtt
import time
serv = "10.0.0.4"
host = 1883

def on_connect(client, userdata, flags, rc)):
	print('Connected')
	mqtt.subscribe("Alert")
	
def on_message(client, userdata, msg):
	if msg.payload.decode())
		print(msg.payload.decode())
		time.sleep(5)
		#fonction d'envoi d'alerte sonore
		
mqtt = mqtt.client
mqtt.on_connect = on_connect
mqtt.on_message = on_message
mqtt.connect(serv, host)
mqtt.loop_forever()