module "load_balancer" {
  source = "../../../modules/load_balancer"

  load_balancer_name = var.load_balancer_name_orders_microservice
  target_group_name  = var.target_group_name_orders_microservice
  vpc_id             = data.terraform_remote_state.vpc.outputs.vpc_id

  alb_security_group_ids = [
    data.terraform_remote_state.security_group.outputs.alb_sg_id
  ]
  public_subnets_ids = data.terraform_remote_state.vpc.outputs.public_subnets
}