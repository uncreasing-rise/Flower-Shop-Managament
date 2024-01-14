package Menu;

import Object.OrderManage;
import java.io.IOException;
import java.util.Scanner;
import myUtil.Inputter;
import myUtil.Utils;
import object.FlowerManage;

public class FlowerShopApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static FlowerManage flowerManager;
    private static OrderManage orderManager;

    public static void main(String[] args) throws IOException {
        flowerManager = new FlowerManage(orderManager);
        orderManager = new OrderManage(flowerManager);

        int option = 0;
        System.out.println("\t******* FLOWER STORE MANAGEMENT *******");

        do {
            System.out.println("\nMAIN MENU:");
            System.out.println("1. Flower's management");
            System.out.println("2. Order's management");
            System.out.println("3. Quit");
            System.out.print("Please choose an option: ");

            boolean validInput = false;
            do {
                try {
                    option = Integer.parseInt(scanner.nextLine().trim());
                    validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            } while (!validInput);

            switch (option) {
                case 1:
                    FlowerMenu();
                    break;
                case 2:
                    OrderMenu();
                    break;
                case 3:
                    confirmAndQuit(flowerManager, orderManager);
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        } while (option != 3);
    }

    private static void FlowerMenu() {
        int nurseOption;
        do {
            System.out.println("\nNURSE MENU:");
            System.out.println("1. Add Flowers");
            System.out.println("2. Find a Flower");
            System.out.println("3. Update a Flower");
            System.out.println("4. Delete a Flower");
            System.out.println("5. Display Flowers");
            System.out.println("0. Back to main menu");

            nurseOption = Utils.getInt("Enter a valid option: ");

            switch (nurseOption) {
                case 1:
                    String choice;
                    do {
                        flowerManager.createFlower();
                        choice = Inputter.inputYesNo("Do you want to continue adding a new Flower? (Y/N): ");
                    } while (choice.equalsIgnoreCase("y"));
                    break;
                case 2:
                    flowerManager.findFlower();
                    break;
                case 3:
                    flowerManager.updateFlower();
                    break;
                case 4:
                    flowerManager.deleteFlower(orderManager);
                    break;
                case 5:
                    flowerManager.display();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        } while (nurseOption != 0);
    }

    private static void OrderMenu(){
        int patientOption;
        do {
            System.out.println("\nORDER MENU:");
            System.out.println("1. Add Orders");
            System.out.println("2. Display Orders");
            System.out.println("3. Sort the Orders List");
            System.out.println("4. Save the Data");
            System.out.println("5. Load the Data");
            System.out.println("0. Back to main menu");
            patientOption = Utils.getInt("Enter a valid option: ");

            switch (patientOption) {
                case 1:
                    orderManager.addOrder(flowerManager);
                    break;
                case 2:
                    orderManager.display();
                    break;
                case 3:
                    orderManager.sortAndDisplayOrders();
                    break;
                case 4:
                    if (flowerManager.saveDataFlower()) {
                        System.out.println("Flower data saved successfully.");
                    }
                    if (orderManager.saveDataOrder()) {
                        System.out.println("Order data saved successfully.");
                    }
                    break;
                case 5:
                    if (flowerManager.loadDataFlower()) {
                        System.out.println("Flower data loaded successfully.");
                    }
                    if (orderManager.loadDataOrder()) {
                        System.out.println("Order data loaded successfully.");
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        } while (patientOption != 0);
    }

    private static void confirmAndQuit(FlowerManage fm, OrderManage om) throws IOException {
        String confirm = Inputter.inputYesNo("Do you want to save data before quitting? (Y/N): ");
        System.out.println("Enter your choice: ");
        if (confirm.equalsIgnoreCase("y")) {
            if (fm.saveDataFlower()&& om.saveDataOrder()) {
                System.out.println("Data saved successfully.");
            } else {
                System.out.println("Error saving data.");
            }
        }
        System.out.println("Thank you for using the Flower Store Management Application!");
    }
}
