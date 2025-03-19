module "global" {
    source = "../../../global"
}

resource "aws_security_group" "alb_security_group" {
  description = "Security group for application load balancer"
  name        = "${module.global.project_tag.project}-alb-security-group"
  vpc_id      = data.terraform_remote_state.vpc.outputs.vpc_id
  tags        = module.global.project_tag
}
resource "aws_vpc_security_group_ingress_rule" "alb_security_group_http_ingress_rule" {
  description       = "Allow HTTP traffic from internet"
  security_group_id = aws_security_group.alb_security_group.id
  cidr_ipv4         = local.from_anywhere
  ip_protocol       = local.tcp_protocol
  from_port         = local.http_port
  to_port           = local.http_port
}
resource "aws_vpc_security_group_ingress_rule" "alb_security_group_https_ingress_rule" {
  description       = "Allow HTTPS traffic from internet"
  security_group_id = aws_security_group.alb_security_group.id
  cidr_ipv4         = local.from_anywhere
  ip_protocol       = local.tcp_protocol
  from_port         = local.https_port
  to_port           = local.https_port
}
resource "aws_vpc_security_group_egress_rule" "alb_security_group_egress_rule" {
  description       = "Allow all outbound traffic"
  security_group_id = aws_security_group.alb_security_group.id
  cidr_ipv4         = local.from_anywhere
  ip_protocol       = local.all_traffic
}

resource "aws_security_group" "ecs_security_group" {
  description = "Security group for ECS"
  name        = "${module.global.project_tag.project}-ecs-security-group"
  vpc_id      = data.terraform_remote_state.vpc.outputs.vpc_id
  tags        = module.global.project_tag
}
resource "aws_vpc_security_group_ingress_rule" "ecs_security_group_ingress_rule" {
  description                  = "Allow traffic from application load balancer security group"
  security_group_id            = aws_security_group.ecs_security_group.id
  referenced_security_group_id = aws_security_group.alb_security_group.id
  ip_protocol                  = local.tcp_protocol
  from_port                    = local.http_port
  to_port                      = local.application_port
}
resource "aws_vpc_security_group_egress_rule" "ecs_security_group_egress_rule" {
  description       = "Allow all outbound traffic"
  security_group_id = aws_security_group.ecs_security_group.id
  cidr_ipv4         = local.from_anywhere
  ip_protocol       = local.all_traffic
}