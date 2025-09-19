# =====================
# Etapa de construcción
# =====================
# Usamos una imagen JDK de Eclipse Temurin, que es ligera y adecuada para Java.
FROM eclipse-temurin:21-jdk-alpine AS builder

# Instala las dependencias necesarias para Gradle en Alpine.
RUN apk add --no-cache bash unzip

WORKDIR /build

# Copia todo el código fuente del proyecto al directorio de trabajo.
COPY . .

# El siguiente comando convierte los finales de línea de CRLF a LF para que el script de Gradle sea ejecutable en entornos Linux.
RUN sed -i 's/\r$//' gradlew

# El siguiente comando asegura que el script de Gradle tenga permisos de ejecución.
RUN chmod +x gradlew

# Descarga todas las dependencias para aprovechar el cache de Docker.
# Si estos archivos no cambian, esta capa no se reconstruirá.
RUN ./gradlew dependencies --no-daemon

# Construye el JAR ejecutable.
RUN ./gradlew clean bootJar -x validateStructure -x test --no-daemon


# =====================
# Etapa de ejecución
# =====================
# Usa una imagen JRE más pequeña para el entorno de producción.
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copia el JAR construido desde la etapa anterior.
COPY --from=builder /build/applications/app-service/build/libs/*.jar app.jar

# Crea un usuario y grupo no root para mayor seguridad.
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Expone el puerto por defecto de Spring Boot.
EXPOSE 8080

# Define una variable de entorno por defecto para el perfil de Spring.
ENV SPRING_PROFILES_ACTIVE=docker

# Cambia al usuario no root para ejecutar la aplicación.
USER appuser

# Define el punto de entrada para ejecutar la aplicación.
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=70.0", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
