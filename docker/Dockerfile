# Usando uma imagem base do OpenJDK 24
FROM openjdk:21-jdk-slim

# Definindo o diretório de trabalho dentro do container
WORKDIR /app

# Copiando o arquivo JAR gerado pelo Maven para dentro do container
COPY ./target/api-0.0.1-SNAPSHOT.jar /app/cadastro-api.jar

# Expondo a porta usada pela aplicação (Spring Boot padrão: 8080)
EXPOSE 8080

# Definindo o comando de inicialização do container
CMD ["java", "-jar", "/app/cadastro-api.jar"]