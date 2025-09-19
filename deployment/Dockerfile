# =====================
# Etapa de construcción
# =====================
# Usamos una imagen JDK ligera para compilar
FROM eclipse-temurin:21-jdk-alpine AS builder

# Instala dependencias necesarias para Gradle en Alpine
RUN apk add --no-cache bash unzip

WORKDIR /build

# Copia primero solo los archivos relacionados con Gradle (mejor uso de cache)
COPY gradlew gradlew.bat ./
COPY gradle/ ./gradle/
COPY build.gradle settings.gradle main.gradle gradle.properties ./

# Ajusta gradlew para Linux y permisos de ejecución
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew

# Descarga dependencias (se cachea mientras no cambien los gradle files)
RUN ./gradlew dependencies --no-daemon

# Ahora sí, copia el resto del código fuente
COPY . .

RUN sed -i 's/\r$//' gradlew && chmod +x gradlew

# Construye el JAR ejecutable (saltando tests y validaciones innecesarias para build)
RUN ./gradlew clean bootJar -x validateStructure -x test --no-daemon


# =====================
# Etapa de ejecución
# =====================
# Imagen más pequeña para ejecutar el JAR
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copia el JAR construido desde la etapa anterior
COPY --from=builder /build/applications/app-service/build/libs/*.jar app.jar

# Crea un usuario no root para mayor seguridad
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Exponer puerto por defecto de Spring Boot
EXPOSE 8080

# Definir perfil por defecto
ENV SPRING_PROFILES_ACTIVE=docker

# Ejecutar como usuario no root
USER appuser

# Comando de arranque
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=70.0", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
