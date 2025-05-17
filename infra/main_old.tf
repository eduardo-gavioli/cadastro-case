# provider "aws" {
#   region = "us-east-1"
# }
# resource "aws_vpc" "main_vpc" {
#   cidr_block = "10.0.0.0/16"
# }
#
# resource "aws_subnet" "main_subnet" {
#   vpc_id            = aws_vpc.main_vpc.id
#   cidr_block        = "10.0.1.0/24"
#   availability_zone = "us-east-1a"
# }
#
# resource "aws_subnet" "another_subnet" {
#   vpc_id            = aws_vpc.main_vpc.id
#   cidr_block        = "10.0.2.0/24"
#   availability_zone = "us-east-1b"
# }
#
# resource "aws_db_subnet_group" "main_subnet_group" {
#   name       = "main_subnet"
#   subnet_ids = [aws_subnet.main_subnet.id, aws_subnet.another_subnet.id]
#
#   tags = {
#     Name = "RDS Subnet Group"
#   }
# }
#
#
# resource "aws_security_group" "ec2_sg" {
#   name        = "ec2-security-group"
#   description = "Permitir entrada na porta 80"
#   vpc_id      = aws_vpc.main_vpc.id
#
#   ingress {
#     from_port   = 80
#     to_port     = 80
#     protocol    = "tcp"
#     cidr_blocks = ["0.0.0.0/0"]
#   }
#
#   egress {
#     from_port   = 0
#     to_port     = 65535
#     protocol    = "tcp"
#     cidr_blocks = ["0.0.0.0/0"]
#   }
# }
#
# resource "aws_instance" "ec2_instance" {
#   ami           = "ami-0953476d60561c955"
#   instance_type = "t2.nano"
#
#   security_groups = [aws_security_group.ec2_sg.name]
#   vpc_security_group_ids = [aws_security_group.ec2_sg.id]
#   subnet_id = aws_subnet.main_subnet.id
#
#   tags = {
#     Name = "TerraformEC2"
#   }
# }
#
# resource "aws_security_group" "rds_sg" {
#   name        = "rds-security-group"
#   description = "Permitir acesso ao PostgreSQL pela porta 5432"
#   vpc_id      = aws_vpc.main_vpc.id
#   ingress {
#     from_port   = 5432
#     to_port     = 5432
#     protocol    = "tcp"
#     cidr_blocks = ["0.0.0.0/0"]
#   }
# }
#
# resource "aws_db_instance" "postgresql" {
#   engine            = "postgres"
#   instance_class    = "db.t3.micro"
#   allocated_storage = 2
#   db_name           = "cadastro"
#   username         = "gavioli"
#   password         = "ayWq8u#o^C2EegMx" # Altere para um valor seguro
#   vpc_security_group_ids = [aws_security_group.rds_sg.id]
#   db_subnet_group_name =aws_db_subnet_group.main_subnet_group.name
#   skip_final_snapshot = true
#   apply_immediately = true
#   snapshot_identifier = "postgresql_cluster"
#   tags = {
#     Name = "TerraformRDS"
#   }
# }