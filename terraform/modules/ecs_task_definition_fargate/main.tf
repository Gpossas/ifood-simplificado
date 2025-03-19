module "global" {
  source = "../../global"
}

resource "aws_ecs_task_definition" "ecs_task_definition" {
  family                   = "${var.task_definition_family_name}-family"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = var.task_definition_cpu
  memory                   = var.task_definition_memory
  execution_role_arn       = var.task_execution_role_arn
  task_role_arn            = var.task_role_arn
  container_definitions    = var.container_definition

  runtime_platform {
    operating_system_family = "LINUX"
    cpu_architecture        = "ARM64"
  }

  tags = module.global.project_tag
}