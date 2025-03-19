module "global" {
    source = "../../../global"
}

resource "aws_dynamodb_table" "orders_database_table" {
  name = var.order_table_name
  billing_mode = "PAY_PER_REQUEST"
  hash_key = "id"

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

  tags = module.global.project_tag
}

resource "aws_dynamodb_table" "orders_items_database_table" {
  name = var.order_item_table_name
  billing_mode = "PAY_PER_REQUEST"
  hash_key = "id"

  attribute {
    name = "id"
    type = "S"
  }

  tags = module.global.project_tag
}