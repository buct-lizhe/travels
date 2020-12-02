package com.baizhi.travels.service;

import com.baizhi.travels.dao.PlaceDAO;
import com.baizhi.travels.entity.Place;
import com.baizhi.travels.entity.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlaceServiceImpl implements PlaceService{

    @Autowired
    private PlaceDAO placeDAO;

    @Autowired
    private ProvinceService provinceService;

    @Override
    public List<Place> findByProvinceIdPage(Integer page, Integer rows, String provinceId) {

        int start = (page-1)*rows;
        return placeDAO.findByProvinceIdPage( start , rows , provinceId );
    }

    @Override
    public Integer findByProvinceIdCounts(String provinceId) {

        return placeDAO.findByProvinceIdCounts( provinceId );
    }

    @Override
    public void save(Place place) {
        placeDAO.save( place );

        //将景点所关联的省份景点数要加1
        Province province = provinceService.findOneProvince(place.getProvinceid());
        System.out.println(province.getPlacecounts());
        province.setPlacecounts( province.getPlacecounts()+1 );
        provinceService.updateProvince( province );
        System.out.println(province.getPlacecounts());

    }

    @Override
    public void delete(String id , String proId ) {
        placeDAO.delete( id );
        //将景点所关联的省份景点数要减1
        Province province = provinceService.findOneProvince( proId  );
        System.out.println(province.getPlacecounts());
        province.setPlacecounts( province.getPlacecounts()-1 );
        provinceService.updateProvince( province );
        System.out.println(province.getPlacecounts());
    }

    @Override
    public Place findOnePlace(String id) {
        return placeDAO.findOnePlace( id );
    }

    @Override
    public void update(Place place) {
        placeDAO.update( place );
    }
}
