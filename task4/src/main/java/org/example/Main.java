package org.example;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static String pathFile;
    private static List<Integer> list = new ArrayList(); // мвссив введенных даных
    private static int[] nums; //массив значений
    private static int[] stepArr;  // массив шагов каждого элемента
    private static int delta;  // максимальное количество шагов в массиве шагов
    private static int midle; // значение массива, при котором не надо делать ни одного шага
    private static int sumArr; // суммма элементов массива

    private static int midleАrr; // среднее значение, до которого будем менять массив
    private static int lenght; // количество элементов в массиве
    private static int steps; // количество шагов
    private static int max;
    private static int min;

    private static void changeMinOrMax(int numsI) {
        if (numsI< min)
            min = numsI;
        if (numsI > max)
            max = numsI;
    }
    // сумма
    private static int sumStepArr(int[] stepArr){
        int sum = 0;
        for(int k = 0; k<stepArr.length; k++ ){
            sum = sum + stepArr[k];
        }
     return sum;
    }

    // изменение массива шагов
    private static void changeStepArr(int i) {
        int stepNew;
        int sum;    // сумма елементов массива stepArr
        int sumNew;    // сумма елементов массива stepArr с элементом stepNew
        if(i == 1) {
            delta = Math.round((max-min)/2);
            midle = min +delta;
            stepArr[0] = nums[0] - midle;
            stepArr[1] = nums[1] - midle;
        } else {
            stepNew = nums[i] - midle;
            sum = sumStepArr(stepArr);
            stepArr[i] = stepNew;
            if(stepNew != 0) {
                sumNew = sumStepArr(stepArr);
                while (sumNew > sum) {
                    delta = Math.round(stepNew/2);
                    for (int m = 0; m< stepArr.length; m++) {      // меняем stepArr, согласно новой delta
                        stepArr[m] = stepArr[m] + delta;
                        sumNew =  stepArr[m];
                    }
                    sum = sumNew;
                }
            }
        }
    }

    public static void main(String[] args) {

        // ввод пути к файлу
        Scanner in = new Scanner(System.in);
        if (in.hasNext()){
            pathFile = in.nextLine();
        } else {
            System.out.print("Введены некорректные данные!");
            return;
        }

        //считываем значение из файла в ArrayList
        try {
            File file1 = new File(pathFile);
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file1);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            while (line != null) {
                list.add(Integer.parseInt(line));
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //приобразовуем данные в массив
        nums = new int[list.size()];
        sumArr = 0;
        lenght = list.size();
        steps = 0;
        for(int j = 0; j<lenght; j++){
            nums[j] = list.get(j);
        }

        stepArr = new int[nums.length]; // создаем массив шагов
        // перебераем массив значений массива nums
        for(int i = 0; i<lenght; i++){
         if(i==0) {       // если первое значение массива
            min = nums[0];
            max = nums[0];
       //     stepArr[i]= (max-min)/2;
            delta = 0;
             continue;
         } else {
             changeMinOrMax(nums[i]);  //изменение максимальных и минимальных значений шагов
             changeStepArr(i);       // изменение массива шагов
         }
        }

        // сумма всех шагов по модулю
        for(int q = 0; q<lenght; q++) {
            steps = Math.abs(stepArr[q]);
        }

        System.out.println(steps);

    }
}