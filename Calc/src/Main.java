import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Введите два числа чрез пробел с оператором между чисел(1 + 3):");
        String numbers = input.nextLine();
        System.out.println(calc(numbers));
    }

    private static String calc(String input) {
        int numberA;
        int numberB;
        char operator;
        String result;
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Неверный формат выражения");
        }
        try {
            numberA = Integer.parseInt(parts[0]);
            numberB = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат выражения!\nВы ввели не целый цифровой символ!");
        }
        operator = parts[1].charAt(0);

        if (isNumbersCount(numberA, numberB)) {
            throw new IllegalArgumentException("Неверный формат выражения!\nВы ввели превышение символов в числе!");
        }

        switch (operator) {
            case '+' -> result = String.valueOf(numberA + numberB);
            case '-' -> result = String.valueOf(numberA - numberB);
            case '*' -> result = String.valueOf(numberA * numberB);
            case '/' -> result = String.valueOf(numberA / numberB);
            default -> throw new IllegalArgumentException("Неверный формат выражения!\nВы ввели не корректный оператор!");
        }
        return result;
    }

    private static boolean isNumbersCount(int numberA, int numberB) {
        return numberA < 1 || numberA > 10 || numberB < 1 || numberB > 10;
    }

}
