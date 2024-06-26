package org.bitcamp.devlog.service;

import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Category;
import org.bitcamp.devlog.dto.Post;
import org.bitcamp.devlog.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    final CategoryMapper categoryMapper;

    public void save (Category category){
        categoryMapper.save(category);
    }
    public void update (Category category){
        categoryMapper.update(category);
    }
    public void delete (Category category){

        categoryMapper.delete(category.getCategoryId());
    }
    public List<Category> findAllByPostId(Long categoryId){
        return categoryMapper.findAllByPostId(categoryId);
    };
    public List<Category> findByPostId(Post post){
        List<Category> categories=categoryMapper.findAllByPostId(post.getPostId());
        return categories;
    }

    public List<Category> findAllByAccountId(Long accountId){
        return categoryMapper.findAllByAccountId(accountId);
    };

    public Long findCategoryIdByCategoryType(String categoryType){
        return categoryMapper.findCategoryIdByCategoryType(categoryType);
    }

    public String findCategoryTypeByCategoryId(Long categoryId) {
        return categoryMapper.findCategoryTypeByCategoryId(categoryId);
    }

    public Category findByCategoryId(Long categoryId) {
        return categoryMapper.findByCategoryId(categoryId);
    }

    public Category findByCategoryType(String categoryType) {
        return categoryMapper.findByCategoryType(categoryType);
    }
}