FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17-jdk-slim AS final
VOLUME /tmp
COPY --from=build /app/target/*.jar app.jar
CMD ["java", "-jar", "/app.jar"]
EXPOSE 8282
