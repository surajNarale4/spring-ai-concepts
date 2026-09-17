package com.prod.services;

import com.prod.dto.PostDTO;
import com.prod.entities.PostEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PostService {

    List<PostEntity> getAllPost();

    PostDTO creatNewPost(PostDTO post);

    void deleteAllPost();

    PostDTO getPostById(Long postId);
}
