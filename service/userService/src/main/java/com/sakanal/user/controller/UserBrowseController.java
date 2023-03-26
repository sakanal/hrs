package com.sakanal.user.controller;

import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;
import com.sakanal.service.entity.user.UserBrowseEntity;
import com.sakanal.user.service.UserBrowseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;



/**
 * 用户浏览表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-22 13:24:10
 */
@RestController
@RequestMapping("user/userbrowse")
public class UserBrowseController {
    @Resource
    private UserBrowseService userBrowseService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("user:userbrowse:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userBrowseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("user:userbrowse:info")
    public R info(@PathVariable("id") Long id){
		UserBrowseEntity userBrowse = userBrowseService.getById(id);

        return R.ok().put("userBrowse", userBrowse);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("user:userbrowse:save")
    public R save(@RequestBody UserBrowseEntity userBrowse){
		userBrowseService.save(userBrowse);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("user:userbrowse:update")
    public R update(@RequestBody UserBrowseEntity userBrowse){
		userBrowseService.updateById(userBrowse);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("user:userbrowse:delete")
    public R delete(@RequestBody Long[] ids) {
        userBrowseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @PostMapping("/browse/{houseBaseId}")
    public R browse(@RequestHeader(value = "token") String token,
                    @PathVariable Long houseBaseId) {
        userBrowseService.browse(token, houseBaseId);
        return R.ok();
    }

    @GetMapping("/myBrowse")
    public R myBrowse(@RequestHeader(value = "token") String token) {
        PageUtils page = userBrowseService.getMyBrowse(token);
        return R.ok().put("page", page);
    }

}
