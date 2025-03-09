resource "aws_alb" "application_load_balancer" {
  name               = var.load_balancer_name
  internal           = false
  load_balancer_type = "application"
  subnets            = module.vpc.public_subnets
  security_groups    = [aws_security_group.alb_security_group.id]
  tags               = var.project_tags
}

resource "aws_alb_target_group" "ecs_target_group" {
  name        = var.target_group_name
  target_type = "ip"
  port        = var.security_group_ports.http
  protocol    = "HTTP"
  vpc_id      = module.vpc.vpc_id
  tags        = var.project_tags

  health_check {
    port     = var.application_port
    protocol = "HTTP"
    path     = "/actuator/health"
  }
}

resource "aws_alb_listener" "load_balancer_listener" {
  load_balancer_arn = aws_alb.application_load_balancer.arn
  port              = var.security_group_ports.http
  protocol          = "HTTP"
  tags              = var.project_tags

  default_action {
    type             = "forward"
    target_group_arn = aws_alb_target_group.ecs_target_group.arn
  }
}