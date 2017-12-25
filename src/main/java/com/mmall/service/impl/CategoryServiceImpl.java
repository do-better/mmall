package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by yuanli on 2017/9/12.
 */
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    public ServerResponse<String> addCategory(String categoryName, Integer parentId) {
        if(parentId == null || StringUtils.isBlank((categoryName))) {
            return ServerResponse.createByErrorMessage("添加品类参数错误");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);//表示这个分类是可用的

        int rowCount = categoryMapper.insert(category);
        if(rowCount > 0) {
            return ServerResponse.createBySuccessMessage("添加商品成功");
        }
        return ServerResponse.createByErrorMessage("添加商品失败");
    }

    public ServerResponse<String> setCategory(Integer categoryId, String categoryName) {
        if(categoryId == null || StringUtils.isBlank((categoryName))) {
            return ServerResponse.createByErrorMessage("更新品类名字参数错误");
        }

        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if(rowCount > 0) {
            return ServerResponse.createBySuccessMessage("更新品类名字成功");
        }
        return ServerResponse.createByErrorMessage("更新品类名字失败");
    }

    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId) {
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if(categoryList == null || CollectionUtils.isEmpty(categoryList)) {
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
      }

      //秭归查询所有子节点及其子节点Id
    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId) {
        List<Integer> categoryIdList = Lists.newArrayList();
        if(categoryId != null) {
            Set<Category> categorySet = Sets.newHashSet();
            findChildCategory(categorySet, categoryId);
            for(Category categoryItem : categorySet) {
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }

      //递归算法，算出子节点,set集合里面的元素不准重复，重写equal和hashcode，即id不同
    private void findChildCategory(Set<Category> categorySet, Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category != null) {
            categorySet.add(category);
        }
            //查找子节点。mybatis返回的集合处理过不会是null，最多为empty
            List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
            for (Category categoryItem : categoryList) {
                if (categoryItem != null) {
                    findChildCategory(categorySet, categoryItem.getId());
                }
            }

    }
}
