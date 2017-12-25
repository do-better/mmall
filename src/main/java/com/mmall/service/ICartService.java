package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

/**
 * Created by yuanli on 2017/9/15.
 */
public interface ICartService {
    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> list(Integer userId);

    ServerResponse<CartVo> update(Integer userId,Integer productId,Integer count);

    ServerResponse<CartVo> deleteProduct(Integer userId,String productIds);

    ServerResponse<CartVo> selectOrUnSelect (Integer userId,Integer productId,Integer checked);

    ServerResponse<Integer> getCartProductCount(Integer userId);
}