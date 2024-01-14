package Model;

public class OrderDetail {
    private String orderDetailId;
    private final Flower flower;  // Reference to the Flower object
    private int quantity;
    private double flowerCost;

    public OrderDetail(String orderDetailId, Flower flower, int quantity, double flowerCost) {
        this.orderDetailId = orderDetailId;
        this.flower = flower;
        this.quantity = quantity;
        this.flowerCost = flowerCost;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public Flower getFlower() {
        return flower;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getFlowerCost() {
        return flowerCost;
    }
}
