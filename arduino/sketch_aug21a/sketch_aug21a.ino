const byte KnopfA = 2;     // Die Variablen werden deklariert
const byte KnopfB = 3;     // Wir verwenden die Deklaration "const byte", da die einzelnen Knöpfe konstant ("const") mit den jeweiligen Pins verbunden werden
const byte KnopfC = 4;
const byte KnopfD = 5;
const byte KnopfE = 6;
const byte KnopfF = 7;
const byte KnopfK = 8;
const byte PinJoystickX = 0;
const byte PinJoystickY = 1;

int A;  // Festlegung der Variablen in den Grundzustand "false" - der entsprechende Taster ist um Grundzustand nicht betätigt
int B;
int C;
int D;
int E;
int F;
int K;
int JoystickX;
int JoystickY;

String normaliseData = ("");

void setup() // Hier beginnt das Setup
{  
  Serial.begin(9600);
  pinMode(KnopfA, INPUT);   // Knopf A dient nun als Eingang und ...
  digitalWrite(KnopfA, HIGH);  // ... wird in den Status "HIGH" versetzt
  pinMode(KnopfB, INPUT);
  digitalWrite(KnopfB, HIGH);
  pinMode(KnopfC, INPUT);
  digitalWrite(KnopfC, HIGH);
  pinMode(KnopfD, INPUT);
  digitalWrite(KnopfD, HIGH);
  pinMode(KnopfE, INPUT);
  digitalWrite(KnopfE, HIGH);
  pinMode(KnopfF, INPUT);
  digitalWrite(KnopfF, HIGH);
}

void loop() {
  JoystickX = analogRead(PinJoystickX);
  JoystickY = analogRead(PinJoystickY);
  A = digitalRead(KnopfA);
  B = digitalRead(KnopfB);
  C = digitalRead(KnopfC);
  D = digitalRead(KnopfD);
  E = digitalRead(KnopfE);
  F = digitalRead(KnopfF);
  K = digitalRead(KnopfK);
  
  normaliseData = ("@"+String(JoystickX)+";"
                      +String(JoystickY)+";"
                      +String(A)+";"
                      +String(B)+";"
                      +String(C)+";"
                      +String(D)+";"
                      +String(E)+";"
                      +String(F)+";"
                      +String(K)+"#");
  Serial.println(normaliseData);
  Serial.flush();
  delay(10);
  

}
