package com.baizhi.travels.service;

import com.baizhi.travels.dao.ProvinceDAO;
import com.baizhi.travels.entity.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private ProvinceDAO provinceDAO;

    @Override
    public List<Province> findByPage(Integer page, Integer rows) {

        Integer start = (page-1)*rows; //当前页减一再乘以每页条数得到起始页
        return provinceDAO.findByPage( start , rows );
    }

    @Override
    public Integer findTotals() {
        return provinceDAO.findTotals();
    }

    @Override
    public void insertProvince(Province province) {
        provinceDAO.save( province );
    }

    @Override
    public void deleteProvince(String id) {
        provinceDAO.delete( id );
    }

    @Override
    public Province findOneProvince(String id) {
        return provinceDAO.findOneProvince( id );
    }

    @Override
    public void updateProvince(Province province) {
        provinceDAO.update( province );
    }
}
