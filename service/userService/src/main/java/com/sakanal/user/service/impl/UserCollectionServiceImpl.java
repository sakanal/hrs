package com.sakanal.user.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.constant.PageConstant;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;
import com.sakanal.base.utils.R;
import com.sakanal.service.dto.PublishInfoListDTO;
import com.sakanal.service.entity.user.UserCollectionEntity;
import com.sakanal.service.utils.JwtUtils;
import com.sakanal.service.vo.PublishBaseInfoVO;
import com.sakanal.user.dao.UserCollectionDao;
import com.sakanal.user.feign.HouseFeignClient;
import com.sakanal.user.service.UserCollectionService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;


@Service("userCollectionService")
public class UserCollectionServiceImpl extends ServiceImpl<UserCollectionDao, UserCollectionEntity> implements UserCollectionService {

    @Resource
    private HouseFeignClient houseFeignClient;
    @Resource
    private ThreadPoolExecutor executor;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserCollectionEntity> page = this.page(new Query<UserCollectionEntity>().getPage(params), new QueryWrapper<>());

        return new PageUtils(page);
    }

    @Override
    public PageUtils getMyCollection(String token, Integer current) {
        String userId = JwtUtils.getUserIdByToken(token);
        if (StringUtils.hasText(userId)) {
            CompletableFuture<List<PublishBaseInfoVO>> dataCompletableFuture = CompletableFuture.supplyAsync(() -> this.list(new LambdaQueryWrapper<UserCollectionEntity>()
                    .select(UserCollectionEntity::getBaseInfoId)
                    .eq(UserCollectionEntity::getUserId, userId)
                    .orderByDesc(UserCollectionEntity::getModifyTime)
                    .last("limit " + ((current - 1) * PageConstant.LIMIT) + "," + PageConstant.LIMIT)
            ), executor).thenApplyAsync((List<UserCollectionEntity> list) -> {
                List<Long> baseInfoIdList = list.stream().map(UserCollectionEntity::getBaseInfoId).collect(Collectors.toList());
                List<PublishBaseInfoVO> result = new ArrayList<>();
                if (baseInfoIdList.size() > 0) {
                    PublishInfoListDTO publishInfoListDTO = new PublishInfoListDTO();
                    publishInfoListDTO.setBaseInfoIdList(baseInfoIdList);
                    publishInfoListDTO.setCurrent(current);
                    R r = houseFeignClient.getBrowsePublishInfoList(publishInfoListDTO);
                    List<PublishBaseInfoVO> publishBaseInfoVOList = r.getData("data", new TypeReference<List<PublishBaseInfoVO>>() {
                    });
                    // 重构结果，按照时间倒序排列
                    for (UserCollectionEntity userCollectionEntity : list) {
                        Long baseInfoId = userCollectionEntity.getBaseInfoId();
                        publishBaseInfoVOList.forEach(publishBaseInfoVO -> {
                            if (Objects.equals(baseInfoId, publishBaseInfoVO.getBaseInfoId())) {
                                result.add(publishBaseInfoVO);
                            }
                        });
                    }
                }
                return result;
            }, executor);
            CompletableFuture<Long> totalCompletableFuture = CompletableFuture.supplyAsync(() -> this.count(new LambdaQueryWrapper<UserCollectionEntity>()
                    .eq(UserCollectionEntity::getUserId, userId)), executor);
            try {
                return new PageUtils(dataCompletableFuture.get(), Math.toIntExact(totalCompletableFuture.get()), PageConstant.LIMIT, current);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean isMyCollection(String token, Long houseBaseId) {
        String userIdByToken = JwtUtils.getUserIdByToken(token);
        if (StringUtils.hasText(userIdByToken)) {
            return this.count(new LambdaQueryWrapper<UserCollectionEntity>()
                    .eq(UserCollectionEntity::getBaseInfoId, houseBaseId)
                    .eq(UserCollectionEntity::getUserId, userIdByToken)
            ) == 1;
        }
        return false;
    }

    @Override
    public boolean addCollection(String token, Long houseBaseId) {
        String userIdByToken = JwtUtils.getUserIdByToken(token);
        if (StringUtils.hasText(userIdByToken)) {
            if (!this.isMyCollection(token, houseBaseId)) {
                UserCollectionEntity userCollectionEntity = new UserCollectionEntity();
                userCollectionEntity.setUserId(Long.valueOf(userIdByToken));
                userCollectionEntity.setBaseInfoId(houseBaseId);
                return this.save(userCollectionEntity);
            }
        }
        return false;
    }

    @Override
    public boolean removeCollection(String token, Long houseBaseId) {
        String userIdByToken = JwtUtils.getUserIdByToken(token);
        if (StringUtils.hasText(userIdByToken)) {
            if (this.isMyCollection(token, houseBaseId)) {
                return this.remove(new LambdaQueryWrapper<UserCollectionEntity>()
                        .eq(UserCollectionEntity::getBaseInfoId, houseBaseId)
                        .eq(UserCollectionEntity::getUserId, userIdByToken));
            }
        }
        return false;
    }

}
