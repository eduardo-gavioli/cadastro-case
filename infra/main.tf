// Aqui estamos configurando nosso provider aws.
// Estamos definindo a região para a região da
// nossa variável "aws_region"
provider "aws" {
  region = var.aws_region
}

// Este objeto data vai armazenar todas as zonas de disponibilidade
// disponíveis na nossa região definida
data "aws_availability_zones" "available" {
  state = "available"
}

// Crie um objeto data chamado "ubuntu" que armazena a última
// AMI do Ubuntu 20.04 server
data "aws_ami" "ubuntu" {
  // Queremos a AMI mais recente
  most_recent = "true"

  // Estamos filtrando pelos nomes das AMIs. Queremos o
  // Ubuntu 20.04 server
  filter {
    name   = "name"
    values = ["ubuntu/images/hvm-ssd/ubuntu-focal-20.04-amd64-server-*"]
  }

  // Estamos filtrando pelo tipo de virtualização para garantir
  // que só encontramos AMIs com tipo de virtualização hvm
  filter {
    name   = "virtualization-type"
    values = ["hvm"]
  }

  // Este é o ID do publicador que criou a AMI.
  // O publicador do Ubuntu 20.04 LTS Focal é a Canonical
  // e o ID deles é 099720109477
  owners = ["099720109477"]
}

// Crie uma VPC chamada "cadastro_vpc"
resource "aws_vpc" "cadastro_vpc" {
  // Aqui estamos definindo o bloco CIDR da VPC
  // para a variável "vpc_cidr_block"
  cidr_block           = var.vpc_cidr_block
  // Queremos DNS hostnames habilitados para esta VPC
  enable_dns_hostnames = true

  // Estamos marcando a VPC com o nome "cadastro_vpc"
  tags = {
    Name = "cadastro_vpc"
  }
}

// Crie um internet gateway chamado "cadastro_igw"
// e anexe à VPC "cadastro_vpc"
resource "aws_internet_gateway" "cadastro_igw" {
  // Aqui estamos anexando o IGW à
  // VPC cadastro_vpc
  vpc_id = aws_vpc.cadastro_vpc.id

  // Estamos marcando o IGW com o nome cadastro_igw
  tags = {
    Name = "cadastro_igw"
  }
}

// Crie um grupo de subnets públicas baseado na variável subnet_count.public
resource "aws_subnet" "cadastro_public_subnet" {
  // count é o número de recursos que queremos criar
  // aqui estamos referenciando a variável subnet_count.public que
  // atualmente está atribuída a 1, então apenas 1 subnet pública será criada
  count             = var.subnet_count.public

  // Coloque a subnet na VPC "cadastro_vpc"
  vpc_id            = aws_vpc.cadastro_vpc.id

  // Estamos pegando um bloco CIDR da variável "public_subnet_cidr_blocks"
  // como é uma lista, precisamos pegar o elemento baseado no count,
  // como count é 1, vamos pegar o primeiro bloco cidr
  // que será 10.0.1.0/24
  cidr_block        = var.public_subnet_cidr_blocks[count.index]

  // Estamos pegando a zona de disponibilidade do objeto data criado anteriormente
  // Como é uma lista, pegamos o nome do elemento baseado no count,
  // então como count é 1 e nossa região é us-east-2, isso deve pegar us-east-2a
  availability_zone = data.aws_availability_zones.available.names[count.index]

  // Estamos marcando a subnet com o nome "cadastro_public_subnet_" e
  // sufixando com o count
  tags = {
    Name = "cadastro_public_subnet_${count.index}"
  }
}

