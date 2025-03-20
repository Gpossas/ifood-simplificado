resource "aws_ecs_task_definition" "ecs_task_definition" {
  family                   = var.task_definition_family
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = var.task_definition_cpu
  memory                   = var.task_definition_memory
  execution_role_arn       = aws_iam_role.task_execution_role.arn
  task_role_arn            = aws_iam_role.ecs_task_role.arn
  container_definitions = jsonencode([
    {
      name : var.microservice_name,
      image : "${var.task_definition_microservice_image}:latest",
      essential : false,
      cpu : var.task_definition_cpu / 2,
      memory : var.task_definition_memory / 2,
      portMappings : [
        {
          "containerPort" : 8080
        }
      ],
      dependsOn : [
        {
          "containerName" : var.mongodb_container_name
          "condition" : "HEALTHY"
        }
      ],
      logConfiguration : {
        "logDriver" : "awslogs",
        "options" : {
          "awslogs-group" : "/ecs/${var.task_definition_family}",
          "awslogs-region" : var.region,
          "awslogs-stream-prefix" : var.project_tags.project,
          "awslogs-create-group" : "true",
          "mode" : "non-blocking",
        }
      }
    },
    {
      name : var.mongodb_container_name,
      image : "mongo:latest",
      essential : true,
      cpu : var.task_definition_cpu / 2,
      memory : var.task_definition_memory / 2,
      portMappings : [
        {
          "containerPort" : 27017
        }
      ],
      healthCheck = {
        command     = ["CMD", "mongosh", "--eval", "db.runCommand('ping').ok"]
        interval    = 10
        retries     = 5
        startPeriod = 10
        timeout     = 5
      },
      environment = [
        {
          "name" : "MONGO_INITDB_ROOT_USERNAME",
          "value" : "admin"
        },
        {
          "name" : "MONGO_INITDB_ROOT_PASSWORD",
          "value" : "admin"
        }
      ]
    }
  ])

  runtime_platform {
    operating_system_family = "LINUX"
    cpu_architecture        = "ARM64"
  }

  tags = var.project_tags
}

data "aws_ecs_container_definition" "ecs_container_definition" {
  task_definition = aws_ecs_task_definition.ecs_task_definition.arn
  container_name  = var.microservice_name
}