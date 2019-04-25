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
const byte sensorPin = 22;


SemaphoreHandle_t syncSemaphore;

void IRAM_ATTR handleInterrupt() {
  xSemaphoreGiveFromISR(syncSemaphore, NULL);
}

void setup() {

  Serial.begin(115200);
  SerialBT.begin("ESP32paul"); //Bluetooth device name
  Serial.println("The device started, now you can pair it with bluetooth!");
  syncSemaphore = xSemaphoreCreateBinary();

  pinMode(sensorPin, INPUT_PULLUP);
  attachInterrupt(digitalPinToInterrupt(sensorPin), handleInterrupt, CHANGE);

}

void loop() {

    if (Serial.available()) {
        String stringSerial = Serial.readString();
        SerialBTWriteString(stringSerial);
        Serial.println("données envoyées");
      
      }
      
/*    xSemaphoreTake(syncSemaphore, portMAX_DELAY);

    if(digitalRead(sensorPin)){
      String msg = "Motion detected";
      Serial.println(msg);
      SerialBTWriteString(msg);
      Serial.println("Motion sent");

    }else{
      String msg = "Motion stoped";
      Serial.println(msg);
      SerialBTWriteString(msg);
      Serial.println("Stop motion sent");

    }
    
    if (SerialBT.available()) {
      Serial.println("données reçues");
      String stringBTSerial = SerialBT.readString();
      SerialWriteString(stringBTSerial);
   
    }*/
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
