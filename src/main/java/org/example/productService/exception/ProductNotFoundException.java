// Custom Exception Handling, very useful in production code to make sure that the user gets a proper error message in case of an exception. It is done using @ControllerAdvice and @ExceptionHandler annotations. It allows you to handle exceptions globally or for specific controllers. Service throws the exception and Controller catches it. @ExceptionHandler methods can be placed within a controller class for specific handling or in a @ControllerAdvice class for global handling.

package org.example.productService.exception;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
