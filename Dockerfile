FROM openjdk:17-ea-11-slim
VOLUME /tmp
COPY build/libs/CoffeeOrderService-0.0.4.jar CoffeeOrderService.jar
ENTRYPOINT ["java","-jar","CoffeeOrderService.jar"]