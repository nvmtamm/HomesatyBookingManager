package dispatcher;

import business.BookingManager;
import business.HomestayManager;
import business.TourManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.Booking;
import model.Tour;
import tools.Acceptable;
import tools.Inputter;

/**
 * Main entry point for Homestay Booking Management System.
 * Menu-driven application using Inputter for safe input.
 */
public class main {

    public static void main(String[] args) {
        HomestayManager hm = new HomestayManager("data/Homestays.txt");
        TourManager tm = new TourManager("data/Tours.txt");
        BookingManager bm = new BookingManager("data/Bookings.txt");
        Inputter input = new Inputter();

        hm.showAll();
        tm.showAll();
        bm.showAll();

        int choice;
        do {
            printMenu();
            choice = input.getPositiveInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    System.out.println("Bạn đã chọn Add A New Tour:    ");
                    Tour t = input.getTourInfo(hm);
                    if (tm.existsById(t.getTourID())) {
                        System.out.println("      Lỗi: đã tồn tại tourID này!!!");
                        break;
                    }
                    if (!hm.checkHomeID(t.getHomeID())) {
                        System.out.println("     Lỗi: không tồn tại homeID này!!!");
                        break;
                    }
                    if (t.getDepartureDate().isAfter(t.getEndDate())) {
                        System.out.println("      Lỗi: departureDate phải trước hoặc bằng endDate.");
                        break;
                    }

                    if (t.getNumberTourist() > hm.searchById(t.getHomeID()).getMaximumCapacity()) {
                        System.out.println("    Lỗi: số lượng khách quá lớn:");
                        break;
                    }
                    tm.addNew(t);

                    System.out.println("Thêm thành công.");

                    break;
                case 2:

                    System.out.println("Bạn đã chọn update Tour Info:   ");
                    Tour tour = new Tour();
                    String tourId = input.inputAndLoop("Nhập tourId muốn Update:   ", Acceptable.TOUR_ID_VALID);
                    if (!tm.existsById(tourId)) {
                        System.out.println("This Tour does not exist....");
                        break;
                    } else {
                        tour = tm.searchById(tourId);
                    }

                    tm.updateTourInfo(tour, input);
                    System.out.println("Update ");
                    tm.setIsSaved(false);
                    break;
                case 3:
                    System.out.println(
                            "You chosen:List of Tours with departure dates earlier than the current date ");
                    List list = tm.listTourAfterCurrentDate();
                    if (!list.isEmpty()) {
                        tm.showAll(list);
                    }else{
                        System.out.println("Not find any Tours with dates earlier than the current date.");
                    }
                    break;
                case 4:
                    System.out.println("You chosen: List the total booking amount for tours");
                    List<Tour> filter = new ArrayList<>();

                    // 1. Duyệt mảng lọc dữ liệu (Lấy các tour sau ngày hiện tại)
                    for (Tour i : tm) {
                        if (i.getDepartureDate().isAfter(LocalDate.now())) {
                            filter.add(i);
                        }
                    }

                    // 2. Kiểm tra danh sách sau khi lọc xong
                    if (filter.isEmpty()) {
                        System.out.println("No tours found after current date.");
                    } else {
                        // 3. Sắp xếp giảm dần (Descending) theo TotalAmount
                        filter.sort(Comparator.comparingInt(Tour::getTotalAmount).reversed());

                        // 4. Hiển thị kết quả
                        tm.showAll(filter);
                    }
                    break;
                case 5:
                    System.out.println("You chosen: Add A New Booking.");
                    Booking b = input.getBookingInfo();
                    

                    if (tm.existsById(b.getTourID())) {
                        Tour t5 = tm.searchById(b.getTourID());
                        if (!bm.existsById(b.getBookingID())) {
                            if (b.getBookingDate().isBefore(t5.getDepartureDate())) {
                                
                                bm.addNew(b);
                                tm.updateTouristCount(b.getTourID(), 1);
                            } else {
                                System.out.println("[error]: The bookingDate musr be after Depaeture day:"
                                        + t5.getDepartureDate());
                            }
                        } else {
                            System.out.println("[error]: This bookingID already exist!");
                        }

                    } else {
                        System.out.println("[error]: This TourId does not exist!!!");
                        break;
                    }

                    break;
                case 6:
                    /*
                        Update a Booking by ID: Accept bookingID. If the booking does not exist, show: “This 
                    Booking does not exist!”. Otherwise, allow updating fullName, tourID, booking_date, and 
                    phone. Empty input means skipping the update for that field.
                    */
                    System.out.println("You chosen: Update a Booking by ID.  ");
                    // Lấy Bookingid và check exist()
                    String bookingId = input.inputAndLoop("Enter BookingID wants to Update:  ", Acceptable.BOOKING_ID_VALID);
                    if (bm.existsById(bookingId)) {
                        bm.updateInfo(tm,input,bookingId);
                        System.out.println("Update successfully!");
                        //thay đổi isSaved().
                        bm.setIsSaved(false);
                    }else{
                        System.out.println("This booking does not exists!!!");
                    }
                    break;
                case 7:
                    /*
                        Remove a Booking by ID: Accept bookingID. If the Booking does not exist, show: “This
                    booking does not exist!”. Otherwise, remove it.
                    After Removed, the booking and number_Tourist field in the tour list will be able to updated.
                    */
                    System.out.println("You chosen: Remove a Booking by ID");
                    String BookingId = input.inputAndLoop("Enter Booking Id to remove: ", Acceptable.BOOKING_ID_VALID);
                    
                    if (bm.existsById(BookingId)){
                        Booking bk = bm.searchById(BookingId);
                            // Giảm số khách của Tour khi hủy booking
    
                            bm.remove(bk);
                            bm.setIsSaved(false);
                            tm.updateTouristCount(bk.getTourID(), -1);
                            tm.searchById(bk.getTourID()).checkBookingStatus();
                            System.out.println("Removed.");
        
                    }else{
                        System.out.println("This booking does not exist!!");
                    }
                    break;
                case 8:
                    /*
                        List all Booking by the fullName or a partial fullName: Accept fullName or partial 
                    fullName (e.g., Binh); display all matching bookings in the table format.
                    */
                    System.out.println("You chosen: List all Booking by the fullName or a partial fullName.");
                    String name = input.inputAndLoop("Enter fullName or a partial fullName: ", Acceptable.NAME_VALID);
                    List l= bm.searchByName(name) ;
                    if (!l.isEmpty()) {
                        bm.showAll(l);
                    }else{
                        
                        System.out.println("[error]:Dont find any Booking with this name..");
                    }
                    
                    break;
                case 9:
                     System.out.println("You chosen: 9. Statistics: Total Tourists Per Homestay.");
                     bm.statisticsTouristsByHomestay(tm, hm);
                     // vòng for 1: duyệt qua mảng Homestay.
                     /*
                        - từ TourId lấy được HomestayId 
                        - duyệt mảng Homestay lấy được HomeStay Name.
                        - biến int  total_Tourist để tổng hợp các  Tourist từ Tour.
                     */
                     // vòng for 2: duyệt qua mảng Booking
                     /*
                        - lấy được TourId, numberTourist.
                        - từ TourId lấy được HomestayId 
                        - duyệt mảng Homestay lấy được HomeStay Name.
                        - biến int Count Tourist để 
                     */
                     
                    break;
                case 10:
                    System.out.println("You chosen: Exit.");
                    boolean anyUnsaved = !tm.isSaved() || !bm.isSaved();
                    if (anyUnsaved) {
                        boolean save = input.getYesNo("There are unsaved changes. Save before exit?");
                        if (save) {
                                tm.saveToFile();
                                bm.saveToFile();
                                System.out.println("Data saved.");
                        }
                    }
                        System.out.println("Goodbye!");
                    break;
                case 11:
                    /*
                    In ra cacs khach hang dat Tour thuong xuyen
                    chi dat 2 lan
                    
                    */
                    System.out.println("In ra khách hàng đặt hàng đúng 2 lần:");
                    BookingManager findTour  = new BookingManager();
                    
                    //duyệ mảng bm lần 1 lấy booking1
                    for ( Booking b1 : bm) {
                        int count = 0;
                        //duyet mang lần 2 lấy booking b2: check b1 xuât hien bao nhieu lần
                        for (Booking b2 : bm) {
                            if (b1.getFullName().equalsIgnoreCase(b2.getFullName())) {
                                count++;
                            }
                        }
                        //duyệt mảng bm lần 3 add() dữ liệu vs count ==2
                        for (Booking b3 : bm) {
                            if (count==2 && !findTour.existsByName(b1.getFullName())) {
                                findTour.add(b1);
                            }
                        }
                    }
                    System.out.println("Kết quả:");
                    for (Booking booking : findTour) {
                        System.out.println("Tên KH: "+ booking.getFullName() + "   SDT:" + booking.getPhone());
                    }
                    break;
                
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 10);
    }

    // =================================================================
    // MENU
    // =================================================================
    private static void printMenu() {
        System.out.println("\n====== HOMESTAY BOOKING MANAGEMENT ======");
        System.out.println(" 1. Add New Tour");
        System.out.println(" 2. Update Tour By ID");
        System.out.println(" 3. List Tours After Current Date ");
        System.out.println(" 4. List the total booking amount for tours.");
        System.out.println(" 5. Add New Booking");
        System.out.println(" 6. Update a Booking by ID.");
        System.out.println(" 7. Remove a Booking by ID");
        System.out.println(" 8. Search Booking By Customer Name");
        System.out.println(" 9. Statistics: Total Tourists Per Homestay");
        System.out.println("10. Exit");
        System.out.println("=========================================");
    }

}