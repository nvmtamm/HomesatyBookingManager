package business;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import model.Homestay;
import tools.FileUtils;

/**
 * Manager for Homestay entities.
 * Extends ArrayList<Homestay> and implements Workable<Homestay>.
 */
public class HomestayManager extends ArrayList<Homestay> implements Workable<Homestay> {
   public static final String TABLE_HEADER =
        String.format("%-7s | %-25s | %-5s | %-70s | %-6s",
                "ID", "Home Name", "Room", "Address", "Cap")
        + "\n------------------------------------------------------------------------------------------------------------------------------";

    public static final String TABLE_FOOTER =
        "------------------------------------------------------------------------------------------------------------------------------";


    private String pathFile;
    private boolean isSaved;

    public HomestayManager() {
    }

    
    public HomestayManager(String pathFile) {
        super();
        this.pathFile = pathFile;
        this.isSaved = true;
        readFromFile(pathFile);
    }
/**
 * Read from file
 * @param pathFile 
 */
    @Override
    public void readFromFile(String pathFile) {
        this.clear();
        List<Homestay> loaded = FileUtils.readFromFile(pathFile, "-", parts -> {
            if (parts.length < 5){
                   System.out.println("lỗi ");
                return null;
            }
         
            try {
                return new Homestay(
                        parts[0].trim(), parts[1].trim(), parts[2].trim(),
                        parts[3].trim(), Integer.parseInt(parts[4].trim()));
            } catch (Exception e) {
                return null;
            }
        });
        if (loaded != null) {
            this.addAll(loaded);
        }
    }

    public void saveToFile() {
        FileUtils.saveToFile(pathFile, this, h -> h.getHomeID() + "," + h.getHomeName() + "," +
                h.getRoomNumber() + "," + h.getAddress() + "," + h.getMaximumCapacity());
        isSaved = true;
    }

     @Override
    /**
     * Hiển thị toàn bộ thông tin của tất cả khách hàng trong danh sách
     */
    public void showAll() {
        showAll(this);
    }
    
    /**
     * Hiển thị thông tin khách hàng từ một collection được cung cấp
     * @param l danh sách khách hàng cần hiển thị
     */
    public void showAll ( Collection<Homestay> l){
        System.out.println(TABLE_HEADER);
        for (Homestay i : l) {
            System.out.println(i);
            
        }
        System.out.println(TABLE_FOOTER);
        
    }
/**
 * Check Mangr xem homeID có tồn tại trong mảng k
 * @param id
 * @return 
 */ 
    public boolean checkHomeID(String id){
        
        for (Homestay h : this) {
            if (h.getHomeID().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public void addNew(Homestay h) {
        this.add(h);
        isSaved = false;
    }



    @Override
    public void delete(Homestay h) {
        if (this.remove(h)) {
            isSaved = false;
        } else {
            System.out.println("[WARN] Homestay not found: " + h.getHomeID());
        }
    }

    @Override
    public Homestay searchById(String id) {
        for (Homestay h : this) {
            if (h.getHomeID().equalsIgnoreCase(id))
                return h;
        }
        return null;
    }


  
    public boolean isSaved() {
        return isSaved;
    }
}
