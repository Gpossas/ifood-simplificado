#!/bin/bash

# Script to create resources not dependent on the application image

set -e  # Exit on first error

RESOURCES=("dynamodb" "vpc" "security_group" "load_balancer" "ecs" "api_gateway")

cd "$(dirname "$0")/../environments/$ENV/"

for RESOURCE in "${RESOURCES[@]}"; do
  echo "Applying Terraform for $RESOURCE..."

  cd "$RESOURCE/"

  terraform apply \
    -var="task_definition_microservice_image_orders_microservice=$ORDERS_IMAGE" \
    -var="task_definition_microservice_image_restaurant_microservice=$RESTAURANT_IMAGE" \
    -auto-approve

  cd ".." # go back
done
