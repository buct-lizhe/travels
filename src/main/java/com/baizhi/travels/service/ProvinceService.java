package com.baizhi.travels.service;

import com.baizhi.travels.entity.Province;

import java.util.List;

public interface ProvinceService {

    //参数1 当前页    // 参数2 每页显示的条数
    List<Province> findByPage( Integer page , Integer rows );

    //查询总条数
    Integer findTotals();

    //添加省份
    void insertProvince(Province province);

    void deleteProvince(String id);

    Province findOneProvince(String id);

    //修改省份
    void updateProvince(Province province);
}
