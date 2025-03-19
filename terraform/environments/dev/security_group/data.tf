data "terraform_remote_state" "vpc" {
  backend = "s3"
  config = {
    bucket = "gpossas-terraform-backend"
    key    = "ifood-simplificado/dev/vpc.tfstate"
    region = "sa-east-1"
  }
}