resource "aws_api_gateway_rest_api" "api_gateway" {
  name        = "${var.project_tags.project}-api-gateway"
  description = "API Gateway for ${var.project_tags.project}"
  tags        = var.project_tags

  endpoint_configuration {
    types = ["REGIONAL"]
  }
}

resource "aws_api_gateway_resource" "api_gateway_resource" {
  parent_id   = aws_api_gateway_rest_api.api_gateway.root_resource_id
  rest_api_id = aws_api_gateway_rest_api.api_gateway.id
  path_part   = "{proxy+}"
}

resource "aws_api_gateway_integration" "aws_api_gateway_alb_integration" {
  rest_api_id             = aws_api_gateway_rest_api.api_gateway.id
  resource_id             = aws_api_gateway_resource.api_gateway_resource.id
  type                    = "HTTP_PROXY"
  http_method             = "ANY"
  integration_http_method = "ANY"
  uri                     = "http://${aws_alb.application_load_balancer.dns_name}/{proxy}"
  passthrough_behavior    = "WHEN_NO_MATCH"

  request_parameters = {
    "integration.request.path.proxy" = "method.request.path.proxy"
  }
}

resource "aws_api_gateway_method" "api_gateway_method" {
  rest_api_id   = aws_api_gateway_rest_api.api_gateway.id
  resource_id   = aws_api_gateway_resource.api_gateway_resource.id
  authorization = "NONE"
  http_method   = "ANY"

  request_parameters = {
    "method.request.path.proxy" = true
  }
}

resource "aws_api_gateway_deployment" "api_gateway_deployment" {
  rest_api_id = aws_api_gateway_rest_api.api_gateway.id

  triggers = {
    redeployment = sha1(jsonencode([
      aws_api_gateway_resource.api_gateway_resource.id,
      aws_api_gateway_method.api_gateway_method.id,
      aws_api_gateway_integration.aws_api_gateway_alb_integration.id,
    ]))
  }

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_api_gateway_stage" "api_gateway_stage" {
  rest_api_id   = aws_api_gateway_rest_api.api_gateway.id
  deployment_id = aws_api_gateway_deployment.api_gateway_deployment.id
  stage_name    = "v1"
}