package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

import java.util.*;

public class Main {
    private static String pathValues;
    private static String pathTests;

    private static Map <Long, String> idValuesMap = new HashMap<Long, String>();
    public static void main(String[] args) {

        // ввод пути к файлам
        Scanner in = new Scanner(System.in);
        if (in.hasNext()){
            pathValues = in.nextLine();
        } else {
            System.out.print("Введены некорректные данные!");
            return;
        }

        if (in.hasNext()){
            pathTests = in.nextLine();
        } else {
            System.out.print("Введены некорректные данные!");
            return;
        }

        //считываем первый файл
        try {
            // считывание файла JSON
            FileReader reader = new FileReader(pathValues);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObjectValues = (JSONObject) jsonParser.parse(reader);
            // получение массива
            JSONArray lang= (JSONArray) jsonObjectValues.get("values");
            // берем каждое значение из массива json отдельно
            Iterator i = lang.iterator();
            while (i.hasNext()) {
                JSONObject innerObj = (JSONObject) i.next();
                //добавляем элементы в HashMap
                long id = (Long) innerObj.get("id");
                String value = (String) innerObj.get("value");
                idValuesMap.put(id, value);

            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        //считываем второй файл

        try {
            // считывание файла JSON
            FileReader reader = new FileReader(pathTests);

            JSONParser jsonParser = new JSONParser();
            JSONObject JSONObjTests = (JSONObject) jsonParser.parse(reader);
            Long idObj = (Long)JSONObjTests.get("id");
            String value = idValuesMap.get(idObj);  // из массива первого файла берем по id - value
            String valueObj = (String) JSONObjTests.get("value");
            JSONObjTests.put("value", value);

            //ищим "values" в "JSONObjTests
            JSONArray valuesObjArr = (JSONArray) JSONObjTests.get("values");

            // берем каждое значение из массива json отдельно
            Iterator it = valuesObjArr.iterator();

            while (it.hasNext()) {
                JSONObject innerObj = (JSONObject) it.next();

                long id = (Long) innerObj.get("id");
                String value2 = idValuesMap.get(id);
                String value2Obj = (String)innerObj.get("value");
                innerObj.put("value", value2);
            }

       //   создание, запись файла
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter("task3/report.json");
                fileWriter.write(JSONObjTests.toJSONString());
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    if (fileWriter != null) {
                        fileWriter.flush();
                        fileWriter.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}
