output "private_subnets" {
  description = "Set of IDs of private subnets"
  value = module.vpc.private_subnets
}

output "vpc_id" {
  value = module.vpc.vpc_id
}