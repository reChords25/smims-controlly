#include <ESP8266WiFi.h>
#include <espnow.h>

// Callback function that will be executed when data is received
void onDataRecv(uint8_t *mac_addr, uint8_t *data, uint8_t len) {
  // Print the received data
  Serial.print("\nReceived data: ");
  Serial.print((char *)data);
  
  // // Extract button states
  // String dataStr = String((char *)data);
  // int button1State = dataStr.charAt(1) - '0'; // Convert '0' or '1' to integer
  // int button2State = dataStr.charAt(3) - '0'; // Convert '0' or '1' to integer
}

void setup() {
  pinMode(16, OUTPUT);                                        
  Serial.begin(115200);

  WiFi.mode(WIFI_STA);

  if (esp_now_init() != 0) {
    Serial.println("ESP-NOW Initialization failed");
    return;
  }

  esp_now_set_self_role(ESP_NOW_ROLE_SLAVE);
  esp_now_register_recv_cb(onDataRecv);
}

void loop() {
  
}
