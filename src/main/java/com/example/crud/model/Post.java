package com.example.crud.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Post implements Model {
    private Long id;
    private String content;
    private LocalDate created;
    private LocalDate updated;
    private List<Label> labels = new ArrayList<>();
    private PostStatus postStatus;

    public Post(Long id, String content, PostStatus postStatus) {
        this.id = id;
        this.content = content;
        this.postStatus = postStatus;
    }

    public Post(String content, List<Label> labels, PostStatus postStatus) {
        this.content = content;
        this.labels = labels;
        this.postStatus = postStatus;
    }

    public Post(Long id, String content, PostStatus postStatus, List<Label> labels) {
        this.id = id;
        this.content = content;
        this.labels = labels;
        this.postStatus = postStatus;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id.equals(post.id) && content.equals(post.content) && created.equals(post.created) && Objects.equals(updated, post.updated) && Objects.equals(labels, post.labels) && postStatus == post.postStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, created, updated, labels, postStatus);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", labels=" + labels +
                ", postStatus=" + postStatus +
                '}';
    }
}
