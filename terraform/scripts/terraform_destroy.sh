#!/bin/bash

# Script to destroy terraform resources

set -e  # Exit on first error

RESOURCES=("api_gateway" "ecs" "load_balancer" "security_group" "vpc" "dynamodb" "sqs")

cd "$(dirname "$0")/../environments/$ENV/"

for RESOURCE in "${RESOURCES[@]}"; do
  echo "Destroying Terraform resource $RESOURCE..."

  cd "$RESOURCE/"

  terraform destroy -auto-approve

  cd ".." # go back
done
