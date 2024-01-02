package com.codegym.spb_eyesclinic_project.domain.dto.eyeCategoryDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EyeCategoryRequest {

    private String nameCategory;

    private String price;

    private String description;

}
