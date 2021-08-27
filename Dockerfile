FROM openjdk:8-alpine
COPY "./target/ms-customer-0.0.1-SNAPSHOT.jar" "appcustomer-service.jar"
EXPOSE 8081
ENTRYPOINT ["java","-jar","appcustomer-service.jar"]