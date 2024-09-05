/*
    filters = [
        {
            "key": "brand",
            "values": ["apple", "samsung"] // OR operation
        },  // AND operation
        {
            "key": "ram",
            "values": ["16GB"]
        },
        {
            "key": "os",
            "values": ["android", "ios"]
        },
        {
            "key": "lowPrice",
            "values": ["1000"]
        },
        {
            "key": "highPrice",
            "values": ["2000"]
        }
    ]

    Easy to search like this based on filters: (brand == "apple" || brand == "samsung") AND (ram == "16GB") AND (os == "android" || os == "ios")
*/

package org.example.productService.dtos.search;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilterDto {
    private String key;
    private List<String> values;
}
