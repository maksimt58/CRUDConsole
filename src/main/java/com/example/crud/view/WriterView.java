package com.example.crud.view;

import com.example.crud.controller.WriterController;
import com.example.crud.model.Post;
import com.example.crud.model.PostStatus;
import com.example.crud.model.Writer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WriterView {
    private final Scanner scanner = new Scanner(System.in);
    private WriterController writerController;


    public WriterView(WriterController writerController) {
        this.writerController = writerController;
    }

    public void show() {
        int select;

        while (true) {
            System.out.println("============================");
            System.out.println("Меню управления писателями:");
            System.out.println("============================");
            System.out.println();
            System.out.println("1. Посмотреть всех писателей");
            System.out.println("2. Добавить нового писателя");
            System.out.println("3. Найти писателя по id");
            System.out.println("4. Изменить данные писателя");
            System.out.println("5. Удалить писателя");
            System.out.println("6. Выход");
            System.out.println();
            System.out.println("====================");
            System.out.println("Выберите пункт меню:");
            System.out.println("====================");
            select = scanner.nextInt();
            System.out.println("\n");

            switch (select) {
                case 1:
                    eventShowAllWriters();
                    break;

                case 2:
                    eventCreateNewWriter();
                    break;

                case 3:
                    eventGetWriterById();
                    break;

                case 4:
                    eventUpdateWriterById();
                    break;
                case 5:
                    eventDeleteWriterById();
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

    public void eventShowAllWriters() {
        System.out.println("==============================");
        System.out.println("Список существующих писателей:");
        System.out.println("==============================");

        for (Writer writer : writerController.onShowAll()) {
            System.out.println(writer.toString());
        }

        System.out.println();
        System.out.println("===============================================================================================");
    }

    public void eventCreateNewWriter() {
        String firstName = "";
        String lastName = "";
        Long postId;

        System.out.println("===========================");
        System.out.println("Добавление нового писателя:");
        System.out.println("===========================");


        System.out.println("Введите имя нового писателя: ");
        scanner.nextLine();
        firstName = scanner.nextLine();

        System.out.println("Введите фамилию нового писателя: ");
        lastName = scanner.nextLine();

        List<Post> posts = writerController.getAllPosts();
        List<Long> idPostsForNewWriter = new ArrayList<>();

        System.out.println();
        System.out.println("======================================================================");
        System.out.println("Выберите статьи, написанные данным писателем, из списка существующих: ");
        System.out.println("======================================================================");
        System.out.println();

        for (Post post : posts) {
            System.out.println(post);
        }

        System.out.println("Укажите id постов, для заверешения введите 0:");

        while ((postId = scanner.nextLong()) > 0) {
            idPostsForNewWriter.add(postId);
        }

        writerController.onCreate(firstName, lastName, idPostsForNewWriter);

    }

    public void eventGetWriterById() {
        Long id = 0L;

        System.out.println("==============================");
        System.out.println("Введите id искомого писателя: ");
        System.out.println("==============================");

        id = scanner.nextLong();

        Writer getWriter = writerController.getById(id);

        System.out.println('\n');
        System.out.println("==================");
        System.out.println("ИСКОМЫЙ ПИСАТЕЛЬ: ");
        System.out.println("=============================================================================================================");
        System.out.println(getWriter.toString());
        System.out.println("=============================================================================================================");
        System.out.println('\n');

    }

    public void eventUpdateWriterById() {
        Long id = 0L;
        String firstName = "";
        String lastName = "";

        System.out.println("===============================================");
        System.out.println("Введите id писателя, которого хотите изменить: ");
        System.out.println("===============================================");

        id = scanner.nextLong();

        Writer getWriter = writerController.getById(id);

        System.out.println("================================");
        System.out.println("Писатель, который будет изменён:");
        System.out.println("=============================================================================================================");
        System.out.println(getWriter.toString());
        System.out.println("=============================================================================================================");
        System.out.println();
        System.out.println("=============================================");
        System.out.println("Введите новые данные для выбранного писателя:");
        System.out.println("=============================================");
        System.out.println();

        System.out.println("Введите новое имя писателя: ");
        scanner.nextLine();
        firstName = scanner.nextLine();

        System.out.println("Введите новую фамилию писателя: ");
        lastName = scanner.nextLine();

        Writer editWriter = writerController.onUpdate(id, firstName, lastName);

        System.out.println("=======================================");
        System.out.println("Писатель с id = " + id + " был изменён:");
        System.out.println("=============================================================================================================");
        System.out.println(editWriter.toString());
        System.out.println("=============================================================================================================");

    }

    public void eventDeleteWriterById() {
        Long id = 0L;

        System.out.println("==============================================");
        System.out.println("Введите id писателя, которого хотите удалить: ");
        System.out.println("==============================================");

        id = scanner.nextLong();

        boolean deleteWriter = writerController.onDelete(id);

        if (deleteWriter) {
            System.out.println("======================================");
            System.out.println("Писатель с id = " + id + " был удален!");
            System.out.println("======================================");
            System.out.println('\n');
        } else {
            System.out.println('\n');
            System.out.println("=============================================================================================================");
            System.out.println("WARNING! Писателя с указанным id не существует! Повторите попытку");
            System.out.println("=============================================================================================================");
            System.out.println('\n');
            show();
        }

    }
}
