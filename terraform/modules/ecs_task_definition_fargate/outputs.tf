output "max_cpu_container" {
  description = "CPU of the task definition"
  value = aws_ecs_task_definition.ecs_task_definition.cpu
}

output "max_memory_container" {
  description = "Memory of the task definition"
  value = aws_ecs_task_definition.ecs_task_definition.memory
}

output "task_definition_arn" {
  description = "ARN of the task definition"
  value = aws_ecs_task_definition.ecs_task_definition.arn
}