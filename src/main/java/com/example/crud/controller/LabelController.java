package com.example.crud.controller;

import com.example.crud.model.Label;
import com.example.crud.model.Post;
import com.example.crud.model.Writer;
import com.example.crud.repository.LabelRepository;
import com.example.crud.repository.impl.JsonIOLabelRepositoryImpl;

import java.util.List;

public class LabelController {
    private final LabelRepository labelRepository;

    public LabelController(){
        this.labelRepository = new JsonIOLabelRepositoryImpl();
    }

    public LabelController(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public List<Label> onShowAll() {
        return labelRepository.getAll();
    }

    public void onCreate(String nameLabel) {
        Label label = new Label(null, nameLabel);
        labelRepository.save(label);
    }

    public Label getById(Long id) {
        return labelRepository.getById(id);
    }

    public Label onUpdate(Long id, String nameLabel) {
        Label label = new Label(id, nameLabel);
        return labelRepository.update(label);
    }

    public boolean onDelete(Long id) {
        return labelRepository.delete(id);
    }

}
