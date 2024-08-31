// speichern des am Anschluss A0 gelesenen Werts
int ReglerWert;

// Potentiometer am Anschluss A0
int REGLER = A0;

// LED am Anschluss D1
int LED = 6;

// String, den wir später an den PC senden.
String data = "";

void setup()
{
  pinMode(LED, OUTPUT);  
  Serial.begin(9600);
}

void loop()
{ 
  // Wert des Potentiometers lesen
  ReglerWert = analogRead(REGLER); 

  // LED einschalten
  digitalWrite(LED, HIGH);

  // LED für die Dauer des gelesenen Werts eingeschaltet lassen
  delay(ReglerWert);

  // LED ausschalten
  digitalWrite(LED, LOW);

  // LED für die Dauer des gelesenen Werts ausgeschaltet lassen
  delay(ReglerWert);

  // -------------------------------
  data = "";
  int wert = analogRead(REGLER);
  data = wert + ";";
  Serial.println(wert);
  Serial.flush();
}