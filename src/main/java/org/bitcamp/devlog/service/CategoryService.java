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
    public List<Category> findAllByPostId(Post post){

        List<Category> categories =categoryMapper.findAllByPostId(post.getPostId());
        return categories;
    };
    public List<Category> findByPostId(Post post){
        List<Category> categories=categoryMapper.findAllByPostId(post.getPostId());

        return categories;
    }
}
