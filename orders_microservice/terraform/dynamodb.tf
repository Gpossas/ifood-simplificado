resource "aws_dynamodb_table" "orders_database_table" {
  name = var.orders_database_table
  billing_mode = "PAY_PER_REQUEST"
  hash_key = "id"

  attribute {
    name = "id"
    type = "S"
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