// Crie um grupo de subnets privadas baseado na variável subnet_count.private
resource "aws_subnet" "cadastro_private_subnet" {
  // count é o número de recursos que queremos criar
  // aqui estamos referenciando a variável subnet_count.private que
  // atualmente está atribuída a 2, então 2 subnets privadas serão criadas
  count             = var.subnet_count.private

  // Coloque a subnet na VPC "cadastro_vpc"
  vpc_id            = aws_vpc.cadastro_vpc.id

  // Estamos pegando um bloco CIDR da variável "private_subnet_cidr_blocks"
  // como é uma lista, precisamos pegar o elemento baseado no count,
  // como count é 2, a primeira subnet pegará o bloco CIDR 10.0.101.0/24
  // e a segunda subnet pegará o bloco CIDR 10.0.102.0/24
  cidr_block        = var.private_subnet_cidr_blocks[count.index]

  // Estamos pegando a zona de disponibilidade do objeto data criado anteriormente
  // Como é uma lista, pegamos o nome do elemento baseado no count,
  // como count é 2 e nossa região é us-east-2, a primeira subnet deve
  // pegar us-east-2a e a segunda pegará us-east-2b
  availability_zone = data.aws_availability_zones.available.names[count.index]

  // Estamos marcando a subnet com o nome "cadastro_private_subnet_" e
  // sufixando com o count
  tags = {
    Name = "cadastro_private_subnet_${count.index}"
  }
}

// Crie uma route table pública chamada "cadastro_public_rt"
resource "aws_route_table" "cadastro_public_rt" {
  // Coloque a route table na VPC "cadastro_vpc"
  vpc_id = aws_vpc.cadastro_vpc.id

  // Como esta é a route table pública, ela precisará
  // de acesso à internet. Então estamos adicionando uma rota com
  // destino 0.0.0.0/0 e direcionando para o Internet
  // Gateway "cadastro_igw"
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.cadastro_igw.id
  }
}

// Aqui vamos adicionar as subnets públicas à
// route table "cadastro_public_rt"
resource "aws_route_table_association" "public" {
  // count é o número de subnets que queremos associar com
  // esta route table. Estamos usando a variável subnet_count.public
  // que atualmente é 1, então vamos adicionar a 1 subnet pública
  count          = var.subnet_count.public

  // Aqui estamos garantindo que a route table é
  // "cadastro_public_rt" de cima
  route_table_id = aws_route_table.cadastro_public_rt.id

  // Este é o ID da subnet. Como "cadastro_public_subnet" é uma
  // lista de subnets públicas, precisamos usar count para pegar o
  // elemento da subnet e então pegar o id dessa subnet
  subnet_id      = 	aws_subnet.cadastro_public_subnet[count.index].id
}

// Crie uma route table privada chamada "cadastro_private_rt"
resource "aws_route_table" "cadastro_private_rt" {
  // Coloque a route table na VPC "cadastro_VPC"
  vpc_id = aws_vpc.cadastro_vpc.id

  // Como esta será uma route table privada,
  // não vamos adicionar nenhuma rota
}

// Aqui vamos adicionar as subnets privadas à
// route table "cadastro_private_rt"
resource "aws_route_table_association" "private" {
  // count é o número de subnets que queremos associar com
  // a route table. Estamos usando a variável subnet_count.private
  // que atualmente é 2, então vamos adicionar as 2 subnets privadas
  count          = var.subnet_count.private

  // Aqui estamos garantindo que a route table é
  // "cadastro_private_rt" de cima
  route_table_id = aws_route_table.cadastro_private_rt.id

  // Este é o ID da subnet. Como "cadastro_private_subnet" é uma
  // lista de subnets privadas, precisamos usar count para pegar o
  // elemento da subnet e então pegar o ID dessa subnet
  subnet_id      = aws_subnet.cadastro_private_subnet[count.index].id
}

