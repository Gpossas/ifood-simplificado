module "global" {
    source = "../../global"
}

resource "aws_ecs_cluster" "fargate" {
  name = "${var.cluster_name}-cluster"
  tags = module.global.project_tag
}

resource "aws_ecs_cluster_capacity_providers" "ecs_cluster_capacity_provider" {
  cluster_name       = aws_ecs_cluster.fargate.name
  capacity_providers = ["FARGATE"]
}