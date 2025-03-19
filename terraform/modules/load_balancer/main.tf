module "global" {
  source = "../../global"
}

resource "aws_alb" "application_load_balancer" {
  name               = var.load_balancer_name
  internal           = false
  load_balancer_type = "application"
  subnets            = var.public_subnets_ids
  security_groups    = var.alb_security_group_ids
  tags               = module.global.project_tag
}

resource "aws_alb_target_group" "ecs_target_group" {
  name        = var.target_group_name
  target_type = "ip"
  port        = local.http_port
  protocol    = local.http_protocol
  vpc_id      = var.vpc_id
  tags        = module.global.project_tag

  health_check {
    port     = local.application_port
    protocol = local.http_protocol
    path     = "/actuator/health"
  }
}

resource "aws_alb_listener" "load_balancer_listener" {
  load_balancer_arn = aws_alb.application_load_balancer.arn
  port              = local.http_port
  protocol          = local.http_protocol
  tags              = module.global.project_tag

  default_action {
    type             = "forward"
    target_group_arn = aws_alb_target_group.ecs_target_group.arn
  }
}