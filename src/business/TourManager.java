package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import model.Tour;
import tools.Acceptable;
import tools.DateUtils;
import tools.FileUtils;

/**
 * Manager for Tour entities.
 * Features: add, update (only if not booked), search, list/filter/sort.
 */
public class TourManager extends ArrayList<Tour> implements Workable<Tour> {
    // ===== TABLE HEADER =====
    public static final String TABLE_HEADER = String.format(
            "%-8s |%-8s | %-18s | %-15s | %-6s | %-12s | %-12s | %-8s | %-8s",
            "ID", "HomeID", "Tour Name", "Time", "Price", "Departure", "End_date", "Guest", "Booking")
            + "\n----------------------------------------------------------------------------------------------------------------------";
    // ===========TABLE FOOTER ====
    public static final String TABLE_FOOTER = "----------------------------------------------------------------------------------------------------------------------";

    private String pathFile;
    private boolean isSaved;

    public TourManager() {
    }

    public TourManager(String pathFile) {
        super();
        this.pathFile = pathFile;
        this.isSaved = true;
        this.readFromFile(pathFile);
    }

    // =============== setIsSaved() =================
    public void setIsSaved(boolean b) {
        this.isSaved = b;
    }

    @Override
    public void readFromFile(String pathFile) {
        this.clear();
        List<Tour> loaded = FileUtils.readFromFile(pathFile, ",", parts -> {
            if (parts.length < 9) {
                // System.out.println("[DEBUG] Invalid parts count: " + parts.length + " for
                // path: " + pathFile);
                return null;
            }

            try {
                return new Tour(
                        parts[0].trim(), parts[1].trim(), parts[2].trim(),
                        Integer.parseInt(parts[3].trim()),
                        parts[4].trim(),
                        tools.DateUtils.parse(parts[5].trim()),
                        tools.DateUtils.parse(parts[6].trim()),
                        Integer.parseInt(parts[7].trim()),
                        Boolean.parseBoolean(parts[8].trim()));
            } catch (Exception e) {
                // System.out.println("[ERROR] Failed to parse tour line: " + String.join(",",
                // parts));
                return null;
            }
        });
        if (loaded.isEmpty()) {
            System.out.println("File rổng");
        }
        if (loaded != null) {
            this.addAll(loaded);
        }
    }

    public void saveToFile() {
        FileUtils.saveToFile(pathFile, this, t -> t.getTourID() + "," + t.getTourName() + "," + t.getTime() + "," +
                t.getPrice() + "," + t.getHomeID() + "," +
                tools.DateUtils.format(t.getDepartureDate()) + "," +
                tools.DateUtils.format(t.getEndDate()) + "," +
                t.getNumberTourist() + "," + t.isIsBooked());
        isSaved = true;
    }
/**
 * 
 */
    @Override
    public void showAll() {
        showAll(this);
    }

    /**
     * Hiển thị thông tin khách hàng từ một collection được cung cấp
     * 
     * @param l danh sách khách hàng cần hiển thị
     */
    public void showAll(Collection<Tour> l) {
        System.out.println(TABLE_HEADER);
        for (Tour i : l) {
            System.out.println(i);

        }
        System.out.println(TABLE_FOOTER);

    }

    /**
     * Check mảng xem có tồn tại tourId này k: kiểm tra tính unique của TourId
     * 
     * @param tourId
     * @return
     */
    public boolean existsById(String id) {
        return searchById(id) != null;
    }

    /**
     * 
     * @return
     */
    public boolean isSaved() {
        return isSaved;
    }

    /**
     * 
     * @param id
     * @return
     */
    @Override
    public Tour searchById(String id) {
        for (Tour t : this) {
            if (t.getTourID().equalsIgnoreCase(id))
                return t;
        }
        return null;
    }
    /**
     * Add new tour
     * 
     * @param t
     */
    @Override
    public void addNew(Tour t) {
        this.add(t);
        isSaved = false;
    }

