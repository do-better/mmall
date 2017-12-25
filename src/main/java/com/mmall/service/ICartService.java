package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

/**
 * Created by yuanli on 2017/9/15.
 */
public interface ICarService {
    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);
}
