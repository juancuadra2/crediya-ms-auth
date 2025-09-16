# =====================
# Etapa de construcción
# =====================
FROM eclipse-temurin:21-jdk-alpine AS builder

# Necesario para gradlew en alpine
# Instala bash, ca-certificates y unzip para que el wrapper de Gradle pueda descargar y desempaquetar distribuciones
RUN apk add --no-cache bash ca-certificates unzip

WORKDIR /build

# Copia solo los archivos de configuración de Gradle primero (para cachear dependencias)
COPY gradle/ gradle/
# Copia los archivos del wrapper y de construcción. Preserva el bit ejecutable estableciéndolo explícitamente.
COPY gradlew gradlew.bat build.gradle settings.gradle gradle.properties ./
RUN chmod +x ./gradlew

# Copia el resto del código fuente
COPY . .

# Limpia y construye el JAR
RUN ./gradlew clean bootJar -x validateStructure -x test --no-daemon


# =====================
# Etapa de ejecución
# =====================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Crea un usuario no root (genérico)
RUN addgroup -g 1001 appgroup \
 && adduser -u 1001 -G appgroup -s /bin/sh -D appuser

# Copia el JAR del subproyecto app-service
# Apunta a la ruta exacta para evitar no encontrar el jar en monorepos
COPY --from=builder /build/applications/app-service/build/libs/*.jar app.jar

VOLUME /tmp

# Variables de entorno por defecto
ENV SPRING_PROFILES_ACTIVE=docker

USER appuser

# Ejecuta directamente
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=70.0", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
