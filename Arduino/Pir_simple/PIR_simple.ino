const int buttonPin = 5; // broche du capteur PIR
const int ledPin = 13; // la LED du Arduino
int buttonState = 0; // etat de la sortie du capteur
 
void setup()
{
  pinMode(ledPin, OUTPUT); //la broche de la LED est mise en sortie
  pinMode(buttonPin, INPUT); //la broche du capteur est mise en entree
  Serial.begin(9600);
}
 
void loop()
{
  buttonState = digitalRead(buttonPin);//lecture du capteur
  if (buttonState == HIGH) //si quelquechose est detecte
  {
    digitalWrite(ledPin, HIGH); //on allume la LED
    Serial.println("Detection");
  }
  else //sinon
  {
    digitalWrite(ledPin, LOW); //on eteint la LED
    Serial.println("pas de Detection");
  }
  delay(100);
}
