FROM bladex/alpine-java:openjdk8-openj9_cn_slim

MAINTAINER smallchill@163.com

RUN mkdir -p /blade

WORKDIR /blade

EXPOSE 1889

ADD ./target/welfare_lottery_platform_api.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Ddruid.mysql.usePingMethod=false", "-jar", "app.jar"]

CMD ["--spring.profiles.active=dev"]
