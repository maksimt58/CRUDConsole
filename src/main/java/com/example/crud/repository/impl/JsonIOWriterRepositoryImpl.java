package com.example.crud.repository.impl;

import com.example.crud.model.Label;
import com.example.crud.model.Model;
import com.example.crud.model.Post;
import com.example.crud.model.Writer;
import com.example.crud.repository.WriterRepository;
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


public class JsonIOWriterRepositoryImpl implements WriterRepository {
    private final String FILE_PATH_WRITERS = "src\\main\\resources\\writers.json";
    private File fileWriters = new File(FILE_PATH_WRITERS);
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final List<Writer> WRITERS_LIST = new ArrayList<>();

    @Override
    public List<Writer> getAll() {
        if(WRITERS_LIST.isEmpty()) {
            readFromJsonToListWriter();
        }
        return WRITERS_LIST;
    }

    @Override
    public Writer getById(Long id) {
        return WRITERS_LIST.stream().filter(writer -> writer.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Writer update(Writer writer) {
        List<Writer> writers = getAllWritersInternal();
        writers.forEach(w -> {
            if(w.getId().equals(writer.getId())){
                w.setFirstName(writer.getFirstName());
                w.setLastName(writer.getLastName());
            }
        });
        writeListToJson(writers);
        return writer;
    }

    @Override
    public boolean delete(Long id) {
        List<Writer> writers = getAllWritersInternal();
        boolean removed = writers.removeIf(writer -> writer.getId().equals(id));
        writeListToJson(writers);
        return removed;
    }

    @Override
    public void save(Writer writer) {
        List<Writer> writers = getAllWritersInternal();
        writer.setId(generateId(writers));
        writers.add(writer);
        writeListToJson(writers);
    }

    private List<Writer> getAllWritersInternal() {
        return WRITERS_LIST;
    }

    private Long generateId(List<Writer> writer) {
        Writer maxIdWriter = writer.stream().max(Comparator.comparing(Writer::getId)).orElse(null);
        return Objects.nonNull(maxIdWriter) ? maxIdWriter.getId() + 1 : 1L;
    }

    public void writeListToJson(List<? extends Model> tList) {

        try (FileWriter fileWriter = new FileWriter(fileWriters.getAbsolutePath())) {
            if (!tList.isEmpty()) {
                GSON.toJson(tList, fileWriter);
                fileWriter.flush();
            } else System.out.println("Ошибка - передан пустой список в метод writeListToJson");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void readFromJsonToListWriter() {
        Type listOfWriterObject = new TypeToken<ArrayList<Writer>>() {
        }.getType();

        try (FileReader fileReader = new FileReader(fileWriters.getAbsolutePath())) {

            List<Writer> outputList = GSON.fromJson(fileReader, listOfWriterObject);

            if (outputList != null) {
                WRITERS_LIST.addAll(outputList);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Ошибка нахождения файла json по указанному пути в методе readFromJsonToList");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
