variable "load_balancer_name" {
    description = "Name of the load balancer"
    type        = string
    nullable    = false
}

variable "target_group_name" {
    description = "Name of the target group"
    type        = string
    nullable    = false
}

variable "public_subnets_ids" {
    description = "IDs of the public subnets"
    type        = set(string)
    nullable    = false
}

variable "alb_security_group_ids" {
    description = "IDs of the security groups for the load balancer"
    type        = set(string)
    nullable    = false
}

variable "vpc_id" {
    description = "ID of the VPC"
    type        = string
    nullable    = false
}