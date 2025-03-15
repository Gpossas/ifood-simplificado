# ECS task execution role
resource "aws_iam_role" "task_execution_role" {
  name               = "${var.project_tags.project}-task-execution-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_task_trusted_entities_policy.json
}
data "aws_iam_policy_document" "ecs_task_trusted_entities_policy" {
  statement {
    actions = ["sts:AssumeRole"]
    principals {
      type        = "Service"
      identifiers = ["ecs-tasks.amazonaws.com"]
    }
  }
}
resource "aws_iam_role_policy_attachment" "task_execution_role_policy" {
  role       = aws_iam_role.task_execution_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

# ECS task role
resource "aws_iam_role" "ecs_task_role" {
  name               = "${var.project_tags.project}-ecs-task-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_task_trusted_entities_policy.json
  description        = "Allows ECS tasks to call AWS services on your behalf."
  tags               = var.project_tags
}
resource "aws_iam_role_policy_attachment" "attach_sqs_policy" {
  role       = aws_iam_role.ecs_task_role.name
  policy_arn = aws_iam_policy.sqs_send_message_policy.arn
}
resource "aws_iam_policy" "sqs_send_message_policy" {
  name        = "${var.project_tags.project}-sqs-send-message-policy"
  policy      = data.aws_iam_policy_document.sqs_send_message_policy.json
  description = "Allows to send messages to SQS"
}
data "aws_iam_policy_document" "sqs_send_message_policy" {
  statement {
    effect = "Allow"
    actions = [
      "sqs:SendMessage"
    ]
    resources = [aws_sqs_queue.orders-placed-queue.arn]
  }
}