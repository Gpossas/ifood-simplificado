module "global" {
  source = "../../../global"
}

resource "aws_api_gateway_rest_api" "api_gateway" {
  description = "API Gateway"
  name        = "${var.api_gateway_name}-api-gateway"
  tags        = module.global.project_tag

  endpoint_configuration {
    types = ["REGIONAL"]
  }
}

# Order microservice

resource "aws_api_gateway_resource" "order_resource_path" {
  rest_api_id = aws_api_gateway_rest_api.api_gateway.id
  parent_id   = aws_api_gateway_rest_api.api_gateway.root_resource_id
  path_part   = "order"

  depends_on = [
    aws_api_gateway_rest_api.api_gateway
  ]
}

resource "aws_api_gateway_resource" "order_proxy_resource_path" {
  rest_api_id = aws_api_gateway_rest_api.api_gateway.id
  parent_id   = aws_api_gateway_resource.order_resource_path.id
  path_part   = "{proxy+}"
  depends_on = [
    aws_api_gateway_rest_api.api_gateway,
    aws_api_gateway_resource.order_resource_path
  ]
}

resource "aws_api_gateway_method" "order" {
  rest_api_id   = aws_api_gateway_rest_api.api_gateway.id
  resource_id   = aws_api_gateway_resource.order_proxy_resource_path.id
  authorization = "NONE"
  http_method   = "ANY"

  request_parameters = {
    "method.request.path.proxy" = true
  }

  depends_on = [
    aws_api_gateway_rest_api.api_gateway,
    aws_api_gateway_resource.order_resource_path,
    aws_api_gateway_resource.order_proxy_resource_path
  ]
}

resource "aws_api_gateway_integration" "alb_integration_orders_microservice" {
  rest_api_id             = aws_api_gateway_rest_api.api_gateway.id
  resource_id             = aws_api_gateway_resource.order_proxy_resource_path.id
  type                    = "HTTP_PROXY"
  http_method             = "ANY"
  integration_http_method = "ANY"
  uri                     = "http://${data.terraform_remote_state.load_balancer.outputs.orders_microservice_dns_name}/{proxy}"
  passthrough_behavior    = "WHEN_NO_MATCH"

  request_parameters = {
    "integration.request.path.proxy" = "method.request.path.proxy"
  }

  depends_on = [
    aws_api_gateway_rest_api.api_gateway,
    aws_api_gateway_resource.order_resource_path,
    aws_api_gateway_resource.order_proxy_resource_path,
    aws_api_gateway_method.order
  ]
}

# Restaurant microservice

resource "aws_api_gateway_resource" "restaurant_resource_path" {
  rest_api_id = aws_api_gateway_rest_api.api_gateway.id
  parent_id   = aws_api_gateway_rest_api.api_gateway.root_resource_id
  path_part   = "restaurant"

  depends_on = [
    aws_api_gateway_rest_api.api_gateway
  ]
}

resource "aws_api_gateway_resource" "restaurant_proxy_resource_path" {
  rest_api_id = aws_api_gateway_rest_api.api_gateway.id
  parent_id   = aws_api_gateway_resource.restaurant_resource_path.id
  path_part   = "{proxy+}"

  depends_on = [
    aws_api_gateway_rest_api.api_gateway,
    aws_api_gateway_resource.restaurant_resource_path
  ]
}

resource "aws_api_gateway_method" "restaurant" {
  rest_api_id   = aws_api_gateway_rest_api.api_gateway.id
  resource_id   = aws_api_gateway_resource.restaurant_proxy_resource_path.id
  authorization = "NONE"
  http_method   = "ANY"

  request_parameters = {
    "method.request.path.proxy" = true
  }

  depends_on = [
    aws_api_gateway_rest_api.api_gateway,
    aws_api_gateway_resource.restaurant_resource_path,
    aws_api_gateway_resource.restaurant_proxy_resource_path,
  ]
}

resource "aws_api_gateway_integration" "alb_integration_restaurant_microservice" {
  rest_api_id             = aws_api_gateway_rest_api.api_gateway.id
  resource_id             = aws_api_gateway_resource.restaurant_proxy_resource_path.id
  type                    = "HTTP_PROXY"
  http_method             = "ANY"
  integration_http_method = "ANY"
  uri                     = "http://${data.terraform_remote_state.load_balancer.outputs.restaurant_microservice_dns_name}/{proxy}"
  passthrough_behavior    = "WHEN_NO_MATCH"

  request_parameters = {
    "integration.request.path.proxy" = "method.request.path.proxy"
  }

  depends_on = [
    aws_api_gateway_rest_api.api_gateway,
    aws_api_gateway_resource.restaurant_resource_path,
    aws_api_gateway_resource.restaurant_proxy_resource_path,
    aws_api_gateway_method.restaurant
  ]
}

# Deployment

resource "aws_api_gateway_deployment" "deployment" {
  rest_api_id = aws_api_gateway_rest_api.api_gateway.id

  triggers = {
    redeployment = sha1(jsonencode([
      aws_api_gateway_resource.order_proxy_resource_path.id,
      aws_api_gateway_method.order.id,
      aws_api_gateway_integration.alb_integration_orders_microservice.id,

      aws_api_gateway_resource.restaurant_proxy_resource_path.id,
      aws_api_gateway_method.restaurant.id,
      aws_api_gateway_integration.alb_integration_restaurant_microservice.id
    ]))
  }

  lifecycle {
    create_before_destroy = true
  }

  depends_on = [
    aws_api_gateway_method.order,
    aws_api_gateway_method.restaurant,

    aws_api_gateway_integration.alb_integration_orders_microservice,
    aws_api_gateway_integration.alb_integration_restaurant_microservice,

    aws_api_gateway_resource.order_proxy_resource_path,
    aws_api_gateway_resource.restaurant_proxy_resource_path,

    aws_api_gateway_resource.order_resource_path,
    aws_api_gateway_resource.restaurant_resource_path
  ]
}

resource "aws_api_gateway_stage" "stage" {
  rest_api_id   = aws_api_gateway_rest_api.api_gateway.id
  deployment_id = aws_api_gateway_deployment.deployment.id
  stage_name    = "v1"

  depends_on = [aws_api_gateway_deployment.deployment]
}