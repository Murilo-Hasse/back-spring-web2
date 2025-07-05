FROM maven:3.9.10-amazoncorretto-21-debian-bookworm as build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21
WORKDIR /app
COPY --from=build ./build/target/*.jar ./vagas.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "vagas.jar"]

