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

#### Docker
- Utilize o arquivo *docker.yml* para executar o mongodb no microsserviço de restaurante
- O mongoDB será executado na porta 27017
```bash
$ cd restaurant_microservice/docker
$ docker compose up -d
```

### Executando microsserviços

```bash
# Clone este repositório
$ git clone https://github.com/Gpossas/ifood-simplificado.git

# Acesse a pasta do projeto 
$ cd ifood-simplificado
```

#### - Executando a aplicação em local host

Caso você não queira criar toda a infraestutura e executar o projeto em local host, você pode criar apenas o dynamoDB e as filas SQS.

##### Criando as filas 

```bash
# Entre na pasta do terrafom com as filas
$ cd terraform/environments/dev/sqs

# Inicie o backend do terraform
$ terraform init \
  -backend-config="bucket=nome-do-seu-bucket-s3" \
  -backend-config="key=ifood-simplificado/dev/sqs" \
  -backend-config="region=sa-east-1"
  
# Crie os recursos utilizando terraform
$ terraform apply -auto-approve
```

Após o apply você irá receberá outputs das filas.
- Passe uma variável de ambiente chamada *AWS_SQS_ORDER_REQUEST_URL* para *orders_microservice* com o valor do output *orders_microservice_queue_url*
- Passe uma variável de ambiente chamada *AWS_SQS_ORDER_STATUS_UPDATE_URL* para *restaurant_microservice* com o valor do output *restaurant_microservice_queue_url*

##### Criando DynamoDB

```bash
# Entre na pasta do terrafom com o dynamoDB
$ cd ../dynamodb

# Inicie o backend do terraform
$ terraform init \
  -backend-config="bucket=nome-do-seu-bucket-s3" \
  -backend-config="key=ifood-simplificado/dev/dynamodb" \
  -backend-config="region=sa-east-1"
  
# Crie os recursos utilizando terraform
$ terraform apply -auto-approve
```

Obs: utilize esse comando para destruir os recursos alocados do DynamoDB
```bash
$ terraform destroy -auto-approve
```

#### - Executando a aplicação na internet

1. Na aba de actions execute o workflow Development environment CI/CD para criar a infraestrutura completa
2. Entre no console da AWS e utilize a URL do API Gateway em “stage”

Obs: utilize o workflow *Destroy all terraform resources* quando terminar

## Melhorias

- Alterar a base de dados para DocumentDB do microsserviço de restaurante
- Foi utilizado o Scheduled do Spring Boot para limpar pedidos não respondidos a tempo. Essa solução irá ativar em cada instância ECS, ou seja, cada instância irá fazer uma chamada para a base de dados ao mesmo tempo, isso significa que apenas uma delas irá fazer algo e as outras vão apenas gastar dinheiro no DynamoDB. Seria mais eficiente um Batch job a cada x minutos
- Utilizar S3 para salvar imagens 
- Atualmente o projeto força uma resposta do microsserviço de restaurante de aceitar um pedido. Essencialmente deveria ter um microsserviço de notificação para atuar como uma ponte entre os microsserviços e entre o cliente.