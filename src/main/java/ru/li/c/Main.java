package ru.li.c;

public class Main{
    public static void main (String[] args) {

        int a = 5;
        int b = 7;
        int c = 8;
        int d = 4;
        double e = 456.709;

        // применить несколько арифметических операций ( + , -, * , /) над двумя примитивами типа int
        int sum = a + b;
        int comp = c - d;
        int div = c / d;

        System.out.println("Sum: " + sum);
        System.out.println("Comp: " + comp);
        System.out.println("Div: " + div);

// применить несколько арифметических операций над int и double в одном выражении
        double result1 = e + d - (a + e);
        System.out.println("Result1: " + result1);

// применить несколько логических операций ( < , >, >=, <= )
        System.out.println(a == b);  //false
        System.out.println(c != b);  //true
        System.out.println( a >= d); //true

// получить переполнение при арифметической операции
        int maxInt = Integer.MAX_VALUE;
        int overflowInt = maxInt + 1; // overflowInt будет -2147483648 (минимальное значение int)
        System.out.println(overflowInt);
    }
}