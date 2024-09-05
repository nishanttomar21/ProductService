/*
    Searching can be called using 2 HTTP methods:
        1. GET - [URL: size limit, Body: not as per standard]
            Pros:
                - Easy to share URL
                - Cache search results at load balancer for faster query searching next time.
            Cons:
                - Length of the URL is limited to certain old browsers (2048 characters), nowadays new browsers support up to 2M characters in URL.
                - As per HTTP standard (REST), GET method req shouldn't have a request body (can send unlimited characters).
        2. POST - [URL: not as per standard, Body: unlimited, no standard violation]
            Pros:
                - No limit on the size of the request body.
            Cons:
                - No caching possible at load balancer
                - Not easy to share URL (as data in request body)

        In Industry, we use both the methods depending on the use case for different searches:
            1. POST - (Complex Search, Large filters)
                - When we have a complex search
                - When we have a lot of parameters
                - When we have a lot of data
                - When we have a lot of filters
                - When we have a lot of sorting
                - When we have a lot of pagination
                - When we have a lot of data validation
                - When we have a lot of business logic
                - When we have a lot of security
                - When we have a lot of performance
                - When we have a lot of scalability
                - When we have a lot of reliability
                - When we have a lot of availability
                - When we have a lot of maintainability
                - When we have a lot of testability
                - When we have a lot of documentation
                - When we have a lot of monitoring
            2. GET - (Simple Search, Less filters)
                - When we have a simple search
                - When we have a few parameters
                - etc...

        In this case we will use GET method because we have a simple search and few parameters.
 */

package org.example.productService.controllers;

import org.example.productService.dtos.product.GetProductDto;
import org.example.productService.dtos.search.SearchRequestDto;
import org.example.productService.dtos.search.SearchResponseDto;
import org.example.productService.exception.ProductNotFoundException;
import org.example.productService.models.Product;
import org.example.productService.services.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;  // Dependency Injection

    public SearchController(SearchService searchService) {  // Constructor Injection
        this.searchService = searchService;
    }

    @PostMapping("/")   // As per industry, use POST method for complex search
    // SearchRequestDto:
    //      String query
    //      List<FilterDto> filters // complex search
    //      SortingCriteria sortingCriteria
    //      int pageNumber
    //      int pageSize
    public SearchResponseDto complexSearch(@RequestBody SearchRequestDto searchRequest) throws ProductNotFoundException {
        SearchResponseDto response = new SearchResponseDto();

        // Call the search service to get the results
        Page<Product> productsPage = searchService.search(
                searchRequest.getQuery(),
                searchRequest.getFilters(),
                searchRequest.getSortingCriteria(),
                searchRequest.getPageNumber(),
                searchRequest.getPageSize()
        );

        // Set the results in the response
        Page<GetProductDto> productDtoPage = productsPage.map(GetProductDto::fromProduct);

        // [Pagination Information]
        // int totalPages = productDtoPage.getTotalPages();
        // long totalElements = productDtoPage.getTotalElements();
        // int currentPageNumber = productDtoPage.getNumber();
        // int pageSize = productDtoPage.getSize();
        // List<GetProductDto> content = productDtoPage.getContent();
        // boolean hasNext = productDtoPage.hasNext();
        // boolean hasPrevious = productDtoPage.hasPrevious();

        response.setProductsPage(productDtoPage);

        return response;
    }

    @GetMapping("/byCategory")  // As per industry, use GET method for simple search
    public SearchResponseDto simpleSearch(@RequestParam("query") String query,
                                          @RequestParam("category") Long categoryId,    // simple search
                                          @RequestParam("sortingAttribute") String sortingAttribute,
                                          @RequestParam("pageNumber") int pageNumber,
                                          @RequestParam("pageSize") int pageSize)
    {
        return new SearchResponseDto();
    }
}
