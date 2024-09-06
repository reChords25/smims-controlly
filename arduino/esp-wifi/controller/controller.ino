#include <esp_now.h>
#include <WiFi.h>

uint8_t receiverAddress[] = {0xC0, 0x49, 0xEF, 0x68, 0xA7, 0x20};// c0:49:ef:68:a7:20
esp_now_peer_info_t peerInfo;

int rStickXPin = 39;
int rStickYPin = 35;
int lStickXPin = 33;
int lStickYPin = 34;

int rStickButtonPin = 0;
int lStickButtonPin = 25;

int rPadPin = 32;
int lPadPin = 4;

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

void messageSent(const uint8_t *macAddr, esp_now_send_status_t status) {
    Serial.print("Send status: ");
    if(status == ESP_NOW_SEND_SUCCESS){
        Serial.println("Success!");
    }
    else{
        Serial.println("Error!");
    }
}

void setup(){
    analogReadResolution(10);
    analogSetAttenuation(ADC_11db);

    pinMode(rStickXPin, INPUT);
    pinMode(rStickYPin, INPUT);
    pinMode(lStickXPin, INPUT);
    pinMode(lStickYPin, INPUT);
    pinMode(rStickButtonPin, INPUT);
    pinMode(lStickButtonPin, INPUT);
    pinMode(rPadPin, INPUT);
    pinMode(lPadPin, INPUT);
    
    Serial.begin(115200);
    WiFi.mode(WIFI_STA);
    
    if (esp_now_init() == ESP_OK) {
        Serial.println("ESPNow Init success!");
    }
    else {
        Serial.println("ESPNow Init fail!");
        return;
    }
    
    esp_now_register_send_cb(messageSent);   

    memcpy(peerInfo.peer_addr, receiverAddress, 6);
    peerInfo.channel = 0;
    peerInfo.encrypt = false;

    if (esp_now_add_peer(&peerInfo) != ESP_OK) {
        Serial.println("Failed to add peer!");
        return;
    }
}
 
void loop(){
    myMessage.rStickX = analogRead(rStickXPin);
    myMessage.rStickY = analogRead(rStickYPin);
    myMessage.rStickClicked = digitalRead(rStickButtonPin);
    myMessage.lStickX = analogRead(lStickXPin);
    myMessage.lStickY = analogRead(lStickYPin);
    myMessage.lStickClicked = digitalRead(lStickButtonPin);
    myMessage.rPadClicked = touchRead(rPadPin) < 50 ? 0 : 1;
    myMessage.lPadClicked = touchRead(lPadPin) < 50 ? 0 : 1;

    Serial.println(myMessage.rStickX);

    esp_err_t result = esp_now_send(receiverAddress, (uint8_t *) &myMessage, sizeof(myMessage));
    if (result != ESP_OK) {
        Serial.println("Sending error!");
    }
    delay(10);
}