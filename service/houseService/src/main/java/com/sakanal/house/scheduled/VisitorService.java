package com.sakanal.house.scheduled;

import com.sakanal.house.service.HousePromotionService;
import com.sakanal.service.entity.house.HousePromotionEntity;
import com.sakanal.service.properties.MyCommonRedisProperties;
import com.sakanal.service.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class VisitorService {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private MyCommonRedisProperties redisProperties;
    @Resource
    private HousePromotionService promotionService;

    /**
     * 将redis中的数据保存到数据库中，每小时进行一次
     */
    public void updateToDataSource(){
        Set<String> keys = redisTemplate.keys(redisProperties.getVisitorPrefix() + "*");
        if (keys!=null && keys.size()>0){
            List<Object> list = redisTemplate.opsForValue().multiGet(keys);
            if (list!=null && list.size()>0){
                List<HousePromotionEntity> promotionList = new ArrayList<>();
                AtomicInteger i = new AtomicInteger();
                keys.forEach(key->{
                    HousePromotionEntity promotion = new HousePromotionEntity();
                    promotion.setBaseInfoId(Long.valueOf(key));
                    promotion.setVisitorNumber((Integer) list.get(i.getAndIncrement()));
                    promotionList.add(promotion);
                });
                if (promotionList.size()>0){
                    promotionService.saveOrUpdateBatch(promotionList);
                }
            }
        }
    }
}
