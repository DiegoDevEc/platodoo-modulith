# --- Stage 1: Build ---
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar los archivos de dependencias primero para aprovechar la cache de Docker
COPY pom.xml .
COPY .mvn .mvn
RUN mvn dependency:go-offline

# Ahora copiamos el resto del proyecto y lo construimos
COPY . .
RUN mvn clean package -DskipTests

# --- Stage 2: Runtime ---
FROM eclipse-temurin:21-jre-alpine AS runtime

WORKDIR /app

# Copiar solo el jar final
COPY --from=build /app/target/*.jar app.jar

# Variables de entorno (puedes añadir más aquí)
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Puerto expuesto por tu aplicación Spring Boot
EXPOSE 8081

# Comando de arranque
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
