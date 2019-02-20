package com.example.sweater.Algorithms.ExcelExtract;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

// загружаем сырую таблицу
// подразумевается следующий формат:
// каждый столбец это вопрос, строка это респондент
// пересечение - ответ респондента на вопрос
public class ExcelReader {
    private final File filePath;
    private final static DataFormatter dataFormatter = new DataFormatter();

    public ExcelReader(String path) {
        filePath = new File(path);
    }

    public ArrayList<Responder> read() throws IOException {
        ArrayList<Responder> responders = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(filePath);

        // first sheet
        Sheet sheet = workbook.sheetIterator().next();

        Iterator<Row> rowIterator = sheet.rowIterator();
        rowIterator.next(); // skip header

        while (rowIterator.hasNext()) {
            Responder responder = new Responder();
            Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                responder.answers.add(dataFormatter.formatCellValue(cell));
            }
            responders.add(responder);
        }
        return responders;
    }

    // list all questions
    public ArrayList<Question> questions() throws IOException {
        ArrayList<Question> result = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(filePath);

        Sheet sheet = workbook.sheetIterator().next();
        for (Cell cell : sheet.getRow(0)) {
            result.add(new Question(dataFormatter.formatCellValue(cell)));
        }
        return result;
    }

}
