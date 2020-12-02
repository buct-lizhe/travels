package com.baizhi.travels.controller;

import com.baizhi.travels.entity.Place;
import com.baizhi.travels.entity.Result;
import com.baizhi.travels.service.PlaceService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("place")
public class PlaceController {

    @Autowired
    PlaceService placeService;

    @Value("${upload.dir}")
    private String realpath;

    @PostMapping("save")
    public Result save(MultipartFile pic, Place place) throws IOException {
        Result result = new Result();
        try {
            System.out.println(pic.getOriginalFilename());
            System.out.println(place);

            //文件上传
            String extension = FilenameUtils.getExtension(pic.getOriginalFilename());
            String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + extension;
            place.setPicpath(Base64Utils.encodeToString(pic.getBytes()));
            pic.transferTo(new File(realpath, newFileName));


            //保存place对象
            placeService.save(place);
            result.setMsg("保存景点信息成功！！！");
        }catch ( Exception e){
            result.setState(false).setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 根据省份id查询景点的方法
     */
    @GetMapping("findAllPlace")
    public Map<String, Object> findAllPlace(Integer page, Integer rows, String provinceId) {
        HashMap<String, Object> result = new HashMap<>();
        page = page == null ? 1 : page;
        rows = rows == null ? 3 : rows;
        //景点集合
        List<Place> places = placeService.findByProvinceIdPage(page, rows, provinceId);
        //处理分页
        Integer counts = placeService.findByProvinceIdCounts(provinceId);
        //总页数
        Integer totalPage = counts % rows == 0 ? counts / rows : counts / rows + 1;
        result.put("places", places);
        result.put("page", page);
        result.put("counts", counts);
        result.put("totalPage", totalPage);
        return result;
    }

    /**
     * 根据景点id删除对应的景点
     */
    @GetMapping("delete")
    public String delete( String id , String proId ){
        System.out.println( id +' '+proId );
        placeService.delete( id , proId );

        return "ok";
    }

    @GetMapping("findOnePlace")
    public Place delete( String id ){
        System.out.println( id );
        Place place = placeService.findOnePlace( id );
        System.out.println( place );
        return place;
    }

    /**
     * 更新景点信息
     */
    @PostMapping("updatePlace")
    public Result updatePlace(MultipartFile pic, Place place) throws IOException {
        Result result = new Result();

        try {
            //对接收的文件做base64
            String picpath = Base64Utils.encodeToString(pic.getBytes());
            place.setPicpath( picpath );
            //处理文件上传
            String extension = FilenameUtils.getExtension(pic.getOriginalFilename());
            String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format( new Date() ) + extension;
            pic.transferTo( new File( realpath , newFileName ));

            //修改景点信息
            placeService.update( place );

            result.setMsg("修改景点信息成功！！！");
        }catch (Exception e ){
            result.setState(false).setMsg(e.getMessage());
        }

        return result;
    }

}
