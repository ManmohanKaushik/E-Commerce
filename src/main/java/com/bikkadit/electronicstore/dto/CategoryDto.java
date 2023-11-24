package com.bikkadit.electronicstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private String categoryId;

    @NotBlank()
    @Min(value = 4,message = "minimum 4 characters must be required in title")
    private String title;

    @NotBlank()
    @Min(value = 10,message = "minimum 10 characters must be required in description")
    private String description;


    private String coverImage;
}
