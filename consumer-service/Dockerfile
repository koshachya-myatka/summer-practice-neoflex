FROM maven:latest AS build
WORKDIR /consumer-service
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /consumer-service
COPY --from=build consumer-service/target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
