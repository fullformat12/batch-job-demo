package bimatlaptrinh.com.batch.writer;

import bimatlaptrinh.com.batch.model.User;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserExcelWriter implements ItemWriter<User> {

    private final List<User> buffer = new ArrayList<>();
    @Override
    public void write(Chunk<? extends User> chunk) throws Exception {
        buffer.addAll(chunk.getItems());
    }

    public void writeToExcel() throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("NAME");
        header.createCell(2).setCellValue("EMAIL");

        int rowIndex = 1;
        for (User user : buffer) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getName());
            row.createCell(2).setCellValue(user.getEmail());
        }

        File file = new File("output/users.xlsx");

        // Tạo thư mục cha nếu chưa có (output)
        file.getParentFile().mkdirs();
        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);
        fos.close();
        workbook.close();
    }
}
