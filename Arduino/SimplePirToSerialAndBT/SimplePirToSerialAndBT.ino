//This example code is in the Public Domain (or CC0 licensed, at your option.)
//By Evandro Copercini - 2018
//
//This example creates a bridge between Serial and Classical Bluetooth (SPP)
//and also demonstrate that SerialBT have the same functionalities of a normal Serial

#include "BluetoothSerial.h"

#if !defined(CONFIG_BT_ENABLED) || !defined(CONFIG_BLUEDROID_ENABLED)
#error Bluetooth is not enabled! Please run `make menuconfig` to and enable it
#endif

BluetoothSerial SerialBT;
const int pin = 5; // broche du capteur PIR
int pirState = LOW; //etat du sensor
int buttonState = 0; // etat de la sortie du capteur
 
void setup()
{
  pinMode(pin,INPUT);
  Serial.begin(9600);
  SerialBT.begin("ESP32paul"); //Bluetooth device name
  Serial.println("The device started, now you can pair it with bluetooth!");
}
 
void loop()
{
 
   buttonState = digitalRead(pin);//lecture du capteur
 
  if(buttonState == HIGH){ //si quelquechose est detecte
    if (pirState == LOW) {
      // we have just turned on
      String msg = " Presence detected";
      Serial.println(msg);
      SerialBTWriteString(msg);
      Serial.println("données envoyées");
      // We only want to print on the output change, not state
      pirState = HIGH;
    }
    
    
  }

  else //sinon
  {
    if (pirState == HIGH){
      // we have just turned of
      String msg = " Motion ended";
      Serial.println(msg);
      SerialBTWriteString(msg);
      Serial.println("données envoyées");  
      // We only want to print on the output change, not state
      pirState = LOW;
    }
  }

  if (Serial.available()) { //Read serial port to send via BLE data
  String stringSerial = Serial.readString();
  SerialBTWriteString(stringSerial);
  Serial.println("données envoyées");
  }
  
  if (SerialBT.available()) { //Read BLE to send via serial port data
    Serial.println("données reçues");
    String stringBTSerial = SerialBT.readString();
    SerialWriteString(stringBTSerial);
    Serial.println("");
    Serial.println("Fin");
  }

  delay(6000);

}

void SerialWriteString(String stringData) { // Used to serially push out a String with Serial.write()

  for (int i = 0; i < stringData.length(); i++)
  {
    Serial.write(stringData[i]);   // Push each char 1 by 1 on each loop pass
  }

}// end writeString

void SerialBTWriteString(String stringData) { // Used to serially push out a String with Serial.write()

  for (int i = 0; i < stringData.length(); i++)
  {
    SerialBT.write(stringData[i]);   // Push each char 1 by 1 on each loop pass
  }

}// end writeString
