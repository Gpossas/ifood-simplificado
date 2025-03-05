variable "database_name" {
    type        = string
    default     = "ifood-simplificado-db"
}

variable "orders_placed_queue_name" {
    type = string
    default = "orders-placed-queue.fifo"
}

variable "receive_message_wait_time" {
    description = "Time to wait for a message to arrive"
    type = number
    default = 20
}

variable "message_retention_period_seconds" {
    description = "Time to keep a message in the queue"
    type = number
    default = 300
}

variable "account_id" {
    type = string
    nullable = false
}

variable "project_tags" {
    type = map(string)
    default = {
        project = "ifood-simplificado"
        terraform = "true"
        environment = "dev"
    }
}