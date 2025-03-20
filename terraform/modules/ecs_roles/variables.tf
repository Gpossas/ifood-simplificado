variable "task_execution_role_name" {
    description = "ECS task execution role"
    type        = string
    nullable    = false
    default     = "ecs-task-execution-role"
}

variable "logs_create_group_policy_name" {
    description = "Name of the logs create group policy"
    type        = string
    nullable    = false
    default     = "ifood-simplificado-logs-create-group-policy"
}

variable "ecs_task_role_name" {
    description = "ECS task role"
    type        = string
    nullable    = false
    default     = "ifood-simplificado-ecs-task-role"
}

variable "create_ecs_task_role" {
  description = "Conditional to create ECS task role"
  type        = bool
  default     = false
}

variable "create_sqs_policy" {
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
  description = "Set of queues ARNs to allow policy"
  type = set(string)
  default = ["*"]
}

variable "create_dynamodb_policy" {
  description = "Conditional to create DynamoDB role to allow ECS tasks to access DynamoDB"
  type = bool
  default = false
}

variable "dynamodb_policy_name" {
  description = "Name of the DynamoDB policy"
  type = string
  default = "ifood-simplificado-dynamodb-policy"
}

variable "dynamodb_resources" {
  description = "Set of resources to allow policy"
  type = set(string)
  default = ["*"]
}