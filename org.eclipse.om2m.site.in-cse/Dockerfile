#******************************************************************************* 
# Copyright (c) 2014, 2016 Orange. 
# All rights reserved.This program and the accompanying materials 
# are made available under the terms of the Eclipse	Public License v1.0 
# which accompanies this distribution, and is available	at 
# http://www.eclipse.org/legal/epl-v10.html 
#
# Contributors: 
#       Sébastien BOLLE (Orange),
#       Grégory BONNARDEL (Thales for Orange), 
#       Cyrille Bareau (Orange). 
#*******************************************************************************

FROM openjdk:8-jre-alpine



# install incse
COPY target/products/in-cse/ /tmp/incse
RUN  mv /tmp/incse/*/*/* /incse
RUN chmod +x /incse/start.sh
RUN chmod +x /incse/start.bat

# expose port 8080 (http) & 5683 (coap)
EXPOSE 8080
EXPOSE 5683

WORKDIR /incse/

RUN echo "#!/bin/sh"  >> /startFromDocker.sh
RUN echo "cd /incse/ && ./start.sh" >> /startFromDocker.sh
RUN chmod +x /startFromDocker.sh

VOLUME /incse/configurations
VOLUME /incse/configuration

CMD ["/startFromDocker.sh"]
