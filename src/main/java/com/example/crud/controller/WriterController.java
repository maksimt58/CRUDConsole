package com.example.crud.controller;

import com.example.crud.model.Label;
import com.example.crud.model.Post;
import com.example.crud.model.Writer;
import com.example.crud.repository.WriterRepository;
import com.example.crud.repository.impl.JsonIOWriterRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class WriterController{
    private final WriterRepository writerRepository;
    private final PostController postController;

    public WriterController(){
        this.writerRepository = new JsonIOWriterRepositoryImpl();
        this.postController = new PostController();
    }

    public WriterController(WriterRepository writerRepository, PostController postController) {
        this.writerRepository = writerRepository;
        this.postController = postController;
    }

    public List<Writer> onShowAll() {
        return writerRepository.getAll();
    }

    public void onCreate(String firstName, String lastName, List<Long> postsId) {
        List<Post> allPostsList = getAllPosts();
        List<Post> postListForNewWriter = new ArrayList<>();

        //TODO Я знаю, что это не очень здорово, но не смог быстро придумать, как сделать это через Stream API
        for (Long  id : postsId) {
            for (Post post : allPostsList) {
                if(id.equals(post.getId())){
                    postListForNewWriter.add(post);
                }
            }
        }
        Writer writer = new Writer(null, firstName, lastName, postListForNewWriter);
        writerRepository.save(writer);
    }

    public Writer getById(Long id) {
        return writerRepository.getById(id);
    }

    public Writer onUpdate(Long id, String firstName, String lastName) {
        Writer writer = new Writer(id, firstName, lastName);
        return writerRepository.update(writer);
    }

    public boolean onDelete(Long id) {
        return writerRepository.delete(id);
    }

    public List<Post> getAllPosts(){
        return postController.onShowAll();
    }

}
