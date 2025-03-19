output "task_execution_role_arn" {
  description = "ARN of the task execution role"
  value = aws_iam_role.task_execution_role.arn
}

output "ecs_task_role_arn" {
  description = "ARN of the ECS task role"
  value = aws_iam_role.ecs_task_role.arn
}