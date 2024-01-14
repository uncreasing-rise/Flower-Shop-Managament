package Object;

import Model.Flower;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Model.Order;
import Model.OrderDetail;
import Model.OrderHeader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import myUtil.Utils;
import object.FlowerManage;

public class OrderManage {

    private List<Order> orders = new ArrayList<>();
    private Set<Flower> flowers;
    private final Scanner scanner = new Scanner(System.in);

    public OrderManage(FlowerManage flowerManager) {
        this.flowers = flowerManager.getFlowers();
    }

    public void addOrder(FlowerManage fm) {
        Scanner scanner = new Scanner(System.in);

        boolean continueAddingOrder = true;

        while (continueAddingOrder) {
            try {
                String orderID;
                boolean orderExists;
                do {
                    orderID = Utils.getStringreg("Enter your Order ID (Oxxx - example: O001): ", "^O\\d{3}$", "Order ID is required!!!. Enter again please.", "Please provide the input correctly");

                    orderExists = false;
                    for (Order order : orders) {
                        if (order.getOrderHeader().getOrderID().equals(orderID)) {
                            orderExists = true;
                            System.out.println("The Flower ID has already existed.");
                            break;
                        }
                    }
                } while (orderExists);

                LocalDate orderDate = Utils.getDate("Enter order date", "Please fill in a valid order date (dd/MM/yyyy).");
                String customerName;
                do {
                    customerName = Utils.getString("Enter Customer's name: ", "Customer's name is required!!!. Enter again please.");
                    if (!Utils.isAllLetters(customerName)) {
                        System.out.println("The Customer's name is not valid. Please input again!");
                    }
                } while (!Utils.isAllLetters(customerName));

                OrderHeader orderHeader = new OrderHeader(orderID, orderDate, customerName);
                Order order = new Order(orderHeader);

                boolean continueAddingDetails = true;
                Set<String> usedOrderDetailIds = new HashSet<>(); // To keep track of used IDs
                while (continueAddingDetails) {
                    try {
                        System.out.println("Order Detail: ");
                        String orderDetailId = Utils.getStringreg("Enter your OrderDetail ID (Dxxx - example: D001): ", "^D\\d{3}$", "OrderDetail ID is required!!!. Enter again please.", "Please provide the input correctly");

                        if (usedOrderDetailIds.contains(orderDetailId)) {
                            System.out.println("The Order Detail ID is already used. Please enter a unique ID.");
                            continue;
                        }

                        usedOrderDetailIds.add(orderDetailId);
                        fm.display();
                        // Rest of your code for adding order details
                        String flowerId = Utils.getString("Enter Flower's ID: ", "Cannot be empty");
                        Flower flower = FlowerManage.findFlowerById(flowers, flowerId);
                        if (flower == null) {
                            System.out.println("The flower with ID " + flowerId + " does not exist.");
                            continue;
                        }

                        int quantity = Utils.getInt("Enter Quantity: ", 1);

                        double flowerCost = flower.getUnitPrice() * quantity;

                        OrderDetail orderDetail = new OrderDetail(orderDetailId, flower, quantity, flowerCost);
                        order.addOrderDetail(orderDetail);

                        System.out.println("Order detail added successfully.");

                        String continueAddingDetailsInput;
                        do {
                            System.out.print("Add another order detail for the current order? (yes/no): ");
                            continueAddingDetailsInput = scanner.nextLine();
                            if (!continueAddingDetailsInput.equalsIgnoreCase("yes") && !continueAddingDetailsInput.equalsIgnoreCase("no")) {
                                System.out.println("Please enter 'yes' or 'no'.");
                            }
                        } while (!continueAddingDetailsInput.equalsIgnoreCase("yes") && !continueAddingDetailsInput.equalsIgnoreCase("no"));

                        continueAddingDetails = continueAddingDetailsInput.equalsIgnoreCase("yes");
                    } catch (Exception e) {
                        System.out.println("An error occurred while adding order details. Please try again.");
                    }
                }

                orders.add(order);

                String continueAddingOrderInput;
                do {
                    System.out.print("Add another new order? (yes/no): ");
                    continueAddingOrderInput = scanner.nextLine();
                    if (!continueAddingOrderInput.equalsIgnoreCase("yes") && !continueAddingOrderInput.equalsIgnoreCase("no")) {
                        System.out.println("Please enter 'yes' or 'no'.");
                    }
                } while (!continueAddingOrderInput.equalsIgnoreCase("yes") && !continueAddingOrderInput.equalsIgnoreCase("no"));

                continueAddingOrder = continueAddingOrderInput.equalsIgnoreCase("yes");
            } catch (Exception e) {
                System.out.println("An error occurred while adding the order. Please try again.");
            }
        }
    }

