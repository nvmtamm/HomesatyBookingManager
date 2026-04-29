package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import model.Booking;
import model.Homestay;
import model.Tour;
import tools.Acceptable;
import tools.DateUtils;
import tools.FileUtils;
import tools.Inputter;


/**
 * Manager class for Booking entities.
 * Features: add (with tour validation), delete (reset tour bookingFlag),
 * update, search by name, statistics per homestay.
 */
public class BookingManager extends ArrayList<Booking> implements Workable<Booking> {
   //Fields
   private String pathFile;
   private boolean isSaved;
   public static final String TABLE_HEADER =
        String.format(
                "%-12s | %-25s | %-10s | %-15s | %-15s%n",
                "BookingID",
                "Full Name",
                "Tour ID",
                "Booking Date",
                "Phone"
        )
        + "-------------------------------------------------------------------------------";
   public static final String TABLE_FOOTER =
        "-------------------------------------------------------------------------------";

    public BookingManager() {
    }
   
  
   
   //Constructor
   public BookingManager(String pathFile) {
      super();
      this.pathFile = pathFile;
      this.isSaved = true;
      readFromFile(pathFile);
   }
/**
 * isSaved()
 * @return 
 */
     public boolean isSaved() {
      return isSaved;
   }

    public void setIsSaved(boolean isSaved) {
        this.isSaved = isSaved;
    }


 
/**
 * Read from file
 * @param pathFile 
 */
   @Override
   public void readFromFile(String pathFile) {
      this.clear();
      List<Booking> loaded = FileUtils.readFromFile(pathFile, ",", parts -> {
         if (parts.length < 5)
            return null;
         try {
            return new Booking(
                  parts[0].trim(), parts[1].trim(), parts[2].trim(),
                  tools.DateUtils.parse(parts[3].trim()),
                  parts[4].trim());
         } catch (Exception e) {
            return null;
         }
      });
      if (loaded != null) {
         this.addAll(loaded);
      }
   }
/**
 * Save to file
 */
   public void saveToFile() {
      FileUtils.saveToFile(pathFile, this, b -> b.getBookingID() + "," + b.getFullName() + "," +
            b.getTourID() + "," + tools.DateUtils.format(b.getBookingDate()) + "," +
            b.getPhone());
      isSaved = true;
   }

   @Override
   public void addNew(Booking b) {
      this.add(b);
      isSaved = false;
   }
   @Override
   public Booking searchById(String id) {
      for (Booking b : this) {
         if (b.getBookingID().equalsIgnoreCase(id))
            return b;
      }
      return null;
   }

   public boolean existsById(String id) {
      return searchById(id) != null;
   }
    public boolean existsByName(String name) {
        for (Booking b : this) {
            if (b.getFullName().equalsIgnoreCase(name)) {
                return  true;
            }
        }
      return false;
   }
       @Override
    public void showAll() {
       this.showAll(this);
    }
    public void showAll(Collection l){
        System.out.println(TABLE_HEADER);
        for (Object o : l) {
            System.out.println(o);
        }
        System.out.println(TABLE_FOOTER);
    }

/**
 * check Add Booking: hàm này giúp kiểm tra các điều kiện trước khi add 1 booking mới
 * @param b
 * @param tm
 * @return false : nếu k thỏa đk, in ra lỗi
 *         true : nếu thỏa
 *         
 */
   public boolean checkAddBooking(Booking b, TourManager tm) {
      // 1. Tour must exist
      Tour tour = tm.searchById(b.getTourID());
      if (tour == null) {
         System.out.println("[ERROR] Tour ID does not exist: " + b.getTourID());
         return false;
      }
      // 2. Tour must not already be booked
      if (tour.isIsBooked()) {
         System.out.println("[ERROR] Tour is already booked: " + b.getTourID());
         return false;
      }
      // 3. bookingDate must be before departureDate
      if (!b.getBookingDate().isBefore(tour.getDepartureDate())) {
         System.out.println("[ERROR] Booking date must be before departure date ("
               + tour.getDepartureDate() + ").");
         return false;
      }
      // 4. bookingID must not exist
      if (existsById(b.getBookingID())) {
         System.out.println("[ERROR] Booking ID already exists: " + b.getBookingID());
         return false;
      }
      return true;
   }

/**
 * Function 6: Update Booking Info:  fullName, tourID, booking_date, and phone.
 * @param tm
 * @param input
 * @param bookingId 
 */   
    public void updateInfo(TourManager tm, Inputter input, String bookingId) {
        Booking bookingUpdate = this.searchById(bookingId);
        Tour t = tm.searchById(bookingUpdate.getTourID());

        //1.Update Name
        String newName = input.getStringWithEmpty("Enter new Booking Name, ( Enter to skip): ", Acceptable.NAME_VALID);
        if (!newName.isEmpty()) {
            bookingUpdate.setFullName(newName);
            System.out.println("Successfully!!!");
        }
        
        //2.Update TourId
        String newTourId = input.getStringWithEmpty("Enter new TourId, (Enter to skip):  ", Acceptable.TOUR_ID_VALID);
        if (tm.existsById(newTourId) && !newTourId.isEmpty()) {
            bookingUpdate.setTourID(newTourId);
            System.out.println("Successfully!!!");
        }else{
            System.out.println("This TourID does not exist!!");
        }
        
        //3. Booking_date
        String  temp = input.getStringWithEmpty("Enter new Booking_date, (Enter to skip):  ",Acceptable.DATE_VALID);
        LocalDate newBooking_date = DateUtils.tryParse(temp);
            //check newBooking_date >= departure_date
            if (!temp.isEmpty()) {
                if (tm.checkOver_departure_date(bookingUpdate.getTourID(),newBooking_date)) {
                        bookingUpdate.setBookingDate(newBooking_date);
                        System.out.println("Successfully!");
            }else{
                System.out.println("[error]: new Booking_date must be before:" + t.getDepartureDate() );
            }
        }
        //4.Update Phone
        String newPhone = input.getStringWithEmpty("Enter new Phone, (Enter to skip):  ", Acceptable.PHONE_VALID);
        if ( !newPhone.isEmpty()) {
            bookingUpdate.setPhone(newPhone);
            System.out.println("Successfully!!!");
        }   
    }
/**
 * Funtion 8: List Booking searchs by fullName or partName.
 * @param name
 * @return 
 */
   public List<Booking> searchByName(String name) {
      List<Booking> result = new ArrayList<>();
      String lower = name.toLowerCase();
      for (Booking b : this) {
         if (b.getFullName().toLowerCase().contains(lower)) {
            result.add(b);
         }
      }
      return result;
   }
/**
 * Function 9 
 * @param tm
 * @param hm 
 */
public void statisticsTouristsByHomestay(TourManager tm, HomestayManager hm) {
      System.out.println("\n=== STATISTICS: Total Tourists Per Homestay ===");
      System.out.printf("%-8s | %-25s | %s%n", "HomeID", "Home Name", "Total Tourists");


      for (Homestay h : hm) {
         int totalTourists = 0;
         for (Booking b : this) {
            Tour t = tm.searchById(b.getTourID());
            if (t != null && t.getHomeID().equalsIgnoreCase(h.getHomeID())) {
               totalTourists += t.getNumberTourist();
            }
         }
         System.out.printf("%-8s | %-25s | %d%n",
               h.getHomeID(), h.getHomeName(), totalTourists);
      }

   }
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
    
    
    
    
    
    
   
   
   
   
   


   @Override
   public void delete(Booking b) {
      this.remove(b);
      isSaved = false;
   }


   }




 



   

