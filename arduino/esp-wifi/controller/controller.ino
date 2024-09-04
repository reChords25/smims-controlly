#include <ESP8266WiFi.h>
#include <espnow.h>

// Define button pins
const int buttonPin1 = 5; // GPIO5
const int buttonPin2 = 4; // GPIO4

// Define peer MAC address (receiver's Wi-Fi MAC)
uint8_t receiverMAC[] = {0x48, 0x55, 0x19, 0x7A, 0x94, 0x8B}; // Replace with receiver's MAC address

// Callback when data is sent
void onDataSent(uint8_t *mac_addr, uint8_t sendStatus) {
  Serial.print("Send Status: ");
  Serial.println(sendStatus == 0 ? "Success" : "Fail");
}

void setup() {
  Serial.begin(115200);

  // Initialize button pins
  pinMode(buttonPin1, INPUT_PULLUP);
  pinMode(buttonPin2, INPUT_PULLUP);

  // Initialize Wi-Fi in Station Mode
  WiFi.mode(WIFI_STA);

  // Initialize ESP-NOW
  if (esp_now_init() != 0) {
    Serial.println("ESP-NOW Initialization failed");
    return;
  }

  // Set the ESP-NOW role
  esp_now_set_self_role(ESP_NOW_ROLE_CONTROLLER);

  // Register the receiver peer
  esp_now_add_peer(receiverMAC, ESP_NOW_ROLE_SLAVE, 1, NULL, 0);

  // Register the send callback
  esp_now_register_send_cb(onDataSent);
}

void loop() {
  // Read button states
  int button1State = digitalRead(buttonPin1);
  int button2State = digitalRead(buttonPin2);

  // Create a data packet to send
  String data = "#";
  data += button1State == LOW ? "1," : "0,";
  data += button2State == LOW ? "1?" : "0?";

  // Convert the string to a char array
  char dataToSend[20];
  data.toCharArray(dataToSend, 20);

  // Send the data
  esp_now_send(receiverMAC, (uint8_t *)dataToSend, sizeof(dataToSend));

  delay(5); //1000 / delayTime = polling rate
}