    /**
     * Function 2: Update Tour
     * 
     * @param tourId
     * @param input
     */
    public void updateTourInfo(Tour t, tools.Inputter input) {

        // tiến hành update allow updating tourName, time, price, homeID,
        // departure_date, end_date,
        // number_Tourist, bookingflag

        // 1. Update TourName
        String tourName = input.getStringWithEmpty("Nhập tourName update, (Enter to skip)", Acceptable.NAME_VALID);
        if (!tourName.isEmpty()) {
            t.setTourName(tourName);
            System.out.println("Sucessfully!");
        }
        // 2.Update Time
        String time = input.getStringWithEmpty("Nhập Time update, (Enter to skip)", Acceptable.TIME_VALID);
        if (!time.isEmpty()) {
            t.setTime(time);
            System.out.println("Sucessfully!");
        }
        // 3.Update Price
        String price = input.getStringWithEmpty("Nhập Price update, (Enter to skip):   ",
                Acceptable.POSITIVE_INT_VALID);
        if (!price.isEmpty()) {
            t.setPrice(Integer.parseInt(price));
            System.out.println("Sucessfully");
        }
        // 4.Update HomeID

        // Thiếu bước duyệt mảng xem có tồn tại homeId k
        String homeId = input.getStringWithEmpty("Nhập homeId update, ( Enter to skip):  ", Acceptable.HOME_ID_VALID);
        if (!homeId.isEmpty()) {
            t.setHomeID(homeId);
            System.out.println("Succesfully!");
        }
        // 5.Update departure_date
        LocalDate departure_date = DateUtils.tryParse(
                input.getStringWithEmpty("Nhập departure_date update,( Enter to skip):     ", Acceptable.DATE_VALID));
        if (departure_date != null) {
            if (!departure_date.isBefore(LocalDate.now())) {
                t.setDepartureDate(departure_date);
                System.out.println("Successfully!!");
            } else {
                System.out.println("Departure_date must be >= today, please.");
            }
        }
        // 6.Update end_date
        LocalDate newEndDate = DateUtils
                .tryParse(input.getStringWithEmpty("Enter new end date, ( Enter to skip):  ", Acceptable.DATE_VALID));
        if (newEndDate != null) {
            if (newEndDate.isAfter(departure_date)) {
                t.setEndDate(newEndDate);
                System.out.println("Successfully!!");
            } else {
                System.out.println("Departure_date must be >= departure_date, please.");
            }
        }
        // 7. Update number of tourists

        // Thiếu bước kiểm tra newTourists <= maxCapacity trong homeList
        String newTourists = input.getStringWithEmpty("Enter new Tourists, ( Enter to skip):  ",
                Acceptable.POSITIVE_INT_VALID);
        if (!newTourists.isEmpty()) {
            t.setNumberTourist(Integer.parseInt(newTourists));
            System.out.println("Successfully!!");
        }

        // 8. Update isBookingFlag
        String newBookingFlag = input.getStringWithEmpty("Enter new BookingFlag: Yes/No ( Enter to skip):  ",
                Acceptable.BOOLEAN_VALID);
        if (!newBookingFlag.isEmpty()) {
            t.setIsBooked(Boolean.parseBoolean(newBookingFlag));
            System.out.println("Successfully");
        }

    }

    /**
     * Function 3: List the tours with departure dates earlier than the current
     * date: No data input.
     * 
     * @return l
     */
    public List<Tour> listTourAfterCurrentDate() {
        List l = new ArrayList();
        // Find the Tour after current days
        for (Tour t : this) {
            if (t.getDepartureDate().isBefore(LocalDate.now())) {
                l.add(t);
            }
        }
        return l;
    }

    @Override
    public void delete(Tour t) {
        if (this.remove(t)) {
            isSaved = false;
        } else {
            System.out.println("[WARN] Tour not found: " + t.getTourID());
        }
    }
 
/**
 * Hàm này dùng để check Over departure_Date --> sử dụng cho Funtion 6: Update BookingInfo()
 * @param tourID
 * @param newBooking_date
 * @return 
 */
    boolean checkOver_departure_date(String tourID, LocalDate date) {
        boolean over = false;
        Tour t = this.searchById(tourID);
        
        
        if (date.isAfter(t.getDepartureDate())) {
            return true;
        }
        return over;
    }   

/**
 * Cập nhật số lượng khách tham gia Tour.
 * @param tId
 * @param delta 
 */
    public void updateTouristCount(String tId, int delta) {
        Tour t = searchById(tId);
        t.checkBookingStatus();
        if (t != null)
            t.setNumberTourist(t.getNumberTourist()+ delta);

    }

}
