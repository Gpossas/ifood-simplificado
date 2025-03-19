terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
  backend "s3" {
    bucket = "gpossas-terraform-backend"
    key    = "ifood-simplificado/dev/api_gateway.tfstate"
    region = "sa-east-1"
  }
}