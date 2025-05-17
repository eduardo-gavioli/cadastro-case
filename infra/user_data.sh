#!/bin/bash

sudo sudo
yum update -y
yum install -y docker
service docker start
usermod -a -G docker ec2-user

#sobe a aplicação da aws
docker run -p 80:8080 edumapfre/public-api:latest