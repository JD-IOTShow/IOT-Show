FROM ubuntu:16.04
MAINTAINER mule-standalone-3.8.1
RUN apt-get update
RUN apt-get install -y language-pack-zh-hans language-pack-zh-hant software-properties-common apt-utils vim unzip zip tar
ENV LANG zh_CN.UTF-8
ENV LANGUAGE zh_CN:zh:en_US:en
#confd
ADD confd-0.11.0-linux-amd64 /usr/local/bin/confd
RUN mkdir -p /etc/confd/{conf.d,templates}
# 安装jdk
RUN mkdir /usr/java
ADD jdk1.8.0_131 /usr/java/jdk1.8.0_131
# 配置环境变量
ENV JAVA_HOME /usr/java/jdk1.8.0_131
ENV PATH      $PATH:$JAVA_HOME/bin
ENV CLASSPATH .:$JAVA_HOME/lib
#mule
ADD mule-standalone-3.8.1 /opt/mule-standalone-3.8.1
VOLUME /tmp
ADD iot-show-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]