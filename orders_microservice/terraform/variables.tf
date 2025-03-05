variable "database_name" {
  description = "Name of the database"
  type        = string
  default     = "ifood-simplificado-db"
}

variable "orders_placed_queue_name" {
  description = "Name of the queue for orders placed"
  type        = string
  default     = "orders-placed-queue.fifo"
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