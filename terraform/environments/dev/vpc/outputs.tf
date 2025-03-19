output "private_subnets" {
  description = "Set of IDs of private subnets"
  value = module.vpc.private_subnets
}

output "public_subnets" {
  description = "Set of IDs of public subnets"
  value = module.vpc.public_subnets
}

output "vpc_id" {
  description = "ID of the VPC"
  value = module.vpc.vpc_id
}