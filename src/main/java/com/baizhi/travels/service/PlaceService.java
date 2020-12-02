package com.baizhi.travels.service;

import com.baizhi.travels.entity.Place;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlaceService {
    List<Place> findByProvinceIdPage( Integer page, Integer rows , String provinceId );

    Integer findByProvinceIdCounts( String provinceId );

    void save(Place place);

    void delete(String id , String proId );

    Place findOnePlace(String id);

    void update(Place place);
}
