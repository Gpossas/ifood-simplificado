#!/bin/bash

# Script to validate terraform for all resources

set -e  # Exit on first error

ENV="dev"
RESOURCES=("sqs" "dynamodb" "vpc" "security_group" "load_balancer" "ecs" "api_gateway")

cd "$(dirname "$0")/../environments/$ENV/"

for RESOURCE in "${RESOURCES[@]}"; do
  echo "Initializing Terraform for $RESOURCE..."

  cd "$RESOURCE/"

  terraform validate -json

  cd ".." # go back
done
