package com.bikkadit.electronicstore.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public  class PegeableResponse<T> {

    private List<T> content;

    private  int pageNumber;

    private int pageSize;

    private  int totalPage;

    private long totalElements;

    private boolean lastPage;
}