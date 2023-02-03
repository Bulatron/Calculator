import java.util.Scanner;

public class Main {
    static String [] arabNum = new String[] {"1","2","3","4","5","6","7","8","9","10"};
    static String [] signs = new String[] {"+","-","*","/"};
    static String [] romNum = new String[] {"I","II","III","IV","V","VI","VII","VIII","IX","X",};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //считываем данные и запускаем метод calc

        String expression = sc.nextLine();
        System.out.println(calc(expression));
        sc.close();
    }
    public static String calc (String input) {
        String [] expressionArray = input.split(" ");
        if (expressionArray.length < 3) {
            throw new ArrayIndexOutOfBoundsException("убедитесь что между числами и знаком есть пробелы.");
        }
        String firstNum = expressionArray[0];
        String sign = expressionArray[1];
        String secondNum = expressionArray[2];

        //проверка чисел: римские или арабские

        boolean firstIsArab = false;
        boolean secondIsArab = false;
        boolean firstIsRom = false;
        boolean secondIsRom = false;
        boolean rightSign = false;

        for (String x : arabNum){
            if (firstNum.equals(x)) {
                firstIsArab = true;
                break;
            }
        }
        for (String x : arabNum){
            if (secondNum.equals(x)) {
                secondIsArab = true;
                break;
            }
        }
        for (String x : romNum){
            if (firstNum.equals(x)) {
                firstIsRom = true;
                break;
            }
        }
        for (String x : romNum){
            if (secondNum.equals(x)) {
                secondIsRom = true;
                break;
            }
        }
        if ((firstIsArab && secondIsRom) || (firstIsRom && secondIsArab) || (!firstIsArab && !firstIsRom) || (!secondIsArab && !secondIsRom) ) {
            throw new NumberFormatException("калькулятор умеет работать с числами от 1 до 10 и одновременно только с арабскими или только с римскими числами.");
        }

        //проверка знака

        for (String x : signs){
            if (sign.equals(x)) {
                rightSign = true;
                break;
            }
        }
        if (!rightSign) {
            throw new ArithmeticException("незнакомое выражение, калькулятор умеет выполнять следующие операции : a + b, a - b, a * b, a / b.");
        }

        //если оба числа римские конвертируем
        int subResult = 0;
        String result = "";
        if (firstIsRom) {
            switch (sign) {
                case "+":
                    subResult = (Integer.parseInt(romToArab(firstNum)) + Integer.parseInt(romToArab(secondNum)));
                    break;
                case "-":
                    subResult = (Integer.parseInt(romToArab(firstNum)) - Integer.parseInt(romToArab(secondNum)));
                    if (subResult < 0) {
                        throw new NumberFormatException("Результатом работы калькулятора с римскими числами могут быть только положительные числа.");
                    }
                    break;
                case "*":
                    subResult = (Integer.parseInt(romToArab(firstNum)) * Integer.parseInt(romToArab(secondNum)));
                    break;
                case "/":
                    subResult = (Integer.parseInt(romToArab(firstNum)) / Integer.parseInt(romToArab(secondNum)));
                    break;
            }
            return arabToRom(subResult);
        } else {
            switch (sign) {
                case "+":
                    result = (Integer.parseInt(firstNum) + Integer.parseInt(secondNum)) + "";
                    break;
                case "-":
                    result = (Integer.parseInt(firstNum) - Integer.parseInt(secondNum)) + "";
                    break;
                case "*":
                    result = (Integer.parseInt(firstNum) * Integer.parseInt(secondNum)) + "";
                    break;
                case "/":
                    result = (Integer.parseInt(firstNum) / Integer.parseInt(secondNum)) + "";
                    break;
            }
        }
        return result;
    }

    public static String romToArab (String num) {
        String result = "";
        for (int i = 0; i < romNum.length; i++) {
            if(num.equals(romNum[i]))
                result = arabNum[i];
        }
        return result;
    }

    public static String arabToRom (int num) {
        StringBuilder rom = new StringBuilder("");

        int c1 = num / 100;
        rom.append(c(c1));
        int c2 = num % 100;

        int l1 = c2 / 50;
        int l2 = c2 % 50;
        rom.append(l(l1,l2));

        int x1 = l2 / 10;
        rom.append(x(x1));
        int x2 = l2 % 10;

        rom.append(units(x2));
        return rom.toString();
    }
    public static String c (int c1){
        int c2 = (c1 * 100) % 100;
        if(c1 == 1) {
            return "C";
        } else if(c2 > 89){
            return "XC";
        } else {
            return "";
        }
    }

    public static String l (int l1, int l2){
        if(l1 == 1) {
            return "L";
        } else if (l2 > 39) {
            return "XL";
        } else {
            return "";
        }
    }

    public static String x (int x1){
        if ((x1 != 0) && (x1 < 4)) {
            StringBuilder a = new StringBuilder("");
            int i = 0;
            while (i < x1) {
                a.append("X");
                i++;
            }
            return a.toString();
        }
        else return "";
    }

    public static String units(int x2) {
        String[] a = {"","I","II","III","IV","V","VI","VII","VIII","IX"};
        return a[x2];
    }
}