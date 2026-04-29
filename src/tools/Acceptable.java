/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package tools;

/**
 *
 * @author khach
 */
public interface Acceptable {
   
    // ===================== ID VALIDATION =====================

    String HOME_ID_VALID = "^HS\\d{4}$";
    public final String TOUR_ID_VALID = "^T\\d{5}$";
    public final String BOOKING_ID_VALID = "^B\\d{5}$";
    public final String NAME_VALID = "^.{2,50}$";
    public final String TIME_VALID = "^.{1,100}$";
    public final String POSITIVE_INT_VALID = "^[1-9]\\d*$";
    public final String PHONE_VALID = "^0\\d{9}$";
    public final String DATE_VALID =
            "^(0[1-9]|[12][0-9]|3[01])/"
          + "(0[1-9]|1[0-2])/"
          + "\\d{4}$";
    public final String BOOLEAN_VALID = "^(true|false|TRUE|FALSE)$";

    
    /**
     * Kiểm tra tính hợp lệ của dữ liệu đầu vào dựa vào một pattern regex
     * @param data chuỗi dữ liệu cần kiểm tra
     * @param pattern regex pattern để kiểm tra
     * @return true nếu dữ liệu hợp lệ (khớp với pattern), false nếu không
     */
    public static boolean isValid(String data, String pattern) {
        return data.matches(pattern);
    }
}
