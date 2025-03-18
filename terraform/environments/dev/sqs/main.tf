module "orders_microservice_queue" {
  source = "../../../modules/fifo_queue"
  queue_name = var.order_microservice_queue_name
}

module "restaurant_microservice_queue" {
  source = "../../../modules/fifo_queue"
  queue_name = var.restaurant_microservice_queue_name
}