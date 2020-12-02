package com.baizhi.travels.dao;

import com.baizhi.travels.entity.Province;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProvinceDAO extends  BaseDAO<Province , String>{

    //通过id查找到一个省份
    Province findOneProvince(String id);

}
