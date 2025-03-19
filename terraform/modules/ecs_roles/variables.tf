variable "task_execution_role_name" {
    description = "ECS task execution role"
    type        = string
    nullable    = false
    default     = "ecs-task-execution-role"
}

variable "ecs_task_role_name" {
    description = "ECS task role"
    type        = string
    nullable    = false
    default     = "ifood-simplificado-ecs-task-role"
}

variable "create_sqs_role" {
  description = "Conditional to create SQS role to allow ECS tasks to send messages to SQS"
  type        = bool
  default     = false
}

variable "sqs_send_message_policy_name" {
  description = "Name of the SQS send message policy"
  type = string
  default = "ifood-simplificado-sqs-send-message-policy"
}

variable "queues_arns" {
  description = "Set of queues ARNs"
  type = set(string)
  default = ["*"]
}