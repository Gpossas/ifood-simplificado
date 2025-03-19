variable "service_name" {
    description = "Name of the service"
    type        = string
    nullable    = false
}

variable "instances_count" {
    description = "Number of instances"
    type        = number
    default     = 2
    nullable    = false
}

variable "cluster_id" {
    description = "ID of the cluster"
    type        = string
    nullable    = false
}

variable "task_definition_arn" {
    description = "ARN of the task definition"
    type        = string
    nullable    = false
}

variable "private_subnets" {
    description = "Private subnets of the VPC"
    type        = set(string)
    nullable    = false
}

variable "security_groups_ids" {
    description = "IDs of the security groups"
    type        = set(string)
    nullable    = false
}

variable "ecs_target_group_arn" {
    description = "ARN of the target group for ECS"
    type        = string
    nullable    = false
}

variable "container_name" {
    description = "Name of the container to attach load balancer"
    type        = string
    nullable    = false
}