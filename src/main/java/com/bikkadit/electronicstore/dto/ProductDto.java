package com.bikkadit.electronicstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto
{
    private String productId;

    @NotBlank()
    @Size(min = 4, max = 50,message = "minimum 4 characters must be required in title")
    private String title;

    @NotBlank()
    @Size(min = 10, max = 5000,message = "minimum 4 characters must be required in description")
    private String description;

    private Integer price;

    private Integer quantity;

    private Date addedDate;

    private boolean live;

    private boolean stock;
}
