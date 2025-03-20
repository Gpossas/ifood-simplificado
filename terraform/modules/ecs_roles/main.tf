module "global" {
  source = "../../global"
}

# ECS task execution role
resource "aws_iam_role" "task_execution_role" {
  name               = var.task_execution_role_name
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

resource "aws_iam_role_policy_attachment" "attach_create_log_group" {
  role       = aws_iam_role.task_execution_role.name
  policy_arn = aws_iam_policy.logs_create_group.arn
}
resource "aws_iam_policy" "logs_create_group" {
  description = "Allows create log group for ECS"
  name        = var.logs_create_group_policy_name
  policy      = data.aws_iam_policy_document.logs_create_group.json
}
data "aws_iam_policy_document" "logs_create_group" {

  statement {
    effect = "Allow"
    actions = [
      "logs:CreateLogGroup"
    ]
    resources = ["*"]
  }
}

# ECS task role
resource "aws_iam_role" "ecs_task_role" {
  count = var.create_ecs_task_role ? 1 : 0

  description        = "Allows ECS tasks to call AWS services on your behalf."
  name               = var.ecs_task_role_name
  assume_role_policy = data.aws_iam_policy_document.ecs_task_trusted_entities_policy.json
  tags               = module.global.project_tag
}

resource "aws_iam_role_policy_attachment" "attach_sqs_policy" {
  count = var.create_sqs_policy ? 1 : 0

  role       = aws_iam_role.ecs_task_role[0].name
  policy_arn = aws_iam_policy.sqs_policy[0].arn
}
resource "aws_iam_policy" "sqs_policy" {
  count = var.create_sqs_policy ? 1 : 0

  name        = var.sqs_send_message_policy_name
  policy      = data.aws_iam_policy_document.sqs_policy[0].json
  description = "Allows to send messages to SQS"
}
data "aws_iam_policy_document" "sqs_policy" {
  count = var.create_sqs_policy ? 1 : 0

  statement {
    effect = "Allow"
    actions = [
      "sqs:*"
    ]
    resources = var.queues_arns
  }
}

resource "aws_iam_role_policy_attachment" "attach_dynamodb_policy" {
  count = var.create_dynamodb_policy ? 1 : 0

  role       = aws_iam_role.ecs_task_role[0].name
  policy_arn = aws_iam_policy.dynamodb_policy[0].arn
}
resource "aws_iam_policy" "dynamodb_policy" {
  count = var.create_dynamodb_policy ? 1 : 0

  name        = var.dynamodb_policy_name
  policy      = data.aws_iam_policy_document.dynamodb_policy[0].json
  description = "Allows DynamoDB access"
}
data "aws_iam_policy_document" "dynamodb_policy" {
  count = var.create_dynamodb_policy ? 1 : 0

  statement {
    effect = "Allow"
    actions = [
      "dynamodb:*"
    ]
    resources = var.dynamodb_resources
  }
}