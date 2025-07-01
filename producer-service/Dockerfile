FROM maven:latest AS build
WORKDIR /producer-service
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /producer-service
COPY --from=build producer-service/target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]