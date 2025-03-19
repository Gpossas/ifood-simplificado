output "ecs_target_group_orders_microservice_arn" {
  value = module.load_balancer_orders_microservice.ecs_target_group_arn
}

output "ecs_target_group_restaurant_microservice_arn" {
  value = module.load_balancer_restaurant_microservice.ecs_target_group_arn
}