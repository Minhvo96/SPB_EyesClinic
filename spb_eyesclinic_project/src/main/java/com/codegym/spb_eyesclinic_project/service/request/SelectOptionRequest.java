package com.codegym.spb_eyesclinic_project.service.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
// class sinh ra để mapper id của 1 field nào đó mà data type dạng object
// ví dụ Film có filed type của  director là Person
// để mapper được phải xài thằng này vì
//payload
//{
//  "publishDate": "123",
//  "director": {
//      "id": "1"
//  },
//  "categories": ["1", "2"],
//  "actors": ["1", "2", "3"]
//Entity Film
// {
////  "publishDate": "123",
////  "director": {
////      "id": 1,
//          "name": null,
//
////  },
public class SelectOptionRequest {
    private String id;
}
