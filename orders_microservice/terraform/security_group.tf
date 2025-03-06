resource "aws_security_group" "alb_security_group" {
  description = "Security group for application load balancer"
  name        = "${var.project_tags.project}-alb-security-group"
  vpc_id      = module.vpc.vpc_id
}
resource "aws_vpc_security_group_ingress_rule" "alb_security_group_http_ingress_rule" {
  description       = "Allow HTTP traffic from internet"
  security_group_id = aws_security_group.alb_security_group.id
  cidr_ipv4         = var.security_group_anywhere
  ip_protocol       = var.security_group_ports.http
}
resource "aws_vpc_security_group_ingress_rule" "alb_security_group_https_ingress_rule" {
  description       = "Allow HTTPS traffic from internet"
  security_group_id = aws_security_group.alb_security_group.id
  cidr_ipv4         = var.security_group_anywhere
  ip_protocol       = var.security_group_ports.https
}
resource "aws_vpc_security_group_egress_rule" "alb_security_group_egress_rule" {
  description       = "Allow all outbound traffic"
  security_group_id = aws_security_group.alb_security_group.id
  cidr_ipv4         = var.security_group_anywhere
  ip_protocol       = var.security_group_all_traffic
}

resource "aws_security_group" "ecs_security_group" {
  description = "Security group for ECS"
  name        = "${var.project_tags.project}-ecs-security-group"
  vpc_id      = module.vpc.vpc_id
}
resource "aws_vpc_security_group_ingress_rule" "ecs_security_group_ingress_rule" {
  description                  = "Allow traffic from application load balancer security group"
  security_group_id            = aws_security_group.ecs_security_group.id
  referenced_security_group_id = aws_security_group.alb_security_group.id
  ip_protocol                  = var.security_group_all_traffic
}
resource "aws_vpc_security_group_egress_rule" "ecs_security_group_egress_rule" {
  description       = "Allow all outbound traffic"
  security_group_id = aws_security_group.alb_security_group.id
  cidr_ipv4         = var.security_group_anywhere
  ip_protocol       = var.security_group_all_traffic
}