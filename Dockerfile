FROM openjdk:17-ea-11-slim
VOLUME /tmp
COPY target/TeaOrderService-0.0.1.jar TeaOrderService.jar
ENTRYPOINT ["java","-jar","TeaOrderService.jar"]