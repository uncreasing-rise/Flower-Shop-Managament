/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class Order {
    private OrderHeader orderHeader;
    private List<OrderDetail> orderDetails;

    public Order(OrderHeader orderHeader) {
        this.orderHeader = orderHeader;
        this.orderDetails = new ArrayList<>();
    }

    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetails.add(orderDetail);
    }

    public OrderHeader getOrderHeader() {
        return orderHeader;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
}
