DOCKER IMAGE
============

Image configuration
-------------------
This container exposes 
 * HTTP binding on port 8282
 * COAP binding on port 5684

The following folders are exposed as Docker volumes:
 * /mncse/configurations - hold SDT IPE configurations
 * /mncse/configuration - hold INCSE configuration.



Build image
-----------
docker build -t org.eclipse.om2m.site.mncse .


Run container
-------------
docker run --rm -it -p 8282:8282 org.eclipse.om2m.site.mncse 
Optionnally, you can mount volumes /mncse/configurations & /mncse/configuration


Build with maven
----------------
Docker image can be built with maven. Enable "docker" profile:
mvn clean install -Pdocker