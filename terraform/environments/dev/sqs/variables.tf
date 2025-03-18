variable "order_microservice_queue_name" {
  description = "Name of the queue for orders status update"
  type = string
  default = "orders-update-status-queue.fifo"
}

variable "restaurant_microservice_queue_name" {
  description = "Name of the queue for orders placed"
  type = string
  default = "orders-placed-queue.fifo"
}