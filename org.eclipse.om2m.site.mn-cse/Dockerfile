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
COPY target/products/mn-cse/ /tmp/mncse
RUN  mv /tmp/mncse/*/*/* /mncse
RUN chmod +x /mncse/start.sh
RUN chmod +x /mncse/start.bat

# expose port 8282 (http) & 5683 (coap)
EXPOSE 8282
EXPOSE 5683

WORKDIR /mncse/

RUN echo "#!/bin/sh"  >> /startFromDocker.sh
RUN echo "cd /mncse/ && ./start.sh" >> /startFromDocker.sh
RUN chmod +x /startFromDocker.sh

VOLUME /mncse/configurations
VOLUME /mncse/configuration

CMD ["/startFromDocker.sh"]
