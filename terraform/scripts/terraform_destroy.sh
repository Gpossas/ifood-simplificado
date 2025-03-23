#!/bin/bash

# Script to destroy terraform resources

set -e  # Exit on first error

RESOURCES=("api_gateway" "ecs" "load_balancer" "security_group" "vpc" "dynamodb" "sqs")

cd "$(dirname "$0")/../environments/$ENV/"

for RESOURCE in "${RESOURCES[@]}"; do
  echo "Destroying Terraform resource $RESOURCE..."

  cd "$RESOURCE/"

  if [ "$RESOURCE" == "ecs" ]; then
        terraform destroy \
            -var="task_definition_microservice_image_orders_microservice=$ORDERS_IMAGE" \
            -var="task_definition_microservice_image_restaurant_microservice=$RESTAURANT_IMAGE" \
            -auto-approve
    else
        terraform destroy -auto-approve
    fi

  cd ".." # go back
done
