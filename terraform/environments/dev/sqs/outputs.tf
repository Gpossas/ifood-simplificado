output "orders_microservice_queue_arn" {
  description = "ARN of the orders microservice queue"
  value = module.orders_microservice_queue.queue_arn
}

output "restaurant_microservice_queue_arn" {
  description = "ARN of the restaurant microservice queue"
  value = module.restaurant_microservice_queue.queue_arn
}

output "orders_microservice_queue_url" {
  description = "URL of the orders microservice queue"
  value = module.orders_microservice_queue.queue_url
}

output "restaurant_microservice_queue_url" {
  description = "URL of the restaurant microservice queue"
  value = module.restaurant_microservice_queue.queue_url
}