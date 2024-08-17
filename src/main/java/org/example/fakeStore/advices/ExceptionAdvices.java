// @ControllerAdvices - Allows you to define global exception handlers, model attributes, and data binding logic that apply to all or selected controllers in your application.
// DispatcherServlet <== ControllerAdvice(Additional Check/Manager) <== Controller
// By default, @ControllerAdvice applies to all controllers, but you can limit its scope by specifying the base packages, annotations, or controller classes it should apply to.
// @ExceptionHandler methods can be placed within a controller class for specific handling or in a @ControllerAdvice class for global handling.
// @ExceptionHandler methods can be placed within a controller class for specific handling or in a @ControllerAdvice class for global handling.

package org.example.fakeStore.advices;

import org.example.fakeStore.dtos.ErrorResponseDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ExceptionAdvices {

    @ExceptionHandler(RuntimeException.class)
    public ErrorResponseDto handleRuntimeException(RuntimeException e) {
        ErrorResponseDto dto = new ErrorResponseDto();
        dto.setStatus("ERROR");
        dto.setMessage(e.getMessage());
        return dto;
    }

    @ExceptionHandler(Exception.class)
    public String handleException() {
        return "something went wrong";
    }
}
