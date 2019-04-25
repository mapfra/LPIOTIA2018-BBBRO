//This example code is in the Public Domain (or CC0 licensed, at your option.)
//By Evandro Copercini - 2018
//
//This example creates a bridge between Serial and Classical Bluetooth (SPP)
//and also demonstrate that SerialBT have the same functionalities of a normal Serial

#include "BluetoothSerial.h"
#include <WiFi.h>

#if !defined(CONFIG_BT_ENABLED) || !defined(CONFIG_BLUEDROID_ENABLED)
#error Bluetooth is not enabled! Please run `make menuconfig` to and enable it
#endif

BluetoothSerial SerialBT;

void setup() {
  Serial.begin(115200);
  SerialBT.begin("ESP32paul"); //Bluetooth device name
  Serial.println("The device started, now you can pair it with bluetooth!");
}

void loop() {
  if (Serial.available()) {
    String stringSerial = Serial.readString();
    SerialBTWriteString(stringSerial);
    Serial.println(WiFi.localIP());
  }
  if (SerialBT.available()) {
    String stringBTSerial = SerialBT.readString();
    SerialWriteString(stringBTSerial);
    SerialBTWriteString(stringBTSerial);
  }
  delay(20);
}

void SerialBTWriteString(String stringData) { // Used to serially push out a String with Serial.write()

  for (int i = 0; i < stringData.length(); i++)
  {
    SerialBT.write(stringData[i]);   // Push each char 1 by 1 on each loop pass
  }
}// end writeString

void SerialWriteString(String stringData) { // Used to serially push out a String with Serial.write()

  for (int i = 0; i < stringData.length(); i++)
  {
    Serial.write(stringData[i]);   // Push each char 1 by 1 on each loop pass
  }

}// end writeString
