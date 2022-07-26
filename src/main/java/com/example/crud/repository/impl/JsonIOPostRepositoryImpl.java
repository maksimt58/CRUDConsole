package com.example.crud.repository.impl;

import com.example.crud.model.Model;
import com.example.crud.model.Post;
import com.example.crud.repository.PostRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class JsonIOPostRepositoryImpl implements PostRepository {
    private final String FILE_PATH_POSTS = "src\\main\\resources\\posts.json";
    private File filePosts = new File(FILE_PATH_POSTS);
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final List<Post> POSTS_LIST = new ArrayList<>();

    @Override
    public Post getById(Long id) {
        return POSTS_LIST.stream().filter(post -> post.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public boolean delete(Long id) {
        List<Post> posts = getAllPostsInternal();
        boolean removed = posts.removeIf(post -> post.getId().equals(id));
        writeListToJson(posts);
        return removed;
    }

    @Override
    public Post update(Post post) {
        List<Post> posts = getAllPostsInternal();
        posts.forEach(p -> {
            if(p.getId().equals(post.getId())){
                p.setContent(post.getContent());
                p.setUpdated(LocalDate.now());
                p.setPostStatus(post.getPostStatus());
            }
        });
        writeListToJson(posts);
        return post;
    }

    @Override
    public List<Post> getAll() {
        if(POSTS_LIST.isEmpty()) {
            readFromJsonToListPost();
        }
        return getAllPostsInternal();
    }

    @Override
    public void save(Post post) {
        List<Post> posts = getAllPostsInternal();
        post.setId(generateId(posts));
        posts.add(post);
        writeListToJson(posts);
    }

    private List<Post> getAllPostsInternal() {
        return POSTS_LIST;
    }

    private Long generateId(List<Post> posts) {
        Post maxIdPost = posts.stream().max(Comparator.comparing(Post::getId)).orElse(null);
        return Objects.nonNull(maxIdPost) ? maxIdPost.getId() + 1 : 1L;
    }

    private void writeListToJson(List<? extends Model> tList) {

        try (FileWriter fileWriter = new FileWriter(filePosts.getAbsolutePath())) {
            if (!tList.isEmpty()) {
                GSON.toJson(tList, fileWriter);
                fileWriter.flush();
            } else System.out.println("Ошибка - передан пустой список в метод writeListToJson");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void readFromJsonToListPost() {
        Type listOfPostObject = new TypeToken<ArrayList<Post>>() {
        }.getType();

        try (FileReader fileReader = new FileReader(filePosts.getAbsolutePath())) {

            List<Post> outputList = GSON.fromJson(fileReader, listOfPostObject);

            if (outputList != null) {
                POSTS_LIST.addAll(outputList);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Ошибка нахождения файла json по указанному пути в методе readFromJsonToList");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
