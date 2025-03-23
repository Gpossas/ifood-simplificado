output "queue_arn" {
    value = aws_sqs_queue.fifo.arn
}

output "queue_url" {
    value = aws_sqs_queue.fifo.url
}