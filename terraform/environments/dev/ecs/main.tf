module "global" {
  source = "../../../global"
}

module "cluster" {
  source       = "../../../modules/ecs_cluster_fargate"
  cluster_name = var.cluster_name
}

module "ecs_roles" {
  source                 = "../../../modules/ecs_roles"
  create_ecs_task_role   = true
  create_sqs_policy      = true
  create_dynamodb_policy = true

  queues_arns = [
    data.terraform_remote_state.sqs.outputs.orders_microservice_queue_arn,
    data.terraform_remote_state.sqs.outputs.restaurant_microservice_queue_arn
  ]
}

# Orders Microservice
module "task_definition_orders_microservice" {
  source                      = "../../../modules/ecs_task_definition_fargate"
  task_definition_family_name = var.task_definition_family_name_orders_microservice
  task_definition_cpu         = var.task_definition_cpu_orders_microservice
  task_definition_memory      = var.task_definition_memory_orders_microservice
  task_execution_role_arn     = module.ecs_roles.task_execution_role_arn
  task_role_arn               = module.ecs_roles.ecs_task_role_arn

  container_definition = jsonencode([
    {
      name : var.container_name_orders_microservice,
      image : var.task_definition_microservice_image_orders_microservice,
      essential : true,
      cpu : var.task_definition_cpu_orders_microservice,
      memory : var.task_definition_memory_orders_microservice,
      environment = [
        {
          "name": "AWS_SQS_ORDER_REQUEST_URL"
          "value": data.terraform_remote_state.sqs.outputs.orders_microservice_queue_url
        },
        {
          "name": "RESTAURANT_RESPONSE_TIME_LIMIT_IN_SECONDS"
          "value": var.restaurant_response_time_limit
        },
        {
          "name": "INITIAL_DELAY_IN_SECONDS"
          "value": var.initial_delay
        }
      ]
      portMappings : [
        {
          "containerPort" : local.application_port,
          "hostPort" : local.application_port
        }
      ]
      logConfiguration : {
        "logDriver" : "awslogs",
        "options" : {
          "awslogs-group" : "/ecs/${var.task_definition_family_name_orders_microservice}-family",
          "awslogs-region" : module.global.aws_region,
          "awslogs-stream-prefix" : module.global.project_tag.project,
          "awslogs-create-group" : "true",
          "mode" : "non-blocking",
        }
      }
    }
  ])
}

module "service_orders_microservice" {
  source = "../../../modules/ecs_service_fargate"

  service_name        = "${var.service_name_orders_microservice}-service"
  cluster_id          = module.cluster.cluster_id
  task_definition_arn = module.task_definition_orders_microservice.task_definition_arn

  private_subnets = data.terraform_remote_state.vpc.outputs.private_subnets
  security_groups_ids = [
    data.terraform_remote_state.security_group.outputs.alb_sg_id,
    data.terraform_remote_state.security_group.outputs.ecs_sg_id
  ]

  ecs_target_group_arn = data.terraform_remote_state.load_balancer.outputs.ecs_target_group_orders_microservice_arn
  container_name       = var.container_name_orders_microservice
}

# Restaurant Microservice
module "task_definition_restaurant_microservice" {
  source                      = "../../../modules/ecs_task_definition_fargate"
  task_definition_family_name = var.task_definition_family_name_restaurant_microservice
  task_definition_cpu         = var.task_definition_cpu_restaurant_microservice
  task_definition_memory      = var.task_definition_memory_restaurant_microservice
  task_execution_role_arn     = module.ecs_roles.task_execution_role_arn
  task_role_arn               = module.ecs_roles.ecs_task_role_arn

  container_definition = jsonencode([
    {
      name : var.container_name_restaurant_microservice,
      image : var.task_definition_microservice_image_restaurant_microservice,
      essential : false,
      cpu : var.task_definition_cpu_restaurant_microservice / 2,
      memory : var.task_definition_memory_restaurant_microservice / 2,
      environment = [
        {
          "name": "AWS_SQS_ORDER_REQUEST_URL"
          "value": data.terraform_remote_state.sqs.outputs.restaurant_microservice_queue_url
        }
      ],
      portMappings : [
        {
          "containerPort" : local.application_port
        }
      ],
      dependsOn : [
        {
          "containerName" : var.mongodb_container_name,
          "condition" : "HEALTHY"
        }
      ]
      logConfiguration : {
        "logDriver" : "awslogs",
        "options" : {
          "awslogs-group" : "/ecs/${var.task_definition_family_name_restaurant_microservice}-family",
          "awslogs-region" : module.global.aws_region,
          "awslogs-stream-prefix" : module.global.project_tag.project,
          "awslogs-create-group" : "true",
          "mode" : "non-blocking",
        }
      }
    },
    {
      name : var.mongodb_container_name,
      image : "mongo:latest",
      essential : true,
      cpu : var.task_definition_cpu_restaurant_microservice / 2,
      memory : var.task_definition_memory_restaurant_microservice / 2,
      portMappings : [
        {
          "containerPort" : local.mongodb_port
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
      ],
    }
  ])
}

module "service_restaurant_microservice" {
  source = "../../../modules/ecs_service_fargate"

  service_name        = "${var.service_name_restaurant_microservice}-service"
  cluster_id          = module.cluster.cluster_id
  task_definition_arn = module.task_definition_restaurant_microservice.task_definition_arn

  private_subnets = data.terraform_remote_state.vpc.outputs.private_subnets
  security_groups_ids = [
    data.terraform_remote_state.security_group.outputs.alb_sg_id,
    data.terraform_remote_state.security_group.outputs.ecs_sg_id
  ]

  ecs_target_group_arn = data.terraform_remote_state.load_balancer.outputs.ecs_target_group_restaurant_microservice_arn
  container_name       = var.container_name_restaurant_microservice
}