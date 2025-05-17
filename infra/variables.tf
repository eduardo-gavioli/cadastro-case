// Esta variável é para definir a
// região AWS onde tudo será
// criado
variable "aws_region" {
  default = "us-east-1"
}

// Esta variável é para definir o
// bloco CIDR para a VPC
variable "vpc_cidr_block" {
  description = "CIDR block for VPC"
  type        = string
  default     = "10.0.0.0/16"
}

// Esta variável contém o
// número de subnets públicas e privadas
variable "subnet_count" {
  description = "Number of subnets"
  type        = map(number)
  default = {
    public  = 1,
    private = 2
  }
}

// Esta variável contém as configurações
// para as instâncias EC2 e RDS
variable "settings" {
  description = "Configuration settings"
  type        = map(any)
  default = {
    "database" = {
      allocated_storage   = 2            // armazenamento em gigabytes
      engine              = "postgres"       // tipo do motor
      instance_class      = "db.t2.micro" // tipo de instância rds
      db_name             = "cadastro"    // nome do banco de dados
      engine_version      = "16.3"
      skip_final_snapshot = true
    },
    "web_app" = {
      count         = 1          // número de instâncias EC2
      instance_type = "t2.micro" // a instância EC2
    }
  }
}

// Esta variável contém os blocos CIDR para
// a subnet pública. Incluí apenas 4
// para este cadastro, mas se você precisar de mais
// você deve adicioná-los aqui
variable "public_subnet_cidr_blocks" {
  description = "Available CIDR blocks for public subnets"
  type        = list(string)
  default = [
    "10.0.1.0/24",
    "10.0.2.0/24",
    "10.0.3.0/24",
    "10.0.4.0/24"
  ]
}

// Esta variável contém os blocos CIDR para
// a subnet privada. Incluí apenas 4
// para este exemplo, mas se você precisar de mais
// você deve adicioná-los aqui
variable "private_subnet_cidr_blocks" {
  description = "Available CIDR blocks for private subnets"
  type        = list(string)
  default = [
    "10.0.101.0/24",
    "10.0.102.0/24",
    "10.0.103.0/24",
    "10.0.104.0/24",
  ]
}

// Esta variável contém seu endereço IP. Isto
// é usado ao configurar a regra SSH no
// grupo de segurança web
variable "my_ip" {
  description = "162.120.186.85"
  type        = string
  sensitive   = true
}

// Esta variável contém o usuário master do banco de dados
// Vamos armazenar isto em um arquivo de segredos
variable "db_username" {
  description = "gavioli"
  type        = string
  sensitive   = true
}

// Esta variável contém a senha master do banco de dados
// Vamos armazenar isto em um arquivo de segredos
variable "db_password" {
  description = "ayWq8u#o^C2EegMx"
  type        = string
  sensitive   = true
}