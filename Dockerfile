# Usando uma imagem base do OpenJDK 24
FROM openjdk:21

# Definindo o diretório de trabalho dentro do container
WORKDIR /app

# Copiando o arquivo JAR gerado pelo Maven para dentro do container
COPY target/demo-0.0.1-SNAPSHOT.jar /app/cadastro-api.jar
COPY app_env/.env /app/app_env/.env
COPY docker_env/.env /app/docker_env/.env

# Expondo a porta usada pela aplicação (Spring Boot padrão: 8080)
EXPOSE 8080

# Definindo o comando de inicialização do container
ENTRYPOINT ["java", "-jar", "/app/cadastro-api.jar"]