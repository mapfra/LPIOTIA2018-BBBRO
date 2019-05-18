FROM maven:3-jdk-8

# Install Docker from Docker Inc. repositories.
RUN curl -sSL https://get.docker.com/ | sh
ENV DIND_COMMIT 3b5fac462d21ca164b3778647420016315289034
RUN set -ex; \
	wget -O /usr/local/bin/dind "https://raw.githubusercontent.com/docker/docker/${DIND_COMMIT}/hack/dind"; \
	chmod +x /usr/local/bin/dind


COPY dockerd-entrypoint.sh /usr/local/bin/

VOLUME /var/lib/docker


# install android sdk
RUN mkdir /android-sdk
WORKDIR /android-sdk
RUN wget https://dl.google.com/android/repository/sdk-tools-linux-3859397.zip
RUN unzip sdk-tools-linux-3859397.zip
ENV ANDROID_HOME /android-sdk
ENV PATH $PATH:/android-sdk/tools/bin
ENV ANDROID_PROXY=""
RUN echo "alias sdkmanager='sdkmanager $ANDROID_PROXY'" >> /root/.bashrc
RUN mkdir /root/.android
RUN touch /root/.android/repositories.cfg
RUN yes | sdkmanager $ANDROID_PROXY "platforms;android-17"
RUN yes | sdkmanager $ANDROID_PROXY "build-tools;25.0.3"


CMD ["/bin/bash"]
