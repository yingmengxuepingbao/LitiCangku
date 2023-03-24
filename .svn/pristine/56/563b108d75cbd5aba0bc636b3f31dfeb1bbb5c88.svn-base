# Docker image for springboot file run
# VERSION 0.0.1
# Author: zhangx
# 基础镜像使用java
FROM frolvlad/alpine-oraclejre8:slim
# 作者
MAINTAINER zhangx <zxcq06@126.com>
# VOLUME 指定了临时文件目录为/tmp。
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
VOLUME /tmp 
# 将jar包添加到容器中并更名为app.jar
ADD wcs-1.0-SNAPSHOT.jar app.jar 
EXPOSE 9090
# 运行jar包
CMD ["java","-Xms1024m","-Xmx2048m","-jar","-Dspring.profiles.active=prod","/app.jar"]