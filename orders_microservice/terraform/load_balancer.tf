// todo
resource "aws_lb_target_group" "ecs_target_group" {
    name     = var.target_group_name
    port     = 8080
    protocol = "HTTP"
    vpc_id   = module.vpc.vpc_id
}