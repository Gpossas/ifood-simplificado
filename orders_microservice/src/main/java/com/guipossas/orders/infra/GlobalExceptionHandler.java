package com.guipossas.orders.infra;

import com.guipossas.orders.exceptions.OrderItemNotFound;
import com.guipossas.orders.exceptions.OrderNotFound;
import com.guipossas.orders.exceptions.OrderWithEmptyItems;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleInvalidMethodArgument(MethodArgumentNotValidException exception)
    {
        List<HashMap<String, String>> errorMessages = new ArrayList<>();
        exception.getFieldErrors().forEach(error -> {
            HashMap<String, String> error_map = new HashMap<>();
            error_map.put("message", error.getDefaultMessage());
            errorMessages.add(error_map);
        });

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail("Parâmetros com valores inválidos");
        problemDetail.setTitle("Parâmetro inválido");
        problemDetail.setProperty("errors", errorMessages);

        return problemDetail;
    }

    @ExceptionHandler(OrderItemNotFound.class)
    public ProblemDetail handleOrderItemNotFoundException(OrderItemNotFound exception)
    {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problemDetail.setTitle("Item não encontrado");
        return problemDetail;
    }

    @ExceptionHandler(OrderNotFound.class)
    public ProblemDetail handleOrderNotFoundException(OrderNotFound exception)
    {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problemDetail.setTitle("Pedido não encontrado");
        return problemDetail;
    }

    @ExceptionHandler(OrderWithEmptyItems.class)
    public ProblemDetail handleOrderWithEmptyItemsException(OrderWithEmptyItems exception)
    {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage());
        problemDetail.setTitle("Pedido não possui itens");
        return problemDetail;
    }
}
