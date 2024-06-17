package org.bitcamp.devlog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.bitcamp.devlog.dto.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
    void insertCategory(Category dto);
    void updateCategory(Category dto);
    List<Category> getCategoryList();
    void deleteCategory (int categoryId);

}
