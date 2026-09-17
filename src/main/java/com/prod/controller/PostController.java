package com.prod.controller;


import com.prod.dto.PostDTO;
import com.prod.entities.PostEntity;
import com.prod.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    @Secured("ROLE_ADMIN")
    public List<PostEntity> getAllPosts(){
        return postService.getAllPost();
    }

    @GetMapping("/{postId}")
    @PreAuthorize("hasAnyRole('CREATOR')")
    public PostDTO getPostById(@PathVariable Long postId){
        return postService.getPostById(postId);
    }


    @PostMapping
    public PostDTO createNewsPost(@RequestBody PostDTO inputPost){
        return postService.creatNewPost(inputPost);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() throws Exception {
        try {
            postService.deleteAllPost();
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }


}
