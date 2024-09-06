#include <esp_now.h>
#include <WiFi.h>

typedef struct message {
    int rStickX;
    int rStickY;
    int rStickClicked;
    int lStickX;
    int lStickY;
    int lStickClicked;
    int rPadClicked;
    int lPadClicked;
} message;

message myMessage;

void messageReceived(const esp_now_recv_info *info, const uint8_t* incomingData, int len){
    memcpy(&myMessage, incomingData, sizeof(myMessage));   
    Serial.print("#");
    Serial.print(myMessage.rStickX);
    Serial.print(",");
    Serial.print(myMessage.rStickY);
    Serial.print(",");
    Serial.print(myMessage.rStickClicked);
    Serial.print(",");
    Serial.print(myMessage.rPadClicked);
    Serial.print(",");
    Serial.print(myMessage.lStickX);
    Serial.print(",");
    Serial.print(myMessage.lStickY);
    Serial.print(",");
    Serial.print(myMessage.lStickClicked);
    Serial.print(",");
    Serial.print(myMessage.lPadClicked);
    Serial.print("?");
    Serial.println();
}

void setup(){
    Serial.begin(115200);
    // delay(1000); // uncomment if your serial monitor is empty
    WiFi.mode(WIFI_STA);
    
    pinMode(2, OUTPUT);
    
    if (esp_now_init() == ESP_OK) {
        Serial.println("ESPNow Init success");
        digitalWrite(2, HIGH);
    }
    else {
        Serial.println("ESPNow Init fail");
        while(true) {
          digitalWrite(2, HIGH);
          delay(75);
          digitalWrite(2, LOW);
          delay(75);
        }
    }

    esp_now_register_recv_cb(messageReceived);
}
 
void loop(){}