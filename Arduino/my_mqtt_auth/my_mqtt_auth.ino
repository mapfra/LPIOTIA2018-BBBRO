/*
 Basic MQTT example with Authentication

  - connects to an MQTT server, providing username
    and password
  - publishes "hello world" to the topic "outTopic"
  - subscribes to the topic "inTopic"
*/

#include <SPI.h>
#include <Ethernet.h>
#include <PubSubClient.h>

// Update these with values suitable for your network.
byte mac[]    = {  0x80, 0x7D, 0x3A, 0xDC, 0xA9, 0x62 };
IPAddress ip(0, 0, 0, 0);
IPAddress server(192, 168, 0, 21);

void callback(char* topic, byte* payload, unsigned int length) {
  // handle message arrived
}

EthernetClient ethClient;
PubSubClient client(server, 1883, callback, ethClient);

void setup()
{
  Ethernet.begin(mac, ip);
  // Note - the default maximum packet size is 128 bytes. If the
  // combined length of clientId, username and password exceed this,
  // you will need to increase the value of MQTT_MAX_PACKET_SIZE in
  // PubSubClient.h
  
  if (client.connect("arduinoClient", "testuser", "testpass")) {
    client.publish("outTopic","hello world");
    client.subscribe("inTopic");
  }
}

void loop()
{
  client.loop();
}
