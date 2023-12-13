package com.bikkadit.electronicstore.helper;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {
public static <U,V> PegeableResponse<V> pegeableResponse(Page<U> page,Class<V> type){
    List<U> entity = page.getContent();
    List<V> listDto = entity.stream().map(object -> new ModelMapper()
            .map(object, type)).collect(Collectors.toList());
    PegeableResponse<V> response=new PegeableResponse<>();
    response.setContent(listDto);
    response.setLastPage(page.isLast());
    response.setPageNumber(page.getNumber());
    response.setTotalPage(page.getTotalPages());
    response.setPageSize(page.getSize());
    response.setTotalElements(page.getTotalElements());


    return response;
    }


}
