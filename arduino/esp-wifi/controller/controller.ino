#include <WiFi.h>
#include <esp_now.h>

// Define button pins
const int buttonPin = 18; // GPIO15
const int stickYPin = 19; // GPIO2
const int stickXPin = 20; // GPIO4

// Define peer MAC address (receiver's Wi-Fi MAC)
uint8_t receiverMAC[] = {0x48, 0x55, 0x19, 0x7A, 0x94, 0x8B}; // Replace with receiver's MAC address

// Callback when data is sent
void onDataSent(const uint8_t *mac_addr, esp_now_send_status_t status) {
  Serial.print("Send Status: ");
  Serial.println(status == ESP_NOW_SEND_SUCCESS ? "Success" : "Fail");
}

void setup() {
  Serial.begin(115200);

  // Initialize button pins
  pinMode(buttonPin, INPUT_PULLUP);
  pinMode(stickYPin, INPUT);
  pinMode(stickXPin, INPUT);

  // Initialize Wi-Fi in Station Mode
  WiFi.mode(WIFI_STA);

  // Initialize ESP-NOW
  if (esp_now_init() != ESP_OK) {
    Serial.println("Error initializing ESP-NOW");
    return;
  }

  // Register the send callback
  esp_now_register_send_cb(onDataSent);

  // Add the receiver's peer information
  esp_now_peer_info_t peerInfo;
  memcpy(peerInfo.peer_addr, receiverMAC, 6);
  peerInfo.channel = 0;  
  peerInfo.encrypt = false;

  // Add the peer
  if (esp_now_add_peer(&peerInfo) != ESP_OK) {
    Serial.println("Failed to add peer");
    return;
  }
}

void loop() {
  // Read button states
  int stickYState = digitalRead(stickYPin);
  int stickXState = digitalRead(stickXPin);
  int buttonState = digitalRead(buttonPin);

  // Create a data packet to send
  String data = "#";
  data += String(stickYState + ",");
  data += String(stickYState + ",");
  data += buttonState == LOW ? "1?" : "0?";


  // Convert the string to a char array
  char dataToSend[20];
  data.toCharArray(dataToSend, 20);

  // Send the data
  esp_now_send(receiverMAC, (uint8_t *)dataToSend, sizeof(dataToSend));

  delay(5); // Adjust as needed for desired polling rate
}
