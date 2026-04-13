# Etapa 1: Construção (Build)
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
# Copia o pom.xml e baixa as dependências
COPY pom.xml .
RUN mvn dependency:go-offline
# Copia o código fonte e compila o projeto
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Execução (Run)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copia apenas o arquivo .jar gerado na etapa anterior
COPY --from=builder /app/target/*.jar app.jar
# Expõe a porta 8080
EXPOSE 8080
# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]