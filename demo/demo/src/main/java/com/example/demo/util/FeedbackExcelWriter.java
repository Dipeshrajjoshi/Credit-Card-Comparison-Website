package com.example.demo.util;

import com.example.demo.model.Feedback;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FeedbackExcelWriter {
    private static final String FILE_PATH = "feedback_data.xlsx";

    public static void writeFeedbackToExcel(Feedback feedback) {
        try {
            Workbook workbook;
            Sheet sheet;

            File file = new File(FILE_PATH);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheetAt(0);
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Feedback");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Email");
                header.createCell(1).setCellValue("Mobile");
                header.createCell(2).setCellValue("Complaint");
            }

            int rowCount = sheet.getLastRowNum();
            Row row = sheet.createRow(++rowCount);

            row.createCell(0).setCellValue(feedback.getEmail());
            row.createCell(1).setCellValue(feedback.getMobile());
            row.createCell(2).setCellValue(feedback.getComplaint());

            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            workbook.write(fos);
            fos.close();
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
