package myUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Utils {

    private static Scanner scanner = new Scanner(System.in);

    public static String getString(String welcome, String msg) {
        boolean check = true;
        String result = "";
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.out.println(msg);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static boolean isAllLetters(String inputStr) {
        if (inputStr.matches("[a-zA-Z\\s]+")) {
            return true;
        }
        return false;
    }

    public static String getStringreg(String welcome, String pattern, String msg, String msgreg) {
        boolean check = true;
        String result = "";
        Scanner sc = new Scanner(System.in);
        do {

            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.out.println(msg);
            } else if (!result.matches(pattern)) {
                System.out.println(msgreg);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static int getInt(String prompt) {
        int value = 0;
        boolean validInput = false;

        do {
            try {
                System.out.print(prompt);
                value = Integer.parseInt(scanner.nextLine().trim());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        } while (!validInput);

        return value;
    }

    public static int getInt(String welcome, int min) {
        boolean check = true;
        int number = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {

                System.out.print(welcome);
                number = Integer.parseInt(sc.nextLine());
                if (number < min) {
                    System.out.println("Number must be large than " + min);
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number < min);
        return number;
    }

    public static int getInt(String welcome, int min, int max) {
        boolean check = true;
        int number = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {

                System.out.print(welcome);
                number = Integer.parseInt(sc.nextLine());
                if (number < min || number > max) {
                    System.out.println("Number must be large than " + min + " and " + "smaller than " + max);
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number < min || number > max);
        return number;
    }

    public static float getFloat(String welcome, int min) {
        boolean check = true;
        float number = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {

                System.out.print(welcome);
                number = Float.parseFloat(sc.nextLine());
                if (number < min) {
                    System.out.println("Number must be large than " + min);
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number < min);
        return number;
    }

    public static int getChoice(String welcome, int min, int max) {
        boolean check = true;
        int number = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {

                System.out.print(welcome);
                number = Integer.parseInt(sc.nextLine());
                if (number < min || number > max) {
                    System.out.println("Choice must be in range of " + min + " and " + max);
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Please input a number format!");
            }
        } while (check || number < min || number > max);
        return number;
    }

    //LocalDate nhưng mà bị hardcode format là dd/mm/yyyy
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    //lấy ngày theo LocalDate, SimpleFormatDate sắp xuống lỗ r.
    public static LocalDate getDate(String welcome, String msg) {
        Scanner scanner = new Scanner(System.in);
        LocalDate date = null;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print(welcome + " (dd/MM/yyyy): ");
                String input = scanner.nextLine();
                date = LocalDate.parse(input, DATE_FORMATTER);
                validInput = true;
            } catch (Exception e) {
                System.out.println(msg);
            }
        }
        return date;
    }

    public static long getLong(String welcome, long min) {
        boolean check = true;
        long number = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {

                System.out.print(welcome);
                number = Long.parseLong(sc.nextLine());
                if (number < min) {
                    System.out.println("Number must be large than " + min);
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number < min);
        return number;
    }

    public static String getGender(String welcome, String msg, String msg2) {
        boolean check = true;
        String result = "";
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.out.println(msg);
            } else if ((!result.equalsIgnoreCase("M") && !result.equalsIgnoreCase("F") )) {
                System.out.println(msg2);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static String getDescription(String welcome, String msg, String msg2) {
        boolean check = true;
        String result = "";
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.out.println(msg);
            } else if (result.length() < 3 || result.length() > 50) {
                System.out.println(msg2);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static String getShift() {
        System.out.println("Select a shift:");
        System.out.println("1. 24h - 8h");
        System.out.println("2. 8h - 16h");
        System.out.println("3. 16h - 24h");

        int choice = getInt("Enter your choice: ");

        switch (choice) {
            case 1:
                return "24h - 8h";
            case 2:
                return "8h - 16h";
            case 3:
                return "16h - 24h";
            default:
                System.out.println("Invalid choice. Defaulting to 24h - 8h.");
                return "24h - 8h";
        }
    }
     public static int getUserChoice(int min, int max) {
        int choice;
        do {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
        } while (choice < min || choice > max);
        return choice;
    }
          public static String getCategory() {
        int choice;

        do {
            System.out.println("Select category:");
            System.out.println("1. Annuals");
            System.out.println("2. Perennials");
            System.out.println("3. Biennials");
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        return "Annuals";
                    case 2:
                        return "Perennials";
                    case 3:
                        return "Biennials";
                    default:
                        System.out.println("Invalid choice. Select again!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        } while (true);
    }

}
