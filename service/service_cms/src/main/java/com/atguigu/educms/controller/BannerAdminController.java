package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-12-11
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin //跨域
public class BannerAdminController {
    @Autowired
    private CrmBannerService crmBannerService;
    //1.分页查询banner
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page,@PathVariable long limit){
        //baomidou page
        Page<CrmBanner> pageBanners = new Page<>(page,limit);
        crmBannerService.page(pageBanners,null);
        return R.ok().data("items",pageBanners.getRecords()).data("total",pageBanners.getTotal());
    }
    //2.添加banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return R.ok();
    }
    //3.修改banner
    @PutMapping("update")
    public R update(@RequestBody CrmBanner crmBanner){
        crmBannerService.updateById(crmBanner);
        return R.ok();
    }
    //4.删除banner
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id){
        crmBannerService.removeById(id);
        return  R.ok();
    }
    //5.获取banner
    @GetMapping("get/{id}")
    public R get(@PathVariable String id){
        crmBannerService.getById(id);
        return R.ok();
    }

}

