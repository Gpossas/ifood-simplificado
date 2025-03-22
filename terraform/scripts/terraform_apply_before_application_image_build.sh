#!/bin/bash

# Script to create resources that the applications depends on to build image (e.g. SQS urls)

set -e  # Exit on first error

RESOURCES=("sqs")

cd "$(dirname "$0")/../environments/$ENV/"

for RESOURCE in "${RESOURCES[@]}"; do
  echo "Applying Terraform for $RESOURCE..."

  cd "$RESOURCE/"

  terraform apply -auto-approve

  cd ".." # go back
done
