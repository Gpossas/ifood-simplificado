variable "region" {
  description = "AWS region"
  type        = string
  default     = "sa-east-1"
}

variable "order_update_status_queue_name" {
  description = "Name of the queue for orders placed"
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
