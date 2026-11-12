FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
RUN mvn -B -q dependency:go-offline
COPY src ./src
RUN mvn -B -q clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/target/ledgerline-2.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["sh","-c","exec java -Dserver.port=${PORT:-8080} -jar app.jar"]
