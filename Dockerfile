# 基础镜像
FROM  openjdk:8-jre
# 维护作者
MAINTAINER 单体架构
# 设置默认时区
ENV TZ=Asia/Shanghai
# 创建目录
RUN mkdir -p /data/java
# 指定工作区
WORKDIR /data/java
# 挂载目录
VOLUME /data/java
# 添加应用JAR包到容器对应目录下
ADD regsys_EleVue.jar /data/java
# 暴露容器端口号
EXPOSE 9030
# 容器启动之后执行的命令,参数使用逗号隔开,java -jar /data/java/app.jar 即启动jar
ENTRYPOINT ["java","-jar","regsys_EleVue.jar","--spring.profiles.active=prod"]
