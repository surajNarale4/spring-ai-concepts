package com.prod.services;

import com.prod.dto.PostDTO;
import com.prod.entities.PostEntity;
import com.prod.exception.ResourceNotFoundException;
import com.prod.repositories.PostRepository;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    private final static Logger  logger= LoggerFactory.getLogger(PostServiceImpl.class);
    @Override
    public List<PostEntity> getAllPost() {

        logger.info("This is Info ");
        logger.debug("This is Debug");
        logger.error("This is Error");
        logger.trace("This is Trace ");
        return postRepository.findAll();
    }

    @Override
    public PostDTO creatNewPost(PostDTO inputPost) {
        PostEntity post=modelMapper.map(inputPost,PostEntity.class);
        return modelMapper.map(postRepository.save(post),PostDTO.class);
    }

    @Override
    public void deleteAllPost() {

        postRepository.deleteAll();
    }

    @Override
    public PostDTO getPostById(Long postId) {
        logger.debug("finding post by post id {}",postId);
        PostDTO postDTO= modelMapper.map(postRepository.findById(postId)
                .orElseThrow(
                        ()->{
                            logger.error("no post found with id {}", postId);
                            return new ResourceNotFoundException("post not found with id "+postId);
                        }
                ),
                PostDTO.class);
        logger.debug("post found with id {}", postId);
        return postDTO;
    }
}
