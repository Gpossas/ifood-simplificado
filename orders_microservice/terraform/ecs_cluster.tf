resource "aws_ecs_cluster" "ecs_cluster" {
  name = "${var.project_tags.project}-cluster"

  tags = var.project_tags
}

resource "aws_ecs_cluster_capacity_providers" "ecs_cluster_capacity_provider" {
  cluster_name = aws_ecs_cluster.ecs_cluster.name
  capacity_providers = ["FARGATE"]
}