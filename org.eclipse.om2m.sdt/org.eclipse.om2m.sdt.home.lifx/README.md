# LIFX SDT Basedriver

The LIFX SDT basedriver reifies as SDT Device OSGi every LIFX devices.
Both cloud and lan mode are possibles.

## SDT Device & Modules OSGi services
Each LIFX light is proxyfied through
 * a org.eclipse.om2m.sdt.home.devices.Light SDT Device OSGi service.
 * a org.eclipse.om2m.sdt.home.modules.BinarySwitch SDT Module OSGi service.
 * a org.eclipse.om2m.sdt.home.modules.Colour SDT Module OSGi service


## Cloud mode
Use the LIFX cloud to get access to the LIFX lights. Your LIFX lights need to accessible through the LIFX Cloud.

Please add the following properties in lifx.basedriver.properties file to enable cloud mode :
```
mode cloud
cloud.token **tokenRetrievedThroughLIFXCloud**
```

## Lan mode
Lan mode is used to get access to your LIFX light located in your LAN. 

Please add the following properties in lifx.basedriver.properties file to enable lan mode :
```
mode lan
network.interface.name 10.0.1.1
```
network.interface property specifies which network interface the LIFX SDT basedriver should use to get access to the lights.