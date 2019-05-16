/*
 Basic MQTT example

 This sketch demonstrates the basic capabilities of the library.
 It connects to an MQTT server then:
  - publishes "hello world" to the topic "outTopic"
  - subscribes to the topic "inTopic", printing out any messages
    it receives. NB - it assumes the received payloads are strings not binary

 It will reconnect to the server if the connection is lost using a blocking
 reconnect function. See the 'mqtt_reconnect_nonblocking' example for how to
 achieve the same result without blocking the main loop.
 
*/
#include <time.h>
#include <WiFi.h>
#include <PubSubClient.h>

// Replace the next variables with your SSID/Password combination
const char* ssid = "LPiOTIA";
const char* password = "";
const int pin = 5; // broche du capteur PIR
int pirState = LOW; //etat du sensor
int buttonState = 0; // etat de la sortie du capteur

// Add your MQTT Broker IP address, example:
//const char* mqtt_server = "192.168.1.144";
const char* mqtt_server = "10.0.4.7";

WiFiClient espClient;
PubSubClient client(espClient);
long lastMsg = 0;
char msg[50];
int value = 0;

void callback(char* topic, byte* message, unsigned int length) {
  Serial.print("Message arrived on topic: ");
  Serial.print(topic);
  Serial.print(". Message: ");
  String messageTemp;
  
  for (int i = 0; i < length; i++) {
    Serial.print((char)message[i]);
    messageTemp += (char)message[i];
  }
  Serial.println();
}

void setup() {
  Serial.begin(115200);
  setup_wifi();
  pinMode(pin,INPUT);
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
}

void setup_wifi() {
  delay(10);
  // We start by connecting to a WiFi network
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}


void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Attempt to connect
    if (client.connect("arduinoPresence")) {
      Serial.println("connected");
      // Once connected, publish an announcement...
      client.publish("/oneM2M/req/AE_arduinoPresence/mn-cse/json","hello world");
      // ... and resubscribe
      client.subscribe("/oneM2M/req/AE_arduinoPresence/mn-cse/json");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}


void loop()
{
  if (!client.connected()) {
    reconnect();
  }
  client.loop();
  char received[8];
  int i = 0;
  if (Serial.available())
  {
    while (Serial.available() > 0){
      received[i] = Serial.read();
      i++;
    }
    
    client.publish("outTopic", received);
  }

  buttonState = digitalRead(pin);//lecture du capteur
 
  if(buttonState == HIGH){ //si quelquechose est detecte
    if (pirState == LOW) {
      // we have just turned on
      String msg = " Presence detected";
      Serial.println(msg);
      Serial.println("données envoyées");
      // We only want to print on the output change, not state
      pirState = HIGH;
      //client.publish("pres","detected");
      int msgLen = 0;
      time_t timer;
      time(&timer);
      msgLen = String("{\"m2m:rqp\":{\"m2m:fr\":\"admin:admin\",\"m2m:to\":\"/mn-cse/cnt-542942786\",\"m2m:op\":1,\"m2m:rqi\":123456,\"m2m:pc\":{\"m2m:cin\":{\"cnf\":\"message\",\"con\":\"detected "+String(timer)+"\"}},\"m2m:ty\":4}}").length();
      Serial.println(msgLen);
      client.beginPublish("/oneM2M/req/AE_arduinoPresence/mn-cse/json", msgLen, false);
      client.print("{\"m2m:rqp\":{\"m2m:fr\":\"admin:admin\",\"m2m:to\":\"/mn-cse/cnt-542942786\",");
      client.print("\"m2m:op\":1,\"m2m:rqi\":123456,\"m2m:pc\":{\"m2m:cin\":{\"cnf\":\"message\",\"");
      client.print("con\":\"detected "+String(timer)+"\"}},\"m2m:ty\":4}}");
      Serial.println(client.endPublish());
    }
    
    
  }

  else //sinon
  {
    if (pirState == HIGH){
      // we have just turned of
      String msg = " Motion ended";
      Serial.println(msg);
      client.publish("pres", "no one");
      Serial.println("données envoyées");  
      // We only want to print on the output change, not state
      pirState = LOW;
    }
  }
}
