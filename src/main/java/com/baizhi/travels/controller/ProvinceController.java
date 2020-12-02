package com.baizhi.travels.controller;


import com.baizhi.travels.entity.Province;
import com.baizhi.travels.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("province")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @GetMapping("findByPage")
    public Map<String , Object > findByPage( Integer page , Integer rows ){
        page = page==null? 1 : page ;
        rows = rows==null? 4 : rows;
        HashMap<String, Object> map = new HashMap<>();

        //分页处理
        List<Province> provinces = provinceService.findByPage( page , rows );
        //计算总页数
        Integer totals = provinceService.findTotals();
        Integer totalPage = totals%rows==0?totals/rows:totals/rows+1;
        map.put("province" , provinces);
        map.put("totals" , totals);
        map.put("totalPage" , totalPage);
        map.put("page" , page );
        return map;
    }

    //添加省份
    @PostMapping("inserProvince")
    public String inserProvince( @RequestBody Province province ) {
        province.setPlacecounts( 0 );
        System.out.println(province+"     11111111111111111");
        provinceService.insertProvince( province );
        return "ok";
    }

    @GetMapping("deleteProvince")
    public String deleteProvince( String id ){
        //System.out.println();
        provinceService.deleteProvince( id );
        return "ok";
    }

    @GetMapping("findOneProvince")
    public Map<String,Province> findOneProvince( String id ){
        Province province = provinceService.findOneProvince( id );
        HashMap<String , Province > map = new HashMap<>();
        map.put("province",province);
        return map;
    }

    @PostMapping("updateProvince")
    public Map<String,Province> updateProvince( @RequestBody Province province ){
        System.out.println("传入的： "+province);
        provinceService.updateProvince( province );
        Province updatedProvince = provinceService.findOneProvince(province.getId());
        System.out.println("修改后的："+updatedProvince);
        HashMap<String , Province > map = new HashMap<>();
        map.put("province",updatedProvince);
        return map;
    }
}
