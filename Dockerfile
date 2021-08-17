FROM openjdk:8
VOLUME /temp
EXPOSE 8081
ADD ./target/ms-customer-0.0.1-SNAPSHOT.jar customer-service.jar
ENTRYPOINT ["java","-jar","/customer-service.jar"]