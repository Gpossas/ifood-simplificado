resource "aws_sqs_queue" "orders-placed-queue" {
  name       = var.order_update_status_queue_name
  fifo_queue = true

  content_based_deduplication = false
  deduplication_scope         = "messageGroup"
  fifo_throughput_limit       = "perMessageGroupId"

  sqs_managed_sse_enabled = true

  receive_wait_time_seconds = var.receive_message_wait_time
  message_retention_seconds = var.message_retention_period_seconds

  tags = var.project_tags
}

data "aws_iam_policy_document" "sqs_policy" {
  statement {
    actions   = ["sqs:SendMessage"]
    resources = [aws_sqs_queue.orders-placed-queue.arn]
    principals {
      type        = "AWS"
      identifiers = [var.account_id]
    }
  }
}

resource "aws_sqs_queue_policy" "sqs_policy" {
  queue_url = aws_sqs_queue.orders-placed-queue.id
  policy    = data.aws_iam_policy_document.sqs_policy.json
}