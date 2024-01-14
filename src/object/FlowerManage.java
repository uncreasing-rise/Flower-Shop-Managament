package object;

import Model.Flower;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import myUtil.Utils;
import Object.OrderManage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FlowerManage {

    private Set<Flower> flowers;
    private final Scanner scanner = new Scanner(System.in);

    public FlowerManage(OrderManage orderManage) {
        flowers = new HashSet<>();
    }

    public Set<Flower> getFlowers() {
        return flowers;
    }

    public void createFlower() {
        String id, description, category;
        Float unitPrice;
        LocalDate importDate;

        boolean flowerExists;
        do {
            id = Utils.getStringreg("Enter your Flower ID (Fxxx - example: F001): ", "^F\\d{3}$", "Flower ID is required!!!. Enter again please.", "Please provide the input correctly");

            flowerExists = false;
            for (Flower flower : flowers) {
                if (flower.getId().equals(id)) {
                    flowerExists = true;
                    System.out.println("The Flower ID has already existed.");
                    break;
                }
            }
        } while (flowerExists);

        description = Utils.getDescription("Flower's Name: ", "Flower's Name is required!!!. Enter again please.", "Name length must be between 3 and 50 characters.");
        importDate = Utils.getDate("Enter import date", "Please fill in a valid import date (dd/MM/yyyy).");
        unitPrice = Utils.getFloat("Enter Price (Price must >= 1): ", 1);
        category = Utils.getCategory();
        Flower newFlower = new Flower(id, description, importDate, unitPrice, category);
        flowers.add(newFlower);
        System.out.println("Create Flower successfully");

    }

    public void findFlower() {
        String input = Utils.getString("Enter name or id: ", "Enter again! Input is required!");

        boolean found = false;

        for (Flower flower : flowers) {
            if (flower.getId().equalsIgnoreCase(input)) {
                System.out.println("+------------+----------------------+------------+--------------+--------------------+");
                System.out.println("|    ID      |     Description      | Import Date|     Price    |      Category      |");
                System.out.println("+------------+----------------------+------------+--------------+--------------------+");
                flower.showInfo();
                found = true;
                break;
            } else if (flower.getDescription().toLowerCase().contains(input.toLowerCase())) {
                System.out.println("+------------+----------------------+------------+--------------+--------------------+");
                System.out.println("|    ID      |     Description      | Import Date|     Price    |      Category      |");
                System.out.println("+------------+----------------------+------------+--------------+--------------------+");
                flower.showInfo();
                found = true;
            }
        }
        System.out.println("+------------+----------------------+------------+--------------+--------------------+");

        if (!found) {
            System.out.println("The flower does not exist.");
        }
    }

    public void updateFlower() {
        String inputName = Utils.getDescription("Enter Flower's Name: ", "Flower's Name: is required!!!. Enter again please.", "Name length must be between 3 and 50 characters.");

        boolean found = false;

        for (Flower flower : flowers) {
            if (flower.getDescription().contains(inputName)) {
                System.out.println("Found Flower:");
                displayFlowerDetails(flower);
                System.out.println("Enter new details:");
                // Get new details from the user
                float newUnitPrice = Utils.getFloat("New Unit Price: ", 1);
                // Update the flower details
                flower.setUnitPrice(newUnitPrice);

                System.out.println("Flower details updated successfully!");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("The flower does not exist.");
        }
    }

    public void display() {
        System.out.println("+------------+----------------------+------------+--------------+--------------------+");
        System.out.println("|    ID      |     Description      | Import Date|     Price    |      Category      |");
        System.out.println("+------------+----------------------+------------+--------------+--------------------+");
        List<Flower> sortedFlowers = new ArrayList<>(flowers);
        sortedFlowers.sort(Comparator.comparing(Flower::getId));
        for (Flower flower : sortedFlowers) {
            flower.showInfo();
        }
        System.out.println("+------------+----------------------+------------+--------------+--------------------+");

    }

    private void displayFlowerDetails(Flower flower) {
        System.out.println("+------------+----------------------+------------+--------------+--------------------+");
        System.out.println("|    ID      |     Description      | Import Date|     Price    |      Category      |");
        System.out.println("+------------+----------------------+------------+--------------+--------------------+");
        System.out.printf("|%-12s|%-22s|%-12s|$%-13.2f|%-20s|%n",
                flower.getId(),
                flower.getDescription(),
                flower.getImportDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                flower.getUnitPrice(),
                flower.getCategory());
        System.out.println("+------------+----------------------+------------+--------------+--------------------+");
    }

    public void deleteFlower(OrderManage om) {
        display();
        String flowerIdToDelete = Utils.getString("Enter Flower's ID want to delete: ", "Flower's ID is required! Enter again please.");
        Flower flowerToDelete = this.findFlowerById(flowers, flowerIdToDelete);

        if (flowerToDelete == null) {
            System.out.println("The flower does not exist.");
        } else {
            System.out.println("Found Flower:");
            displayFlowerDetails(flowerToDelete);

            if (om.isFlowerInOrder(flowerToDelete)) {
                System.out.println("Cannot delete the flower. It is used in an order detail.");
            } else {
                System.out.print("Are you sure you want to delete this flower? (Y/N): ");
                String confirmation = scanner.nextLine();
                if (confirmation.equalsIgnoreCase("Y")) {
                    flowers.remove(flowerToDelete);
                    System.out.println("Flower deleted successfully!");
                } else {
                    System.out.println("Deletion canceled.");
                }
            }
        }
    }

    public static Flower findFlowerById(Set<Flower> flowers, String flowerId) {
        for (Flower flower : flowers) {
            if (flower.getId().equals(flowerId)) {
                return flower;
            }
        }
        return null; // Flower with given ID not found
    }

    public boolean saveDataFlower() {
        try ( ObjectOutputStream flowerOutputStream = new ObjectOutputStream(new FileOutputStream("flowers.dat"))) {
            flowerOutputStream.writeObject(flowers);
            System.out.println("Data saved successfully.");
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred while saving data:");
            e.printStackTrace();
            return false;
        }
    }

    public boolean loadDataFlower() {
        try ( ObjectInputStream flowerInputStream = new ObjectInputStream(new FileInputStream("flowers.dat"))) {
            flowers = (Set<Flower>) flowerInputStream.readObject();
            System.out.println("LOADING...");
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("An error occurred while loading data:");
            e.printStackTrace();
            return false;
        }
    }

}
