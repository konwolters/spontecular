FROM openjdk:17-slim
COPY ./target/spontecular-0.8.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]