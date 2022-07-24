package com.example.crud.controller;

import com.example.crud.model.Label;
import com.example.crud.model.Post;
import com.example.crud.model.PostStatus;
import com.example.crud.repository.PostRepository;
import com.example.crud.repository.impl.JsonIOPostRepositoryImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;


public class PostController {
    private final PostRepository postRepository;
    private final LabelController labelController;

    public PostController() {
        this.postRepository = new JsonIOPostRepositoryImpl();
        this.labelController = new LabelController();
    }

    public PostController(PostRepository postRepository, LabelController labelController) {
        this.postRepository = postRepository;
        this.labelController = labelController;
    }

    public List<Post> onShowAll() {
        return postRepository.getAll();
    }

    public void onCreate(String content, PostStatus postStatus, List<Long> labelsId) {
        List<Label> allLabelList = getAllLabels();
        List<Label> labelList = new ArrayList<>();

        //TODO Я знаю, что это не очень здорово, но не смог быстро придумать, как сделать это через Stream API
        for (Long  s : labelsId) {
            for (Label label : allLabelList) {
                if(s.equals(label.getId())){
                    labelList.add(label);
                }
            }
        }

        Post post = new Post(null, content, postStatus,labelList);
        post.setCreated(LocalDate.now());
        post.setUpdated(LocalDate.now());
        postRepository.save(post);
    }

    public Post getById(Long id) {
        return postRepository.getById(id);
    }

    public Post onUpdate(Long id, String content, PostStatus postStatus) {
        Post post = new Post(id, content, postStatus);
        return postRepository.update(post);
    }

    public boolean onDelete(Long id) {
        return postRepository.delete(id);
    }

    public List<Label> getAllLabels() {
        return labelController.onShowAll();
    }
}
