# Etapa 1: Compilación
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

# Copia el archivo pom.xml y las dependencias de Maven
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia todo el código fuente
COPY src ./src

# Compila la aplicación y genera el JAR
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copia el archivo JAR generado en la etapa de compilación
COPY --from=build /app/target/proyectohuella-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto por defecto de Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]