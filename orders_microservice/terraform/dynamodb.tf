resource "aws_dynamodb_table" "orders_database_table" {
  name = var.orders_database_table
  billing_mode = "PAY_PER_REQUEST"
  hash_key = "id"
  range_key = "createdAt"

  attribute {
    name = "id"
    type = "S"
  }

  attribute {
    name = "status"
    type = "S"
  }

  attribute {
    name = "createdAt"
    type = "S"
  }

  global_secondary_index {
    name            = "StatusCreatedAtIndex"
    hash_key        = "status"
    range_key       = "createdAt"
    projection_type = "ALL"
  }

  tags = var.project_tags
}

resource "aws_dynamodb_table" "orders_items_database_table" {
  name = var.order_items_database_table
  billing_mode = "PAY_PER_REQUEST"
  hash_key = "id"

  attribute {
    name = "id"
    type = "S"
  }

  tags = var.project_tags
}