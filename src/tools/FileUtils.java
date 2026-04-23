package tools;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for reading/writing data files (CSV, UTF-8)
 * Refactored to use generic functional interfaces to avoid code duplication.
 */
public class FileUtils {

    /**
     * Functional interface for parsing a String array into an object T.
     */
    @FunctionalInterface
    public interface LineParser<T> {
        T parse(String[] parts);
    }

    /**
     * Functional interface for formatting an object T into a String.
     */
    @FunctionalInterface
    public interface LineFormatter<T> {
        String format(T item);
    }

    /**
     * Đọc file tổng quát với tham số delimiter (kí tự phân cách, vd: "," hoặc "-")
     * và một hàm lambda (LineParser) để biến chuỗi thành Object.
     *
     * @param filePath  Đường dẫn file
     * @param delimiter Ký tự phân cách (ví dụ: "," hoặc "-")
     * @param parser    Hàm callback để parse mảng chuỗi thành Object T
     * @param <T>       Kiểu dữ liệu model (Homestay, Tour, Booking...)
     * @return Danh sách các object T đọc được
     */
    public static <T> List<T> readFromFile(String filePath, String delimiter, LineParser<T> parser) {
        List<T> list = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("[INFO] File not found: " + filePath);
            return list;
        }

    
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) // Skip empty or BOM
                    continue;

                String[] parts = line.split(delimiter, -1);
                try {
                    T item = parser.parse(parts);
                    if (item != null) {
                        list.add(item);
                    }
                } catch (Exception ex) {
                     System.out.println("[WARN] Skipping invalid line: " + line);
                }
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Reading file " + filePath + ": " + e.getMessage());
        }
        return list;
    }

    /**
     * Ghi file tổng quát. Sử dụng LineFormatter để chuyển dữ liệu T thành phân cách
     * String.
     * 
     * @param filePath  Đường dẫn file
     * @param list      Danh sách các phần tử cần lưu
     * @param formatter Hàm callback định dạng Object T thành 1 dòng text (đã có dấu
     *                  phân cách)
     * @param <T>       Kiểu dữ liệu model (Homestay, Tour, Booking...)
     */
    public static <T> void saveToFile(String filePath, List<T> list, LineFormatter<T> formatter) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            for (T item : list) {
                String line = formatter.format(item);
                if (line != null) {
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Saving file " + filePath + ": " + e.getMessage());
        }
    }
}
