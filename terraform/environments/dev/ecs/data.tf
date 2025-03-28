data "terraform_remote_state" "sqs" {
  backend = "s3"
  config = {
    bucket = "gpossas-terraform-backend"
    key    = "ifood-simplificado/dev/sqs.tfstate"
    region = "sa-east-1"
  }
}

data "terraform_remote_state" "vpc" {
  backend = "s3"
  config = {
    bucket = "gpossas-terraform-backend"
    key    = "ifood-simplificado/dev/vpc.tfstate"
    region = "sa-east-1"
  }
}

data "terraform_remote_state" "security_group" {
  backend = "s3"
  config = {
    bucket = "gpossas-terraform-backend"
    key    = "ifood-simplificado/dev/security_group.tfstate"
    region = "sa-east-1"
  }
}

data "terraform_remote_state" "load_balancer" {
  backend = "s3"
  config = {
    bucket = "gpossas-terraform-backend"
    key    = "ifood-simplificado/dev/load_balancer.tfstate"
    region = "sa-east-1"
  }
}