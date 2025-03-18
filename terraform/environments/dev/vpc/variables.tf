variable "vpc_name" {
  description = "Name of the VPC"
  type = string
  default = "ifood-simplificado-vpc"
}

variable "vpc_cidr" {
  description = "CIDR of the VPC"
  type        = string
  default     = "10.0.1.0/26"
}

variable "vpc_azs" {
  description = "Availability zones of the VPC"
  type        = list(string)
  default     = ["sa-east-1a", "sa-east-1b"]
}

variable "vpc_private_subnets" {
  description = "Private subnets of the VPC"
  type        = set(string)
  default     = ["10.0.1.0/28", "10.0.1.16/28"]
}

variable "vpc_public_subnets" {
  description = "Public subnets of the VPC"
  type        = set(string)
  default     = ["10.0.1.32/28", "10.0.1.48/28"]
}