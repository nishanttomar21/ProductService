package org.example.fakeStore.repositories;

public class CustomQueries {
    public final static String GET_PRODUCTS_WITH_SUBCATEGORY_NAME = "SELECT * FROM products p JOIN category c ON p.category_id = c.id JOIN Subcategory sc ON c.name = sc.name";
}
