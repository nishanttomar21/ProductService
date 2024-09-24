package org.example.productService.controllers;

//@WebMvcTest(ProductController.class)
//public class ProductControllerMvcTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private IProductService productService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//    //object <-> json <-> string
//
//
//    @Test
//    public void TestGetAllProducts_RunsSuccessfully() throws Exception {
//        Product product = new Product();
//        product.setId(1L);
//        product.setTitle("Iphone 16");
//        List<Product> productList = new ArrayList<>();
//        productList.add(product);
//        when(productService.getAllProducts()).thenReturn(productList);
//
//       mockMvc.perform(get("/products"))
//               .andExpect(status().isOk())
//               .andExpect(content().string(objectMapper.writeValueAsString(productList)));
//    }
//
//
//    @Test
//    public void TestCreateProduct_RunsSuccessfully() throws Exception {
//        ProductDto productDto = new ProductDto();
//        productDto.setId(2L);
//        productDto.setTitle("MacBook Pro");
//
//        Product product = new Product();
//        product.setId(2L);
//        product.setTitle("MacBook Pro");
//
//        when(productService.createProduct(any(Product.class))).thenReturn(product);
//
//
//        mockMvc.perform(post("/products")
//                        .content(objectMapper.writeValueAsString(productDto)).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().string(objectMapper.writeValueAsString(productDto)))
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$.title").value("MacBook Pro"));
//    }
//}