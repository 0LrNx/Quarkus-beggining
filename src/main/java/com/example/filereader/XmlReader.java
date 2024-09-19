package com.example.filereader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.util.Iterator;

public class XmlReader {
    public static void main(String[] args) {
        XmlMapper xmlMapper = new XmlMapper();
        File[] files = {
                new File("src/main/resources/tasks.xml"),
//                new File("src/main/resources/tasks2.xml")
        };

        try {
            int fileIndex = 1;
            for (File file : files) {
                JsonNode rootNode = xmlMapper.readTree(file);
                JsonNode tasksNode = rootNode.path("task");

                System.out.println("file" + fileIndex + ":");
                System.out.println("     path: " + file.getAbsolutePath());
                System.out.println("     data [");

                Iterator<JsonNode> elements = tasksNode.elements();
                while (elements.hasNext()) {
                    JsonNode taskNode = elements.next();
                    JsonNode idNode = taskNode.path("id");
                    String id = idNode.asText();
                    System.out.println("          path: tasks.task.id");
                    System.out.println("          type: int");
                    System.out.println("          name: id");
                }

                System.out.println("     ]");
                fileIndex++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}