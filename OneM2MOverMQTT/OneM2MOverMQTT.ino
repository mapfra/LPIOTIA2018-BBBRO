#include <WiFi.h>
#include <PubSubClient.h>

WiFiClient espClient;
PubSubClient client(espClient);

// les 8 variables obligatoire à remplir :

// Server MQTT
const char* mqtt_server = "??????????????????????????????????????????????????????????";
// Port MQTT (1883)
int MQTTPort = ????????????????????????????????????????????????;

const char* wifiSsid = "?????????????????????????????????????????????????????????????????";
const char* wifiPassword = "?????????????????????????????????????????????????????????????";

// Par défault, le login et le mot de pass de l'application web OneM2M sont "admin:admin"
const char* OM2MLoginPassword = "?????????????????????????????????????????????????????????";
// Destination de l'arborescence où envoyer les données (ex : /mn-cse/cnt-116673111), fonctionne peut-être avec l'arborescence plus explicite (ex : /mn-cse/AE/Salon)
const char* OM2MUrlDestination = "?????????????????????????????????????????????????????????";
// Le topic sera modifié pour être utilisé avec le protocole MQTT
const char* topic = "?????????????????????????????????????????????????????????";
// Le message sera modifié pour être utilisé avec le protocole MQTT
const char* message = "?????????????????????????????????????????????????????????";


// Construction du topic Over MQTT
const char* topicEnvoiOverMQTT = "/oneM2M/req/"+topic+"/mn-cse/json";
// Construction du topic Over MQTT
const char* topiReceptionOverMQTT = "/oneM2M/res/"+topic+"/mn-cse/json";
// Construction du message over MQTT
const char* messageEnvoiOverMQTT = "{\"m2m:rqp\":{\"m2m:fr\":\""+OM2MLoginPassword+"\",\"m2m:to\":\""+OM2MUrlDestination+"\",\"m2m:op\":1,\"m2m:rqi\":123456,\"m2m:pc\":{\"m2m:cin\":{\"cnf\":\"message\",\"con\":\""+message+"\"}},\"m2m:ty\":4}}";


void setup_wifi() {
  delay(10);
  WiFi.begin(wifiSsid, wifiPassword);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("WiFi connected");
}

void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    if (client.connect("arduinoPresence")) {
      Serial.println("connected");
    } else {
      Serial.print("connection failed");
      Serial.println(" try again in 5 seconds");
      
      delay(5000);
    }
  }
  
void setup() {
  Serial.begin(115200);
  setup_wifi();
  client.setServer(mqtt_server, MQTTPort); 
}

void loop() {

  if (!client.connected()) {
    reconnect();
  }

  //Nous avons besoin de la taille du message
  int msgLen = 0;
  msgLen = messageEnvoiOverMQTT.length();

  //Afin d'envoier des messages trop long, il faut l'envoyer en plusieurs parties (beginPublish() -> print() -> print() -> endPublish() )
  client.beginPublish(topicEnvoiOverMQTT, msgLen/*taille du message*/, false);
  client.print("{\"m2m:rqp\":{\"m2m:fr\":\""+OM2MLoginPassword+\",\"m2m:to\":\""+OM2MUrlDestination+"\",");
  client.print("\"m2m:op\":1,\"m2m:rqi\":123456,\"m2m:pc\":{\"m2m:cin\":{\"cnf\":\"message\",\"");
  client.print("con\":\""+message+"\"}},\"m2m:ty\":4}}");
  Serial.println(client.endPublish());

  delay(10000);
}