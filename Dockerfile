# Base os image
FROM centos:7

MAINTAINER sunxch 88123304@qq.com

LABEL Description="This image is the base os images." Version="1.0"

# reconfig timezone
RUN echo "Asia/Shanghai"

RUN mkdir /usr/local/project

# Install jdk
ADD jdk-8u152-linux-x64.tar.gz /usr/local/

ENV JAVA_HOME /usr/local/jdk1.8.0_152

ENV PATH $PATH:$JAVA_HOME/bin:$CATALINA_HOME/lib:$CATALINA_HOME/bin

RUN java -version 

ADD spring-boot-0.0.1-SNAPSHOT.jar /usr/local/project

EXPOSE 9990

WORKDIR /usr/local/project/

ENTRYPOINT java -jar ./spring-boot-0.0.1-SNAPSHOT.jar