    public void display() {
        LocalDate startDate = Utils.getDate("Enter start date", "Please fill in a valid date (dd/MM/yyyy).");
        LocalDate endDate = Utils.getDate("Enter end date", "Please fill in a valid date (dd/MM/yyyy).");

        List<Order> displayList = new ArrayList<>();
        for (Order o : this.orders) {
            if (isInRangeDate(startDate, endDate, o.getOrderHeader().getOrderDate())) {
                displayList.add(o);
            }
        }
        System.out.println("LIST ORDER FROM " + startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " TO " + endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        display(displayList);
    }

    private boolean isInRangeDate(LocalDate startDate, LocalDate endDate, LocalDate orderDate) {
        return !orderDate.isBefore(startDate) && !orderDate.isAfter(endDate);
    }

    public boolean isFlowerInOrder(Flower flower) {
        for (Order order : orders) {
            List<OrderDetail> orderDetails = order.getOrderDetails();
            for (OrderDetail orderDetail : orderDetails) {
                if (orderDetail.getFlower().getId().equals(flower.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void display(List<Order> displayList) {
        System.out.println("+------+-----------+------------+-------------------+--------------+-------------+");
        System.out.println("| No.  | Order ID  | Order Date |   Customer Name   | Flower Count | Order Total |");
        System.out.println("+------+-----------+------------+-------------------+--------------+-------------+");

        int count = 1;
        double totalOrderTotal = 0;
        int totalFlowerCount = 0;

        for (Order o : displayList) {
            String formattedOrderDate = o.getOrderHeader().getOrderDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int flowerCount = o.getOrderDetails().stream().mapToInt(OrderDetail::getQuantity).sum();
            totalFlowerCount += flowerCount;
            String line = String.format("| %-4d | %-9s | %-10s | %-17s | %-12d | $%-10.2f |",
                    count,
                    o.getOrderHeader().getOrderID(),
                    formattedOrderDate,
                    o.getOrderHeader().getCustomerName(),
                    flowerCount,
                    calculateTotalOrderCost(o));
            System.out.println(line);
            totalOrderTotal += calculateTotalOrderCost(o);
            count++;
        }

        System.out.println("+------+------------+-----------+-------------------+--------------+-------------+");
        System.out.println(String.format("| %-4s | %-9s | %-11s| %-17s | %-12d | $%-10.2f |",
                "", "Total", "", "", totalFlowerCount, totalOrderTotal));
        System.out.println("+------+------------+-----------+-------------------+--------------+-------------+");

    }

    private double calculateTotalOrderCost(Order order) {
        double total = 0;
        for (OrderDetail detail : order.getOrderDetails()) {
            total += detail.getFlowerCost();
        }
        return total;
    }

    public void sortAndDisplayOrders() {
        List<Order> displayList = new ArrayList<>(this.orders);

        try {
            System.out.println("Select sorting field:");
            System.out.println("1. Order ID");
            System.out.println("2. Order Date");
            System.out.println("3. Customer Name");
            System.out.println("4. Order Total");
            int sortFieldChoice = Utils.getInt("Enter your choice: ", 1, 4);

            String sortField;
            switch (sortFieldChoice) {
                case 1:
                    sortField = "order id";
                    break;
                case 2:
                    sortField = "order date";
                    break;
                case 3:
                    sortField = "customer name";
                    break;
                case 4:
                    sortField = "order total";
                    break;
                default:
                    System.out.println("Invalid choice. Sorting by Order ID.");
                    sortField = "order id";
                    break;
            }

            System.out.println("Select sort order:");
            System.out.println("1. Ascending (ASC)");
            System.out.println("2. Descending (DESC)");
            int sortOrderChoice = Utils.getInt("Enter your choice: ", 1, 2);

            String sortOrder = (sortOrderChoice == 1) ? "ASC" : "DESC";

            Comparator<Order> comparator = createComparator(sortField, sortOrder);
            displayList.sort(comparator);

            System.out.println("LIST ALL ORDERS");
            display(displayList);
        } catch (Exception e) {
            System.out.println("An error occurred while processing your input. Please try again.");
        }
    }

    private Comparator<Order> createComparator(String sortField, String sortOrder) {
        Comparator<Order> comparator;

        switch (sortField) {
            case "order id":
                comparator = Comparator.comparing(o -> o.getOrderHeader().getOrderID());
                break;
            case "order date":
                comparator = Comparator.comparing(o -> o.getOrderHeader().getOrderDate());
                break;
            case "customer name":
                comparator = Comparator.comparing(o -> o.getOrderHeader().getCustomerName());
                break;
            case "order total":
                comparator = Comparator.comparingDouble(this::calculateTotalOrderCost);
                break;
            default:
                System.out.println("Invalid sort field. Sorting by order id.");
                comparator = Comparator.comparing(o -> o.getOrderHeader().getOrderID());
                break;
        }

        if (sortOrder.equals("DESC")) {
            comparator = comparator.reversed();
        }

        return comparator;
    }

    public boolean saveDataOrder() {
        try ( ObjectOutputStream orderOutputStream = new ObjectOutputStream(new FileOutputStream("orders.dat"))) {
            orderOutputStream.writeObject(orders);
            System.out.println("Data saved successfully.");
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred while saving data:");
            e.printStackTrace();
            return false;
        }
    }

    public boolean loadDataOrder() {
        try ( ObjectInputStream orderInputStream = new ObjectInputStream(new FileInputStream("orders.dat"))) {
            orders = (List<Order>) orderInputStream.readObject();
            System.out.println("LOADING...");
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("An error occurred while loading data:");
            e.printStackTrace();
            return false;
        }
    }

}
