package com.bikkadit.electronicstore.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;

    @NotBlank()
    @Size(min = 4,max = 50,message = "minimum 4 characters must be required in title")
    private String title;

    @NotBlank()
    @Size(min = 10,max = 5000,message = "minimum 10 characters must be required in description")
    private String description;


    private String coverImage;
}