// Crie um security group para as instâncias EC2 chamado "cadastro_web_sg"
resource "aws_security_group" "cadastro_web_sg" {
  // Detalhes básicos como nome e descrição do SG
  name        = "cadastro_web_sg"
  description = "Security group para servidores web do cadastro"
  // Queremos que o SG esteja na VPC "cadastro_vpc"
  vpc_id      = aws_vpc.cadastro_vpc.id

  // O primeiro requisito é "Instâncias EC2 devem ser acessíveis
  // de qualquer lugar da internet via HTTP." Então vamos
  // criar uma regra de entrada que permite todo o tráfego
  // pela porta TCP 80.
  ingress {
    description = "Permitir todo o trafego via HTTP"
    from_port   = "80"
    to_port     = "80"
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  // O segundo requisito é "Apenas você deve poder acessar as instâncias EC2 via SSH."
  // Então vamos criar uma regra de entrada que permite tráfego SSH SOMENTE do seu IP
  ingress {
    description = "Permitir SSH do meu computador"
    from_port   = "22"
    to_port     = "22"
    protocol    = "tcp"
    // Usando a variável "my_ip"
    cidr_blocks = ["${var.my_ip}/32"]
  }

  // Esta regra de saída permite todo o tráfego de saída
  // das instâncias EC2
  egress {
    description = "Permitir todo o trafego de saida"
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  // Aqui estamos marcando o SG com o nome "cadastro_web_sg"
  tags = {
    Name = "cadastro_web_sg"
  }
}

// Crie um security group para as instâncias RDS chamado "cadastro_db_sg"
resource "aws_security_group" "cadastro_db_sg" {
  // Detalhes básicos como nome e descrição do SG
  name        = "cadastro_db_sg"
  description = "Security group para bancos de dados do cadastro"
  // Queremos que o SG esteja na VPC "cadastro_vpc"
  vpc_id      = aws_vpc.cadastro_vpc.id

  // O terceiro requisito era "RDS deve estar em uma subnet privada e
  // inacessível via internet." Para isso, não vamos adicionar regras
  // de entrada ou saída para tráfego externo.

  // O quarto e último requisito era "Apenas as instâncias EC2
  // devem poder se comunicar com o RDS." Então vamos criar uma
  // regra de entrada que permite tráfego do security group das EC2
  // pela porta TCP 5432, que é a porta do Postgres
  ingress {
    description     = "Permitir trafego Postgres apenas do web sg"
    from_port       = "5432"
    to_port         = "5432"
    protocol        = "tcp"
    security_groups = [aws_security_group.cadastro_web_sg.id]
  }

  // Aqui estamos marcando o SG com o nome "cadastro_db_sg"
  tags = {
    Name = "cadastro_db_sg"
  }
}

// Crie um db subnet group chamado "cadastro_db_subnet_group"
resource "aws_db_subnet_group" "cadastro_db_subnet_group" {
  // Nome e descrição do db subnet group
  name        = "cadastro_db_subnet_group"
  description = "DB subnet group para o cadastro"

  // Como o db subnet group requer 2 ou mais subnets, vamos
  // percorrer nossas subnets privadas em "cadastro_private_subnet" e
  // adicioná-las a este db subnet group
  subnet_ids  = [for subnet in aws_subnet.cadastro_private_subnet : subnet.id]
}

// Crie uma instância DB chamada "cadastro_database"
resource "aws_db_instance" "cadastro_database" {
  // A quantidade de armazenamento em gigabytes que queremos para o banco de dados.
  // Está sendo definida pela variável settings.database.allocated_storage, que está em 10
  allocated_storage      = var.settings.database.allocated_storage

  // O engine que queremos para o banco de dados. Definido pela variável
  // settings.database.engine, que está como "Postgres"
  engine                 = var.settings.database.engine

  // A versão do engine do banco de dados. Definida pela variável
  // settings.database.engine_version, que está como "8.0.27"
  engine_version         = var.settings.database.engine_version

  // O tipo de instância para o DB. Definido pela variável
  // settings.database.instance_class, que está como "db.t2.micro"
  instance_class         = var.settings.database.instance_class

  // Este é o nome do banco de dados. Definido pela variável
  // settings.database.db_name, que está como "cadastro"
  db_name                = var.settings.database.db_name

  // O usuário master do banco de dados. Definido pela variável
  // db_username, que está declarada no arquivo de secrets
  username               = var.db_username

  // A senha do usuário master. Definida pela variável
  // db_password, que está declarada no arquivo de secrets
  password               = var.db_password

  // Este é o DB subnet group "cadastro_db_subnet_group"
  db_subnet_group_name   = aws_db_subnet_group.cadastro_db_subnet_group.id

  // Este é o security group do banco de dados. Aceita uma lista, mas como
  // só temos 1 security group para o db, estamos passando apenas o
  // security group "cadastro_db_sg"
  vpc_security_group_ids = [aws_security_group.cadastro_db_sg.id]

  // Refere-se a pular o snapshot final do banco de dados. É um
  // booleano definido pela variável settings.database.skip_final_snapshot,
  // que está como true.
  skip_final_snapshot    = var.settings.database.skip_final_snapshot
}

// Crie um key pair chamado "cadastro_kp"
# resource "aws_key_pair" "cadastro_kp" {
#   // Dê um nome ao key pair
#   key_name   = "cadastro_kp"
#
#   // Este será o public key do nosso
#   // ssh key. O diretório file pega o arquivo
#   // de um caminho específico. Como a chave pública
#   // foi criada no mesmo diretório do main.tf
#   // podemos apenas colocar o nome
#   public_key = file("cadastro_kp.pub")
# }

// Crie uma instância EC2 chamada "cadastro_web"
resource "aws_instance" "cadastro_web" {
  // count é o número de instâncias que queremos
  // como a variável settings.web_app.cont está em 1, teremos apenas 1 EC2
  count                  = var.settings.web_app.count

  // Aqui precisamos selecionar a ami para a EC2. Vamos usar o
  // objeto data ami chamado ubuntu, que pega a última
  // ami do Ubuntu 20.04
  ami                    = data.aws_ami.ubuntu.id

  // Este é o tipo de instância da EC2. A variável
  // settings.web_app.instance_type está como "t2.micro"
  instance_type          = var.settings.web_app.instance_type

  // O ID da subnet para a instância EC2. Como "cadastro_public_subnet" é uma lista
  // de subnets públicas, queremos pegar o elemento baseado na variável count.
  // Como count é 1, vamos pegar a primeira subnet em
  // "cadastro_public_subnet" e colocar a EC2 lá
  subnet_id              = aws_subnet.cadastro_public_subnet[count.index].id

  // O key pair para conectar na EC2. Estamos usando o key pair "cadastro_kp"
  // que criamos
  # key_name               = aws_key_pair.cadastro_kp.key_name

  // Os security groups da instância EC2. Aceita uma lista, mas só temos
  // 1 security group para as instâncias EC2.
  vpc_security_group_ids = [aws_security_group.cadastro_web_sg.id]

  // Estamos marcando a instância EC2 com o nome "cadastro_db_" seguido
  // do índice count
  tags = {
    Name = "cadastro_web_${count.index}"
  }
}

// Crie um Elastic IP chamado "cadastro_web_eip" para cada
// instância EC2
resource "aws_eip" "cadastro_web_eip" {
  // count é o número de Elastic IPs a criar. Está
  // sendo definido pela variável settings.web_app.count que
  // refere-se ao número de instâncias EC2. Queremos um
  // Elastic IP para cada instância EC2
  count    = var.settings.web_app.count

  // A instância EC2. Como cadastro_web é uma lista de
  // instâncias EC2, precisamos pegar a instância pelo
  // índice count. Como count está em 1, vai pegar a primeira e única EC2
  instance = aws_instance.cadastro_web[count.index].id

  // Queremos que o Elastic IP esteja na VPC
  # vpc      = true
  domain = "vpc"

  // Aqui estamos marcando o Elastic IP com o nome
  // "cadastro_web_eip_" seguido do índice count
  tags = {
    Name = "cadastro_web_eip_${count.index}"
  }
}