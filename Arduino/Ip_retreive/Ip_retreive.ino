#include <tcpip_adapter.h>

void setup() {
  // put your setup code here, to run once:
  tcpip_adapter_ip_info_t ipInfo; 
  char str[256];
      
  // IP address.
  tcpip_adapter_get_ip_info(TCPIP_ADAPTER_IF_STA, &ipInfo);
  sprintf(str, "%x", ipInfo.ip.addr);
  printf("My IP: " IPSTR "\n", IP2STR(&ipInfo.ip));
}

void loop() {
  // put your main code here, to run repeatedly:

}
