# Ifood Simplificado

## Objetivo 

O projeto foi construido com o objetivo de estabalecer uma base sólida dos fundamentos do Terraform e criar recursos reutilizáveis. Durante o desenvolvimento decidi me desafiar e criar uma solução capaz de utilizar múltiplos ambientes de desenvolviemnto(dev, prod) para integrar com CI/CD.

## Arquitetura da Solução

<img width="1229" alt="image" src="https://github.com/user-attachments/assets/061a9f6e-2056-45cd-9b66-169fe5e0a3b3" />

- API Gateway para encaminhar requisições aos nossos microsserviços
- Load balancer para distribuir carga entre as instâncias de cada microsserviço
- ECS Fargate para provisionar aplicações com Spring Boot. OBS: foi utilizado mongoDB no container ECS apenas por curiosidade, para o funcionamento correto deve-se utilizar DocumentDB
- SQS para comunicação assíncrona entre microsserviços

## Como executar o projeto

### Pré-requisitos
#### [GitHub secrets](https://github.com/Gpossas/ifood-simplificado/settings/secrets/actions)
- Adicionar *AWS_REGION* com o nome da região AWS para criar seus recursos. ex: sa-east-1
- Adicionar *AWS_TERRAFORM_BACKEND_BUCKET_NAME* com o nome do bucket S3 para armazenar state files. Crie um bucket S3 com versionamento ativado. [Documentação](https://developer.hashicorp.com/terraform/language/backend/s3)
- Adicionar *DOCKER_ACCESS_TOKEN* com o valor do token criado. [Crie o token](https://app.docker.com/settings/personal-access-tokens) com permissões de ler e escrever
- Adicionar *AWS_GITHUB_ACCESS_ROLE_ARN* com o ARN do role criado para permitir o acesso do GitHub Actions. [Use IAM roles to connect GitHub Actions to actions in AWS | AWS Security Blog](https://aws.amazon.com/blogs/security/use-iam-roles-to-connect-github-actions-to-actions-in-aws/)

#### [GitHub variables](https://github.com/Gpossas/ifood-simplificado/settings/variables/actions)
- Adicionar *DOCKER_IMAGE_NAME_ORDERS_MICROSERVICE* com o nome para a imagem do microsserviço de pedidos
- Adicionar *DOCKER_IMAGE_NAME_RESTAURANT_MICROSERVICE* com o nome para a imagem do microsserviço de restaurante
- Adicionar *DOCKER_USERNAME* com seu usuário do Docker Hub

### Executando microsserviços

```bash
# Clone este repositório
$ git clone https://github.com/Gpossas/ifood-simplificado.git

# Acesse a pasta do projeto 
$ cd ifood-simplificado
```

- O microserviço de pedidos(orders_microservice) precisa de uma fila no SQS e DynamoDB criados
- O microserviço de restaurantes(restaurant_microservice) precisa de uma fila no SQS e container com mongodb executando
```bash
# Crie as filas
$ cd terraform/environments/dev/sqs/
$ terraform init
$ terrafrm apply -auto-approve

# Crie o DynamoDB
$ cd ../dynamodb/
$ terraform init
$ terrafrm apply -auto-approve

# Execute o mongoDB no docker compose
$ cd ../../../../restaurant_microservice/docker/
$ docker compose up -d
```

## Melhorias

- Implementar o microsserviço de restaurante combase de dados DocumentDB
- Foi utilizado o Scheduled do Spring Boot para limpar pedidos não respondidos a tempo. Essa solução irá ativar em cada instância ECS, ou seja, cada instância irá fazer uma chamada para a base de dados ao mesmo tempo, isso significa que apenas uma delas irá fazer algo e as outras vão apenas gastar dinheiro no DynamoDB. Seria mais eficiente um Batch job a cada x minutos
- Utilizar S3 para salvar imagens 
