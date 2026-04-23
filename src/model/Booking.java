package model;

import java.time.LocalDate;

/**
 * Booking entity
 * bookingID: B#####, fullName, tourID, bookingDate, phone
 */
public class Booking {

    private String bookingID;
    private String fullName;
    private String tourID;
    private LocalDate bookingDate;
    private String phone;

    public Booking() {
    }

    public Booking(String bookingID, String fullName, String tourID,
            LocalDate bookingDate, String phone) {
        this.bookingID = bookingID;
        this.fullName = fullName;
        this.tourID = tourID;
        this.bookingDate = bookingDate;
        this.phone = phone;
    }

    // ===== Getters & Setters =====

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTourID() {
        return tourID;
    }

    public void setTourID(String tourID) {
        this.tourID = tourID;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

@Override
    public String toString() {
        return String.format(
            "%-12s | %-25s | %-10s | %-15s | %-15s",
            bookingID,
            fullName,
            tourID,
            bookingDate,
            phone
    );
}
}
