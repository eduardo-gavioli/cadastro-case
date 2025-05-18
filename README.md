📌 Cadastro API

🚀 Cadastro API é uma API RESTful desenvolvida em Java (Spring Boot) que permite o cadastro de usuários, consulta de registros e envio de notificações por e-mail. A aplicação segue arquitetura hexagonal e é conteinerizada, permitindo fácil escalabilidade.

![Custom VPC architecture for AWS](https://i.postimg.cc/tRFWC9G1/Postgre-SQL.png)✨ Funcionalidades

✅ Cadastro de usuários com nome, sobrenome, idade e país<br>
[x] Envio de notificação assíncrona via RabbitMQ<br>
✅ Consulta de um cadastro por ID<br>
✅ Listagem de todos os cadastros<br>
✅ Atualização parcial do cadastro<br>
✅ Exclusão de um cadastro<br>
✅ Exposição da API via API Gateway<br>
[x] Observabilidade com Grafana, Prometheus e Loki<br>
✅ Deploy automatizado usando Docker e Kubernetes<br>
✅ Infraestrutura gerenciada via Terraform na AWS<br>

🚀 Tecnologias Utilizadas
- Spring Boot (Framework principal)
- Spring Data JPA (Banco de dados PostgreSQL)
- Spring AMQP (Mensageria com RabbitMQ)
- JUnit + Mockito (Testes unitários)
- Docker (Containerização)
- Kubernetes (Orquestração)
- Grafana + Prometheus + Loki (Monitoramento)
- Terraform (Infraestrutura como código)
- AWS (Hospedagem e serviços cloud)

🔧 Pré-requisitos
Antes de iniciar, certifique-se de ter instalado:
- Java 17+
- Docker e Docker Compose
- Kubernetes (kubectl + minikube)
- Terraform (para provisionamento)
- PostgreSQL (Banco de dados)

📜 Instalação<br>
1️⃣ Clone o repositório<br>
git clone https://github.com/eduardo-gavioli/cadastro-case.git<br>
cd cadastro-case


2️⃣ Configurar variáveis de ambiente<br>
Edite o arquivo application.properties ou crie um ./app_env/.env:<br>
DATABASE_URL=jdbc:postgresql://localhost:5432/cadastro<br>
RABBITMQ_URL=amqp://guest:guest@localhost<br>


3️⃣ Executar via Docker<br>
docker-compose up -d<br>

4️⃣ Verificar logs e métricas<br>
Acesse o Grafana para visualizar métricas:<br>
http://localhost:3000

🛠 Endpoints da API
Cadastro<br>
✅ POST /cadastros → Criação de cadastro<br>
✅ GET /cadastros/{id} → Consulta por ID<br>
✅ GET /cadastros → Listagem de cadastros<br>
✅ PATCH /cadastros/{id} → Atualização parcial<br>
✅ DELETE /cadastros/{id} → Exclusão<br><br>
Exemplo de requisição:
POST /cadastros
{
"nome": "Eduardo",
"sobrenome": "Gavioli",
"idade": 30,
"pais": "Brasil"
}

✅ Testes
Para rodar os testes unitários:
mvn test


✅ Cobertura de testes esperada: > 90%+ 🛡️
![img.png](img.png)

📦 Estrutura do Projeto<br>
cadastro-api/<br>
├── src/main/java/com/example/cadastro/<br>
│   ├── domain/       # Entidades e regras de negócio<br>
│   ├── application/  # Controllers <br>
│   ├── infrastructure/  # Banco de Dados, Mensageria<br>
├── docker/<br>
├── infra/<br>
├── README.md<br>
├── pom.xml<br>


🏗 Deploy AWS via Terraform<br>
Para provisionar a infraestrutura:<br>
cd terraform<br>
terraform init<br>
terraform apply<br>


Isso criará uma instancia EC2, RDS (PostgreSQL) e API Gateway na AWS.


Se precisar gerar a imagem docker e subir para o docker hub


1️⃣ Ir na pasta principal da aplicação rode
.\mvnw install
mvn clean package install -U

Certifique-se que o docker esteja iniciado em sua maquina

2️⃣ Após, rode para gerar a imagem localmente (esta imagem que será executada na aws pelo arquiv user_data.sh dentro da pasta infra)
docker build . -t userDockerHub/public-api:latest

para subir no docker hub
docker push userDockerHub/public-api:latest

3️⃣ para enviar a imagem para o dockerhub, execute
docker login

4️⃣ para verificar se a imagem está funcionando ou não
docker run -p 8080:8080 userDockerHub/public-api:latest