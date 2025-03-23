data "terraform_remote_state" "load_balancer" {
  backend = "s3"
  config = {
    bucket = "gpossas-terraform-backend"
    key    = "ifood-simplificado/dev/load_balancer.tfstate"
    region = "sa-east-1"
  }
}