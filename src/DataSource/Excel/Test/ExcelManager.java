package DataSource.Excel.Test;

import jxl.*;
import jxl.write.*;

import java.io.*;

import java.util.*;

public class ExcelManager {
    public String file_name;
    public Map<String, ArrayList<ArrayList<String>>> excel_data;

    public ExcelManager(String f_name) {
        file_name = f_name;
    }

    public boolean checkFileExists () {
        File file = new File(file_name);
        if (file.exists() == false) {
            return false;
        }
        else return true;
    }

    public boolean copyExcelToMemory(
                  ArrayList<String> sheet_names
                , int[] get_row_indexes
                , int[] get_column_indexes) {
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File(file_name));
            excel_data = new HashMap<String, ArrayList<ArrayList<String>>>();
            boolean copyTable = false;
            for(int s = 0; s < workbook.getNumberOfSheets(); s++) {
                Sheet sheet = workbook.getSheet(s);
                
                if (sheet_names != null) {
                    for(String tbl : sheet_names) {
                        copyTable = false;
                        if(tbl.toUpperCase().equals(sheet.getName().toUpperCase())) {
                            copyTable = true;
                            break;
                        }
                    }
                    if(sheet_names.size() == 0) copyTable = true;
                }
                else copyTable = true;
                if (copyTable == true) {
                    ArrayList<ArrayList<String>> row = new ArrayList<ArrayList<String>>();
                    for (int r = 0; r < sheet.getRows(); r++) {
                        boolean copyRow = false;
                        if (get_row_indexes == null) copyRow = true;
                        else {
                            for (int i : get_row_indexes) {
                                if (i == r) {
                                    copyRow = true;
                                    break;
                                }
                            }
                        }
                        if (copyRow) {
                            ArrayList<String> column = new ArrayList<String>();
                            for(int c = 0; c < sheet.getColumns(); c++) {
                                boolean copyColumn = false;
                                if (get_column_indexes == null) copyColumn = true;
                                else {
                                    for (int i : get_column_indexes) {
                                        if (i == c) {
                                            copyColumn = true;
                                            break;
                                        }
                                    }
                                }
                                if (copyColumn) {
                                    column.add(sheet.getCell(c, r).getContents());
                                }
                            }
                            row.add(column);
                        }
                    }
                    excel_data.put(sheet.getName(), row);
                }
            }
        } catch (Exception e) {
            return false;
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
        return true;
    }
}