package model;

import java.time.LocalDate;


/**
 * Tour entity
 * tourID: T#####, tourName, time, price, homeID, departureDate, endDate,
 * numberTourist, bookingFlag
 */
public class Tour {


    private String tourID;
    private String tourName;
    private String time;
    private int price;
    private String homeID;
    private LocalDate departureDate;
    private LocalDate endDate;
    private int numberTourist;
    private boolean isBooked;
    private int homeCapacity;

    public Tour() {
    }

    public Tour(String tourID, String tourName, String time, int price, String homeID, LocalDate departureDate, LocalDate endDate, int numberTourist, boolean isBooked) {
        this.tourID = tourID;
        this.tourName = tourName;
        this.time = time;
        this.price = price;
        this.homeID = homeID;
        this.departureDate = departureDate;
        this.endDate = endDate;
        this.numberTourist = numberTourist;
        this.isBooked = isBooked;
 
    }

    public Tour(String tourID, String tourName, String time, int price, String homeID, LocalDate departureDate, LocalDate endDate, int numberTourist, int homeCapacity) {
        this.tourID = tourID;
        this.tourName = tourName;
        this.time = time;
        this.price = price;
        this.homeID = homeID;
        this.departureDate = departureDate;
        this.endDate = endDate;
        this.numberTourist = numberTourist;
        this.homeCapacity = homeCapacity;
        checkBookingStatus();
    }


  public void checkBookingStatus() {
        // Logic: Nếu khách >= sức chứa -> isBooked = TRUE, ngược lại FALSE
        this.isBooked = (numberTourist >= homeCapacity);
        System.out.println(homeCapacity);
    }
    

    // ===== Getters & Setters =====
    public String getTourID() {
        return tourID;
    }

    public void setTourID(String tourID) {
        this.tourID = tourID;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getHomeID() {
        return homeID;
    }

    public void setHomeID(String homeID) {
        this.homeID = homeID;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getNumberTourist() {
        return numberTourist;
    }

    public void setNumberTourist(int numberTourist) {
        this.numberTourist = numberTourist;
        checkBookingStatus(); // Tự động cập nhật Booked khi số lượng thay đổi
    }

    public boolean isIsBooked() {
        return isBooked;
    }

    public void setIsBooked(boolean isBooked) {
        this.isBooked = isBooked;
    }



    // ===== Tổng tiền =====
    public int getTotalAmount() {
        return price * numberTourist;
    }
    




    // ===== toString() FORMAT =====
   @Override
    public String toString() {
        return String.format("%-8s |%-8s | %-18s | %-15s | %-6d | %-12s | %-12s | %-8d | %-8b",
            tourID,
            homeID,
            tourName,
            time,
            price,
            departureDate,
            endDate,
            numberTourist,
            isBooked
    );
}


}