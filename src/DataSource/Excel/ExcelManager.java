package DataSource.Excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import DataSource.Excel.Objects.Excel;
import DataSource.Excel.Objects.ExcelSheet;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class ExcelManager {
    public void readExcel (String file_name) throws Exception {

        Workbook excel = Workbook.getWorkbook(new File(file_name));

        int number_of_sheets = excel.getNumberOfSheets();

        Excel imported_excel = new Excel(file_name);

        for (int sheet_idx = 0; sheet_idx < number_of_sheets; sheet_idx++) {            
            Sheet sheet = excel.getSheet(sheet_idx);

            ExcelSheet excel_sheet = new ExcelSheet(sheet.getName());

            excel_sheet.total_columns = sheet.getColumns();
            excel_sheet.total_rows = sheet.getRows();
            for (int row_idx = 0; row_idx < sheet.getRows(); row_idx++) {
                Cell[] row = sheet.getRow(row_idx);
                ArrayList<String> tmp = new ArrayList<String>();

                for (int i = 0; i < row.length; i++) {
                    tmp.add(row[i].getContents());
                }

                excel_sheet.rows.add(tmp);
            }

            imported_excel.sheets.add(excel_sheet);
        }
        excel.close();

        printExcel(imported_excel);

        writeExcel("newX.xls", imported_excel);
    }

    public void writeExcel (String file_name, Excel e) throws Exception {
        WritableWorkbook excel = Workbook.createWorkbook(new File (file_name));
        for (int i = 0; i < e.sheets.size(); i++) {
            excel.createSheet(e.sheets.get(i).sheet_name, i);
            WritableSheet sheet = excel.getSheet(i);
            ArrayList<ArrayList<String>> rows = e.sheets.get(i).rows;

            for (int r = 0; r < rows.size(); r++) {
                ArrayList<String> columns = rows.get(r);
                for (int c = 0; c < columns.size(); c++) {                    
                    Label label = new Label(c, r, columns.get(c));
                    sheet.addCell(label);
                }
            }
        } 
        excel.write();
        excel.close();
    }

    public void printExcel (Excel excel) {
        System.out.println("Excel name : " + excel.file_name);
        for (int sh = 0; sh < excel.sheets.size(); sh++) {
            ExcelSheet sheet = excel.sheets.get(sh);
            System.out.println("");
            System.out.println("   Sheet name : " + sheet.sheet_name);

            for (int r = 0; r < sheet.total_rows; r++) {
                System.out.println("      ");
                ArrayList<String> row = sheet.rows.get(r);
                for (int c = 0; c < row.size(); c++) {
                    System.out.print(row.get(c) + "   ");
                }
            }
            System.out.println("-------");
        }
    }
}
