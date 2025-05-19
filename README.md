📌 Cadastro API

🚀 Cadastro API é uma API RESTful desenvolvida em Java (Spring Boot) que permite o cadastro de usuários, consulta de registros e envio de notificações por e-mail. A aplicação segue arquitetura hexagonal e é conteinerizada, permitindo fácil escalabilidade.

![Custom VPC architecture for AWS](imgs/desenho-solucao.png)✨ <br>

Funcionalidades
![img.png](imgs/checklistToDo.png)

<br>
🚀 Tecnologias Utilizadas
- Spring Boot (Framework principal)
- Spring Data JPA (Banco de dados PostgreSQL)
- JUnit + Mockito (Testes unitários)
- Docker (Containerização)
- Kubernetes (Orquestração)
- Terraform (Infraestrutura como código)
- AWS (Hospedagem e serviços cloud)

🔧 Pré-requisitos
Antes de iniciar, certifique-se de ter instalado:
- Java 17+
- Docker e Docker Compose
- Kubernetes (kubectl + minikube)
- Terraform (para provisionamento)
- PostgreSQL (Banco de dados)


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
![img.png](imgs/imgCobertura.png)

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


📜 Instalação<br>
1️⃣ Clone o repositório<br>
git clone https://github.com/eduardo-gavioli/cadastro-case.git<br>
cd cadastro-case<br>
-   Abra em uma IDE de preferencia<br>
instale todas as dependencias MAVEN.<br>
-   Ir na pasta principal da aplicação e executar<br>
    .\mvnw install<br>
    mvn clean package install -U<br><br>

-  ⚠️Certifique-se que o docker esteja iniciado em sua maquina

2️⃣ Configurar variáveis de ambiente<br>
Edite o arquivo application.properties e arquivos app_env/.env e docker_env/.env<br>
- Após, rode para gerar a imagem localmente (esta imagem que será executada na aws pelo arquiv user_data.sh dentro da pasta infra)<br>
docker build . -t userDockerHub/public-api:latest<br>

⚠️ Para subir no docker hub<br>
docker push userDockerHub/public-api:latest

3️⃣ Se informar que você não está logado para enviar a imagem para o dockerhub, execute<br>
docker login<br>

4️⃣ para verificar se a imagem está funcionando ou não <br>
docker run -p 8080:8090 userDockerHub/public-api:latest<br>

⚠️ Estando tudo certo execute para rodar o container local<br>
docker-compose up -d<br>

-   AWS<br>
1️⃣  Para criar a infra (IaC) na aws, acesse a pasta infra dentro da raiz do projeto e execute.<br>
terraform apply
<br>
- Após a criação da infra será mostrado no prompt o dns publico, ip publico e url do banco de dados postgres.

