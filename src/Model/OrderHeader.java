
package Model;

import java.time.LocalDate;

/**
 *
 * @author Admin
 */
public class OrderHeader {

    private String orderID;
    private LocalDate orderDate;
    private String customerName;

    public OrderHeader(String orderID, LocalDate orderDate, String customerName) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.customerName = customerName;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

}
