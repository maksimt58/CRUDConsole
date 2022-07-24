package com.example.crud.view;

import com.example.crud.controller.PostController;
import com.example.crud.model.Label;
import com.example.crud.model.Post;
import com.example.crud.model.PostStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostView {
    private final Scanner scanner = new Scanner(System.in);
    private PostController postController;

    public PostView(PostController postController) {
        this.postController = postController;
    }

    public void show() {
        int select;

        while (true) {
            System.out.println("=========================");
            System.out.println("Меню управления статьями:");
            System.out.println("=========================");
            System.out.println();
            System.out.println("1. Посмотреть список всех статей");
            System.out.println("2. Добавить новую статью");
            System.out.println("3. Найти статью по id");
            System.out.println("4. Изменить содержимое или статус статьи");
            System.out.println("5. Удалить статью");
            System.out.println("6. Выход");
            System.out.println();
            System.out.println("====================");
            System.out.println("Выберите пункт меню:");
            System.out.println("====================");
            select = scanner.nextInt();
            System.out.println("\n");

            switch (select) {
                case 1:
                    eventShowAllPosts();
                    break;

                case 2:
                    eventCreateNewPost();
                    break;

                case 3:
                    eventGetPostById();
                    break;
                case 4:
                    eventUpdatePostById();
                    break;
                case 5:
                    eventDeletePostById();
                    break;
                case 6:
                    System.out.println("********************Работа приложения завершена!********************");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неправильно выбран пункт! Пожалуйста, повторите ввод");
                    break;
            }
        }

    }

    public void eventShowAllPosts() {
        System.out.println("===========================");
        System.out.println("Список существующих статей:");
        System.out.println("===========================");

        for (Post post : postController.onShowAll()) {
            System.out.println(post.toString());
        }

        System.out.println();
        System.out.println("=======================================================================");
    }

    public void eventCreateNewPost() {
        String content = "";
        Long labelId;
        PostStatus postStatus = null;
        String status;

        System.out.println("========================");
        System.out.println("Добавление новой статьи:");
        System.out.println("========================");

        System.out.println("Введите содержимое новой статьи: ");
        System.out.println();
        scanner.nextLine();
        content = scanner.nextLine();


        System.out.println();
        System.out.println("Укажите статус новой статьи из списка доступных: ");

        for (PostStatus postStatusEnum : PostStatus.values()) {
            System.out.println(postStatusEnum);
        }

        System.out.println();
        System.out.println("Введите статус: ");

        status = scanner.next();


        switch (status) {
            case "ACTIVE":
                postStatus = PostStatus.ACTIVE;
                break;
            case "UNDER_REVIEW":
                postStatus = PostStatus.UNDER_REVIEW;
                break;
            case "DELETED":
                postStatus = PostStatus.DELETED;
                break;
        }

        List<Label> labels = postController.getAllLabels();
        List<Long> idLabelsForNewPost = new ArrayList<>();

        System.out.println();
        System.out.println("Выберите тэги для новой статьи из списка существующих: ");
        System.out.println();

        for (Label allLabel : labels) {
            System.out.println(allLabel);
        }

        System.out.println("Введите id тэга, для завершения введите 0: ");

        while ((labelId = scanner.nextLong()) > 0) {
            idLabelsForNewPost.add(labelId);
        }

        postController.onCreate(content, postStatus, idLabelsForNewPost);

    }

    public void eventGetPostById() {
        Long id = 0L;

        System.out.println("===========================");
        System.out.println("Введите id искомой статьи: ");
        System.out.println("===========================");

        id = scanner.nextLong();

        Post getPost = postController.getById(id);

        System.out.println('\n');
        System.out.println("================");
        System.out.println("ИСКОМАЯ СТАТЬЯ: ");
        System.out.println("=========================================================================================");
        System.out.println(getPost.toString());
        System.out.println("=========================================================================================");
        System.out.println('\n');
    }

    public void eventUpdatePostById() {
        Long id = 0L;
        String content = "";
        PostStatus postStatus = null;
        String status;

        System.out.println("============================================");
        System.out.println("Введите id статьи, которую хотите изменить: ");
        System.out.println("============================================");

        id = scanner.nextLong();

        Post getPost = postController.getById(id);

        System.out.println("===========================");
        System.out.println("Статья, которая будет изменена:");
        System.out.println("===========================================================");
        System.out.println(getPost.toString());
        System.out.println("===========================================================");
        System.out.println();
        System.out.println("==========================================");
        System.out.println("Введите новые данные для выбранной статьи:");
        System.out.println("==========================================");
        System.out.println();

        System.out.println("Введите новое содержимое статьи: ");
        System.out.println();
        scanner.nextLine();
        content = scanner.nextLine();

        System.out.println();
        System.out.println("Укажите новый статус статьи из списка доступных: ");

        for (PostStatus postStatusEnum : PostStatus.values()) {
            System.out.println(postStatusEnum);
        }

        System.out.println();
        System.out.println("Введите статус: ");

        status = scanner.next();


        switch (status) {
            case "ACTIVE":
                postStatus = PostStatus.ACTIVE;
                break;
            case "UNDER_REVIEW":
                postStatus = PostStatus.UNDER_REVIEW;
                break;
            case "DELETED":
                postStatus = PostStatus.DELETED;
                break;
        }

        Post editPost = postController.onUpdate(id, content, postStatus);

        System.out.println("=======================================");
        System.out.println("Статья с id = " + id + " была изменена:");
        System.out.println("========================================================");
        System.out.println(editPost.toString());
        System.out.println("========================================================");
    }

    public void eventDeletePostById() {
        Long id = 0L;

        System.out.println("===========================================");
        System.out.println("Введите id статьи, которую хотите удалить: ");
        System.out.println("===========================================");

        id = scanner.nextLong();

        boolean deletePost = postController.onDelete(id);

        if (deletePost) {
            System.out.println("======================================");
            System.out.println("Статья с id = " + id + " была удалена!");
            System.out.println("======================================");
            System.out.println('\n');
        } else {
            System.out.println('\n');
            System.out.println("=============================================================================================================");
            System.out.println("WARNING! Статьи с указанным id не существует! Повторите попытку");
            System.out.println("=============================================================================================================");
            System.out.println('\n');
            show();
        }
    }


}

