package tools;

import business.HomestayManager;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import model.Booking;
import model.Tour;

/**
 * Centralized input handler with validation.
 */
public class Inputter {

    private Scanner sc;

    public Inputter() {
        sc = new Scanner(System.in);
    }

    // ================= BASIC STRING =================
    public String getString(String message) {
        System.out.print(message);
        return sc.nextLine().trim();
    }

/**
 * getStringWithEmpty: hàm này sử cho cho việc dùng Enter để skip các thông tin k muốn update
 * @param msg
 * @param regex
 * @param errorMsg
 * @return 
 */
     public String getStringWithEmpty(String message, String pattern) {
        String input;
        do {
            input = getString(message);
            if (!Acceptable.isValid(input, pattern) && !input.isEmpty()){
                System.out.println("Invalid format! Please re-enter.");
            }
        } while (!Acceptable.isValid(input, pattern) && !input.isEmpty());
        return input;
    }
    // ================= POSITIVE INTEGER =================
    public int getPositiveInt(String message) {
        String input;
        do {
            input = getString(message);
            if (!Acceptable.isValid(input, Acceptable.POSITIVE_INT_VALID)) {
                System.out.println("Must be a positive integer! Re-enter.");
            }
        } while (!Acceptable.isValid(input, Acceptable.POSITIVE_INT_VALID));
        return Integer.parseInt(input);
    }
    // ================= DATE =================
    public LocalDate getDate(String message) {
        String input;
        LocalDate date = null;

        do {
            input = getString(message);

            if (!Acceptable.isValid(input, Acceptable.DATE_VALID)) {
                System.out.println("Wrong format (dd/MM/yyyy)! Re-enter.");
                continue;
            }

            try {
                date = tools.DateUtils.tryParse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date! Re-enter.");
            }

        } while (date == null);

        return date;
    }

/**
 * 
 * @param message
 * @return 
 */
    public boolean getYesNo(String message) {
        String input;
        do {
            input = getString(message + " (Y/N): ");
            if (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N")) {
                System.out.println("Please enter Y or N.");
            }
        } while (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N"));

        return input.equalsIgnoreCase("Y");
    }
   /**
     * Nhập và kiểm tra dữ liệu, nhập lại nếu không khớp với pattern
     * @param mess thông báo hiển thị trên màn hình
     * @param pattern regex pattern để kiểm tra dữ liệu
     * @return chuỗi dữ liệu hợp lệ
     */
    public String inputAndLoop(String mess, String pattern){
        boolean more = true; String result = "";
        do {            
           result = getString(mess);
           more = !Acceptable.isValid(result, pattern);
            if ( more ) {
                System.out.println("Data is invalid ! Re_Enter...Please!!");            
            }
        } while (more);
        return result;
    }
/**
 * Hàm này dùng để lấy thông tin của Tour
 * @param hm
 * @return Tour
 */
    public Tour getTourInfo(HomestayManager hm) {
        
     String tourID = inputAndLoop("Enter TourId:   ", Acceptable.TOUR_ID_VALID);
     String tourName = inputAndLoop("Enter TourName:   ", Acceptable.NAME_VALID);
     String time = inputAndLoop("Enter Time:  ", Acceptable.TIME_VALID);
     int price = Integer.parseInt(inputAndLoop("Enter Price:   ", Acceptable.POSITIVE_INT_VALID));
     String homeID = inputAndLoop("Enter HomeID:    ",Acceptable.HOME_ID_VALID);
     LocalDate departureDate = getDate("Enter departureDate:   ");
     LocalDate endDate = getDate("Enter endDate:   ");
     int numberTourist = Integer.parseInt(inputAndLoop("Enter numberTourist:   ", Acceptable.POSITIVE_INT_VALID));
     //Lấy homeCapacity
     int homeCapacity = 0;
          if (hm.checkHomeID(homeID)) {
               homeCapacity = hm.searchById(homeID).getMaximumCapacity();
            }
        
        
       return new Tour(tourID, tourName, time, price, homeID, departureDate, endDate, numberTourist, homeCapacity);
    }
    /**
     * 
     * @return 
     */
    public Booking getBookingInfo(){

     String bookingID = inputAndLoop("Enter bookingID ( must follow the format “B00000”): ", Acceptable.BOOKING_ID_VALID);
     String fullName = inputAndLoop("Enter fullName:  ", Acceptable.NAME_VALID);
     String tourID = inputAndLoop("Enter TourId:  ", Acceptable.TOUR_ID_VALID);
     LocalDate bookingDate = getDate("Enter bookingDate:  " );
     String phone = inputAndLoop("Enter Phone:  ", Acceptable.PHONE_VALID);

        return new Booking(bookingID, fullName, tourID, bookingDate, phone);
    }

    

}