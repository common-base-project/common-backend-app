FROM openjdk:8-jdk-alpine
MAINTAINER 重庆思普信息科技有限公司 wangjunyu@splsw.com
VOLUME ["temp","/logs"]
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone
ADD target/*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dname=user-svc","-Xmx2048m","-Xms2048m","-Xmn512m","-XX:SurvivorRatio=8","-XX:MetaspaceSize=64m","-XX:MaxMetaspaceSize=256m","-jar", "app.jar"]
EXPOSE 5004


