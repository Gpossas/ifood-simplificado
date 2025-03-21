#!/bin/bash

# Script to initialize terraform backend for all resources

set -e  # Exit on first error

ENV="dev"
RESOURCES=("sqs" "dynamodb" "vpc" "security_group" "load_balancer" "ecs" "api_gateway")

cd "../environments/$ENV/"

for RESOURCE in "${RESOURCES[@]}"; do
  echo "Initializing Terraform for $RESOURCE..."

  cd "$RESOURCE/"

  terraform init \
    -backend-config="bucket=$AWS_S3_TERRAFORM_BACKEND" \
    -backend-config="key=ifood-simplificado/$ENV/$RESOURCE.tfstate" \
    -backend-config="region=$AWS_REGION"

  cd ".." # go back
done
