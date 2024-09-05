#include <esp_now.h>
#include <WiFi.h>

typedef struct message {
    int rStickX;
    int rStickY;
    bool rStickClicked;
    int lStickX;
    int lStickY;
    bool lStickClicked;
    bool rPadClicked;
    bool lPadClicked;
} message;

message myMessage;

void messageReceived(const esp_now_recv_info *info, const uint8_t* incomingData, int len){
    memcpy(&myMessage, incomingData, sizeof(myMessage));   
    Serial.print("Right Stick X: ");
    Serial.println(myMessage.rStickX);
    Serial.print("Right Stick Y: ");
    Serial.println(myMessage.rStickY);
    Serial.print("Right Stick Button: ");
    Serial.println(myMessage.rStickClicked);
    Serial.print("Right Pad: ");
    Serial.println(myMessage.rPadClicked);
    Serial.print("Left Stick X: ");
    Serial.println(myMessage.lStickX);
    Serial.print("Left Stick Y: ");
    Serial.println(myMessage.lStickY);
    Serial.print("Left Stick Button: ");
    Serial.println(myMessage.lStickClicked);
    Serial.print("Left Pad: ");
    Serial.println(myMessage.lPadClicked);
    Serial.println();
}

void setup(){
    Serial.begin(115200);
    // delay(1000); // uncomment if your serial monitor is empty
    WiFi.mode(WIFI_STA);
    
    if (esp_now_init() == ESP_OK) {
        Serial.println("ESPNow Init success");
    }
    else {
        Serial.println("ESPNow Init fail");
        return;
    }

    esp_now_register_recv_cb(messageReceived);
}
 
void loop(){}