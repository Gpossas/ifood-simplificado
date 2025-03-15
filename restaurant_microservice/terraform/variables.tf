variable "region" {
  description = "AWS region"
  type        = string
  default     = "sa-east-1"
}

variable "order_update_status_queue_name" {
  description = "Name of the queue for orders status update"
  type        = string
  default     = "orders-update-status-queue.fifo"
}

variable "receive_message_wait_time" {
  description = "Time to wait for a message to arrive"
  type        = number
  default     = 20
}

variable "message_retention_period_seconds" {
  description = "Time to keep a message in the queue"
  type        = number
  default     = 300
}

variable "account_id" {
  description = "AWS account id"
  type        = string
  nullable    = false
}

variable "project_tags" {
  type = map(string)
  default = {
    project     = "ifood-simplificado"
    terraform   = "true"
    environment = "dev"
  }
}

variable "vpc_name" {
  description = "Name of the VPC"
  type        = string
  default     = "ifood-simplificado-vpc"
}

variable "vpc_cidr" {
  description = "CIDR of the VPC"
  type        = string
  default     = "10.0.1.0/26"
}

variable "vpc_azs" {
  description = "Availability zones of the VPC"
  type        = list(string)
  default     = ["sa-east-1a", "sa-east-1b"]
}

variable "vpc_private_subnets" {
  description = "Private subnets of the VPC"
  type        = set(string)
  default     = ["10.0.1.0/28", "10.0.1.16/28"]
}

variable "vpc_public_subnets" {
  description = "Public subnets of the VPC"
  type        = set(string)
  default     = ["10.0.1.32/28", "10.0.1.48/28"]
}

variable "task_definition_cpu" {
  description = "CPU of the task definition"
  type        = number
  default     = 1024
}

variable "task_definition_memory" {
  description = "Memory of the task definition"
  type        = number
  default     = 2048
}

variable "task_definition_family" {
  description = "Family of the task definition"
  type        = string
  default     = "restaurants-task-family"
}

variable "task_definition_microservice_image" {
  description = "Image of the task definition microservice"
  type        = string
  nullable    = false
}

variable "microservice_name" {
  description = "Microservice name"
  type        = string
  default     = "restaurant-microservice"
}

variable "mongodb_container_name" {
  description = "Name of the MongoDB container"
  type        = string
  default     = "mongodb"
}

variable "service_name" {
  description = "Service name"
  type        = string
  default     = "restaurant-service"
}

variable "security_group_all_traffic" {
  description = "Security group for all traffic"
  type        = string
  default     = -1
}

variable "security_group_anywhere" {
  description = "Security group for all traffic"
  type        = string
  default     = "0.0.0.0/0"
}

variable "security_group_ports" {
  description = "Security group for HTTP traffic"
  type        = map(string)
  default = {
    http  = 80
    https = 443
  }
}

variable "target_group_name" {
  description = "Name of the target group"
  type        = string
  default     = "restaurant-target-group"
}

variable "load_balancer_name" {
  description = "Name of the load balancer"
  type        = string
  default     = "restaurant-load-balancer"
}

variable "application_port" {
  description = "Port of the application"
  type        = number
  default     = 8080
}