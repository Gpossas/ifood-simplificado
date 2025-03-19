variable "load_balancer_name_orders_microservice" {
  description = "Name of the load balancer for orders microservice"
  type        = string
  nullable    = false
  default     = "orders-load-balancer"
}

variable "target_group_name_orders_microservice" {
  description = "Name of the target group for orders microservice"
  type        = string
  nullable    = false
  default     = "orders-target-group"
}

variable "load_balancer_name_restaurant_microservice" {
  description = "Name of the load balancer for restaurant microservice"
  type        = string
  nullable    = false
  default     = "restaurant-load-balancer"
}

variable "target_group_name_restaurant_microservice" {
  description = "Name of the target group for restaurant microservice"
  type        = string
  nullable    = false
  default     = "restaurant-target-group"
}