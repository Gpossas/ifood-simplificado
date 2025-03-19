variable "task_definition_family_name" {
    description = "Family name of the task definition"
    type        = string
    nullable    = false
}

variable "task_definition_cpu" {
    description = "CPU of the task definition"
    type        = number
    default     = 1024
}

variable "task_definition_memory" {
    description = "Memory of the task definition"
    type        = number
    default     = 2048
}

variable "container_definition" {
    description = "Container definition of the task definition"
    type        = any
}