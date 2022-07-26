package com.example.crud.repository.impl;

import com.example.crud.model.Label;
import com.example.crud.model.Model;
import com.example.crud.model.Writer;
import com.example.crud.repository.LabelRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class JsonIOLabelRepositoryImpl implements LabelRepository {
    private final  String FILE_PATH_LABELS = "src\\main\\resources\\labels.json";
    private File fileLabels = new File(FILE_PATH_LABELS);
    private final  Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final List<Label> LABELS_LIST = new ArrayList<>();

    @Override
    public Label getById(Long id) {
        return LABELS_LIST.stream().filter(label -> label.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public boolean delete(Long id) {
        List<Label> labels = getAllLabelsInternal();
        boolean removed = labels.removeIf(label -> label.getId().equals(id));
        writeListToJson(labels);
        return removed;
    }

    @Override
    public Label update(Label label) {
        List<Label> labels = getAllLabelsInternal();
        labels.forEach(l -> {
            if(l.getId().equals(label.getId())){
                l.setNameLabel(label.getNameLabel());
            }
        });
        writeListToJson(labels);
        return label;
    }

    @Override
    public List<Label> getAll() {
        if(LABELS_LIST.isEmpty()) {
            readFromJsonToListLabel();
        }
        return LABELS_LIST;
    }

    @Override
    public Label save(Label label) {
        List<Label> labels = getAllLabelsInternal();
        label.setId(generateId(labels));
        labels.add(label);
        writeListToJson(labels);
        return label;
    }

    private List<Label> getAllLabelsInternal() {
        return LABELS_LIST;
    }

    private Long generateId(List<Label> labels) {
        Label maxIdLabel = labels.stream().max(Comparator.comparing(Label::getId)).orElse(null);
        return Objects.nonNull(maxIdLabel) ? maxIdLabel.getId() + 1 : 1L;
    }

    private void writeListToJson(List<? extends Model> tList){

        try(FileWriter fileWriter = new FileWriter(fileLabels.getAbsolutePath())){
            if (!tList.isEmpty()) {
                GSON.toJson(tList, fileWriter);
            }
            else System.out.println("Ошибка - передан пустой список в метод writeListToJson");
        } catch (IOException e) {
            System.out.println("Ошибка при сериализации тэгов в json");
        }
    }

    private void readFromJsonToListLabel() {
        Type listOfLabelObject = new TypeToken<ArrayList<Label>>() {
        }.getType();

        try(FileReader fileReader = new FileReader(fileLabels.getAbsolutePath())) {

            List<Label> outputList = GSON.fromJson(fileReader, listOfLabelObject);

            if (outputList != null) {
                LABELS_LIST.addAll(outputList);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Ошибка нахождения файла json по указанному пути в методе readFromJsonToList");
        } catch (IOException e) {
            System.out.println("Ошибка при десериализации тэгов из json");
        }
    }

}
