module "global" {
    source = "../../global"
}

resource "aws_sqs_queue" "fifo" {
  name       = var.queue_name
  fifo_queue = true

  content_based_deduplication = false
  deduplication_scope         = "messageGroup"
  fifo_throughput_limit       = "perMessageGroupId"

  sqs_managed_sse_enabled = true

  receive_wait_time_seconds = var.receive_message_wait_time
  message_retention_seconds = var.message_retention_period_seconds

  tags = module.global.project_tag
}