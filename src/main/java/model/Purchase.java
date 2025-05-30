package model;

import java.time.LocalDateTime;

public class Purchase {
    private int userId;
    private int tourId;
    private int quantity;
    private LocalDateTime purchaseDate;

    public Purchase() {}

    public Purchase(int userId, int tourId, int quantity, LocalDateTime purchaseDate) {
        this.userId = userId;
        this.tourId = tourId;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
    }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public int getTourId() { return tourId; }

    public void setTourId(int tourId) { this.tourId = tourId; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public LocalDateTime getPurchaseDate() { return purchaseDate; }

    public void setPurchaseDate(LocalDateTime purchaseDate) { this.purchaseDate = purchaseDate; }
}
