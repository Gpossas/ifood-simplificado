variable "project_tags" {
  type = map(string)
  default = {
    "project" = "ifood-simplificado"
    "author" = "gpossas"
    "terraform" = "true"
  }
}

variable "aws_region" {
  description = "AWS region to deploy the services"
  type = string
  default = "sa-east-1"
}