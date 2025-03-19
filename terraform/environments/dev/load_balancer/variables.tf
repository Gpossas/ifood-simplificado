variable "load_balancer_name_orders_microservice" {
  description = "Name of the load balancer"
  type        = string
  nullable    = false
  default     = "orders-load-balancer"
}

variable "target_group_name_orders_microservice" {
  description = "Name of the target group"
  type        = string
  nullable    = false
  default     = "orders-target-group"
}