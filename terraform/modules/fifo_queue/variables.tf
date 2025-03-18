variable "queue_name" {
  description = "Name of the queue"
  type        = string
  nullable = false
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