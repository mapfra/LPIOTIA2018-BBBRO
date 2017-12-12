DOCKER IMAGE
============

Image configuration
-------------------
This container exposes 
 * HTTP binding on port 8080
 * COAP binding on port 5684

The following folders are exposed as Docker volumes:
 * /incse/configurations - hold SDT IPE configurations
 * /incse/configuration - hold INCSE configuration.



Build image
-----------

docker build -t org.eclipse.om2m.site.incse .



Run container
-------------
docker run --rm -it -p 8080:8080 org.eclipse.om2m.site.incse 
Optionnally, you can mount volumes /incse/configurations & /incse/configuration
