// Isso irá exibir o IP público do servidor web
output "web_public_ip" {
  description = "O endereço IP público do servidor web"
  // Estamos pegando do Elastic IP
  value       = aws_eip.cadastro_web_eip[0].public_ip

  // Esta saída espera os Elastic IPs serem criados e distribuídos
  depends_on = [aws_eip.cadastro_web_eip]
}

// Isso irá exibir o endereço DNS público do servidor web
output "web_public_dns" {
  description = "O endereço DNS público do servidor web"
  // Estamos pegando do Elastic IP
  value       = aws_eip.cadastro_web_eip[0].public_dns

  depends_on = [aws_eip.cadastro_web_eip]
}

// Isso irá exibir o endpoint do banco de dados
output "database_endpoint" {
  description = "O endpoint do banco de dados"
  value       = aws_db_instance.cadastro_database.address
}

// Isso irá exibir a porta do banco de dados
output "database_port" {
  description = "A porta do banco de dados"
  value       = aws_db_instance.cadastro_database.port
}