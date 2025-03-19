variable "cluster_name" {
  description = "The name of the cluster"
  type        = string
  default     = "ifood-simplificado"
  nullable    = false
}

variable "task_definition_family_name_orders_microservice" {
  description = "Family of the task definition"
  type        = string
  default     = "orders-microservice"
  nullable    = false
}

variable "task_definition_cpu_orders_microservice" {
  description = "CPU of the task definition"
  type        = number
  default     = 1024
  nullable    = false
}

variable "task_definition_memory_orders_microservice" {
  description = "Memory of the task definition"
  type        = number
  default     = 2048
  nullable    = false
}

variable "container_name_orders_microservice" {
  description = "Name of the container"
  type        = string
  default     = "orders-microservice"
  nullable    = false
}

variable "task_definition_microservice_image_orders_microservice" {
  description = "Image of the task definition for orders microservice"
  type        = string
  nullable    = false
}


variable "task_definition_family_name_restaurant_microservice" {
  description = "Family of the task definition"
  type        = string
  default     = "restaurant-microservice"
  nullable    = false
}

variable "task_definition_cpu_restaurant_microservice" {
  description = "CPU of the task definition"
  type        = number
  default     = 1024
  nullable    = false
}

variable "task_definition_memory_restaurant_microservice" {
  description = "Memory of the task definition"
  type        = number
  default     = 2048
  nullable    = false
}

variable "container_name_restaurant_microservice" {
  description = "Name of the container"
  type        = string
  default     = "restaurant-microservice"
  nullable    = false
}

variable "task_definition_microservice_image_restaurant_microservice" {
  description = "Image of the task definition for restaurant microservice"
  type        = string
  nullable    = false
}

variable "mongodb_container_name" {
  description = "Name of the MongoDB container"
  type        = string
  default     = "mongodb"
  nullable    = false
}

variable "service_name_orders_microservice" {
  description = "Name of the service"
  type        = string
  default     = "orders-microservice"
  nullable    = false
}