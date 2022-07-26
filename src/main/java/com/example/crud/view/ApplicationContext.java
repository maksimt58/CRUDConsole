package com.example.crud.view;

import com.example.crud.controller.LabelController;
import com.example.crud.controller.PostController;
import com.example.crud.controller.WriterController;
import com.example.crud.repository.LabelRepository;
import com.example.crud.repository.PostRepository;
import com.example.crud.repository.WriterRepository;
import com.example.crud.repository.impl.JsonIOLabelRepositoryImpl;
import com.example.crud.repository.impl.JsonIOPostRepositoryImpl;
import com.example.crud.repository.impl.JsonIOWriterRepositoryImpl;

public class ApplicationContext {
    public void loadContext() {

        LabelRepository labelRepository = new JsonIOLabelRepositoryImpl();
        LabelController labelController = new LabelController(labelRepository);
        LabelView labelView = new LabelView(labelController);

        PostRepository postRepository = new JsonIOPostRepositoryImpl();
        PostController postController = new PostController(postRepository, labelController);
        PostView postView = new PostView(postController);

        WriterRepository writerRepository = new JsonIOWriterRepositoryImpl();
        WriterController writerController = new WriterController(writerRepository, postController);
        WriterView writerView = new WriterView(writerController);

        BaseView baseMenu = new BaseView(writerView, postView, labelView);
        baseMenu.show();
    }
}
