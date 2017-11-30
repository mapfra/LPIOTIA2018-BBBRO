FROM maven:3-jdk-8



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
