// @MockBean - Used to create mock objects for testing purposes
// @Captor - Used to capture arguments passed to a method
// Functional testing - It evaluates the system's compliance with functional specifications and requirements.
//      Key characteristics:
//          Focuses on functionality from the user's perspective
//          Typically uses black-box testing techniques
//          Verifies that the system does what it's supposed to do
//      Types of functional testing:
//          Unit testing
//          Integration testing
//          System testing
//          Acceptance testing
//          Smoke testing
//          Sanity testing
//          Regression testing
//  Unit Testing:
//      Tests individual components or functions in isolation
//      Mock the external dependencies using Mockito
//      Fast to run and easy to automate
//      Helps identify bugs in specific units of code
//      Typically written by developers alongside the code
//  Integration Testing:
//      Tests how different components work together
//      Tests external dependencies rather then mocking them
//      Verifies interfaces between integrated units/modules
//      Can uncover issues not apparent in unit tests
//      Usually takes longer to run than unit tests
//      Often performed after unit testing
// Best Practices(UTs):
//      1. Write UT fast (Split in 3 parts: Arrange, Act, Assert)
//      2. UTs should mock the external dependencies as they are not dependable(Flaky)
//      3. UTs should not be waiting for I/P and rather it should be hardcoded with expected values
//      4. UTs should test behaviour and not implementation
// Flaky Testing:
//      Whose behavior is non-deterministic,
//      Sometimes passing, sometimes failing.
//      Eg: RANDOM NUMBER GENERATION, Multi-Threading -> Flaky
// Code coverage - It is a metric used in software testing to measure the extent to which the source code of a program is executed when a particular test suite runs
// Storing in DB is strictly prohibited in case of unit testing. Rather save in RAM using HashMap
// Doubles - Test doubles are objects or procedures used in software testing as substitutes for real production components. They're used when the real components are impractical or impossible to incorporate into a unit test. Speed up test execution by avoiding complex dependencies. Allow testing of a unit in isolation.
// Types of Doubles (body doubles like in acting):
//      Mocks: Pre-programmed with expectations, can verify correct usage and fail tests if unexpected calls are made.
//      Stubs: Provide predefined responses to method calls, used for setting up test scenarios.
//      Fake objects: Working implementations with simplified functionality, not suitable for production.
//      Dummy objects: Passed around but never used, often to fill parameter lists.
//      Spies: Record information about how they were called, allowing verification of interactions.
// Mock - Dumb, Brainless
// Stub - Have some brain (character)
// Fake - Smartest amongst these 3, If real object is 100% then Fake is 50%
/* [Stubs vs Mocks]:
    Stubs and mocks are both test doubles used in software testing, particularly in unit testing, to isolate the code being tested. Here's a comparison:

    Stubs:
        Provide predefined responses to method calls
        Used to replace real objects with simplified versions
        Typically don't fail the test; they just return data
        Useful for simulating various scenarios or states

    Mocks:
        - Objects pre-programmed with expectations
        - Verify that specific methods are called with specific arguments
        - Can fail a test if the expected interactions don't occur
        - Used to test behavior and interactions between objects

    Key differences:
        1. Purpose:
            Stubs: Provide controlled indirect inputs
            Mocks: Verify correct interactions and method calls
        2. Verification:
            Stubs: Don't typically verify anything
            Mocks: Verify that certain methods were called correctly
        3. Complexity:
            Stubs: Generally simpler
            Mocks: Can be more complex, with detailed expectations
        4. Usage:
            Stubs: Often used for state verification
            Mocks: Used for behavior verification
        5. Flexibility:
            Stubs: More flexible, can be used in various scenarios
            Mocks: More rigid, designed for specific interaction patterns

    Example scenarios:
        - Use a stub to simulate a database returning specific data
        - Use a mock to ensure a logging service is called with correct parameters
*/

package org.example.productService.controllers;

import org.example.productService.dtos.product.GetProductDto;
import org.example.productService.exception.ProductNotFoundException;
import org.example.productService.models.Product;
import org.example.productService.services.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductControllerTest {
     @Autowired
     private ProductController productController;

     @MockBean(name = "fakeStoreProductService")
     private ProductService productService;

     @Captor
     private ArgumentCaptor<Long> idCaptor;

     // Function Syntax: testName_When_Then or Write the input arguments and expected results in the comments
     @Test
     public void testGetSingleProduct_WhenValidIdIsPassed_ReturnsProductSuccessfully() throws ProductNotFoundException {
         // 1. Arrange (Create objects of the class whose methods you're going to test and Create Mock of external dependencies)
         // Created product object here to check all the functionalities of GetSingleProduct() function
         Product product = new Product();
         product.setPrice(1000D);
         product.setTitle("Iphone 16");
         product.setDescription("Fastest Iphone");
         product.setId(1L);

         when(productService.getProductById(any(Long.class))).thenReturn(product);    // Mocking the getProductById method with a mocked product response

         // 2. Act (Call the method under test and store the result for comparison)
         ResponseEntity<GetProductDto> responseEntity =  productController.getSingleProduct(1L);

         // 3. Assert (Check the expected outcome = received result)
         assertNotNull(responseEntity);
         assertNotNull(responseEntity.getBody());
         assertEquals(1000,responseEntity.getBody().getPrice());
         assertEquals("Iphone 16",responseEntity.getBody().getTitle());

         // Verify the method argument
         verify(productService).getProductById(idCaptor.capture());
         assertEquals(1L, idCaptor.getValue());

         /* If you want to verify other methods as well:
         @Captor
         private ArgumentCaptor<String> stringCaptor;

         verify(productService).getProductById(idCaptor.capture(), stringCaptor.capture());
         assertEquals(1L, idCaptor.getValue());
         assertEquals("test", stringCaptor.getValue());
         */
     }


     // Exception cases handled
     @Test
     public void testGetSingleProduct_WhenInvalidIdIsPassed_ResultsInRuntimeException() {
             RuntimeException ex = assertThrows(RuntimeException.class, () ->  productController.getSingleProduct(0L));

             assertEquals("Something very bad", ex.getMessage());
     }
}

