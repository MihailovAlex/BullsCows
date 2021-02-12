package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        String enter = sc.nextLine();
        int count;    // игровое поле
        int choice;    // варианты переменных
        try {
            count = Integer.parseInt(enter);
            System.out.println("Input the number of possible symbols in the code \n(at least 10 [0-9] " +
                    "and at most 36 [0-9, a-z]): ");
            enter = sc.nextLine();
            choice = Integer.parseInt(enter);
            if (choice > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            } else if (count == 0) {
                System.out.println("Error: length of the secret code should be more 0");
            } else if (count <= choice) {
                System.out.print("The secret is prepared: ");
                String stars = "*".repeat(count);
                System.out.print(stars + " (0-9)");
                if (choice > 10) {
                    char a = 'a', b = (char) (a + choice - 11);
                    System.out.printf(", (%c-%c)", a, b);
                }
                System.out.println("\nOkay, let's start a game!");
                int[] key = generateField(choice, count);
                //System.out.println(Arrays.toString(key));
                play(key);
            } else {
                System.out.println("Error: it's not possible to generate a code with a length of "
                        + count + " with " + choice + " unique symbols.");
            }
        } catch (RuntimeException e) {
            System.out.println("Error: " + enter + " isn't a valid number.");
        }
    }

    public static void play(int[] key) {
        int bulls = 0, cows = 0;
        Scanner sc = new Scanner(System.in);
        int turn = 0;
        while (true) {
            System.out.println("Turn " + ++turn + ":");
            char[] enter = sc.nextLine().toCharArray();
            int[] digits = new int[enter.length];
            for (int i = 0; i < enter.length; i++) {
                digits[i] = enter[i];
            }
//            System.out.println(Arrays.toString(digits));
//            int[] digits = Arrays.stream(sc.nextLine().split(""))
//                    .mapToInt(Integer::parseInt).toArray();
            for (int i = 0; i < digits.length; i++) {
                for (int j = 0; j < key.length; j++) {
                    if (digits[i] == key[j] && i == j) {
                        bulls++;
                    } else if (digits[i] == key[j]) {
                        cows++;
                    }
                }
            }
            if (bulls != key.length) {
                System.out.println("Grade: " + bulls + " bull and " + cows + " cow");
                bulls = 0;
                cows = 0;
            } else {
                System.out.println("Grade: " + bulls + " bulls\n" +
                        "Congratulations! You guessed the secret code.");
                break;
            }
        }
    }

    public static int[] generateRandomNumber(int choice) {
        Random random = new Random();
        if (choice > 10) {
            choice = 10;
        }
        int[] array = new int[choice];
        for (int i = 0; i < choice; i++) {
            int k = random.nextInt(10);
            for (int j = 0; j < i; j++) {
                if (k == array[j]) {
                    i--;
                    break;
                } else {
                    array[i] = k;
                }
            }
        }
        return array;
    }

    public static int[] generateRandomSymbol(int choice) {
        Random random = new Random();
        int[] array = new int[choice - 10];
        int k = 97;
        for (int i = 0; i < choice - 10; i++) {
            array[i] = k;
            k++;
        }
        return array;
    }

    public static int[] generateField(int choice, int count) {
        int[] array1 = generateRandomNumber(choice);
        for (int i = 0; i < array1.length; i++) {
            array1[i] += 48;
        }

        if (choice > 10) {
            int[] array2 = generateRandomSymbol(choice);
            int[] array12 = new int[choice];
            System.arraycopy(array1, 0, array12, 0, array1.length);
            System.arraycopy(array2, 0, array12, array1.length, array2.length);

            for (int i = array12.length - 1; i > 0; i--) {
                Random r = new Random();
                int index = r.nextInt(i);
                int var = array12[i];
                array12[i] = array12[index];
                array12[index] = var;
            }

            int[] rez = new int[count];
            while (count > 0) {
                rez[count - 1] = array12[count - 1];
                count--;
            }
            return rez;
        } else {
            int[] rez = new int[count];
            while (count > 0) {
                rez[count - 1] = array1[count - 1];
                count--;
            }
            return rez;
        }


    }
}
