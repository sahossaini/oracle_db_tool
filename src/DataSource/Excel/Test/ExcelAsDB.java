package DataSource.Excel.Test;

import jxl.*;
import jxl.write.*;

import java.io.*;

import java.util.*;

public class ExcelAsDB {
    /**
     * How it will work: when ExcelAsDB will be initialized, a new blank excel
     * file will be created with name AppData
     * 
     * when a table needs to be created, a new sheet will be created in the excel
     * with that table name
     * 
     * rows will be returned in arraylist<arraylist<> string> fashion
     */

    String file_name = "AppData.xls";
    public Map<String, ArrayList<ArrayList<String>>> app_data;

    public ExcelAsDB(String _file_name) throws Exception {
        file_name = _file_name;
        File file = new File(file_name);
        if (file.exists() == false) {
            WritableWorkbook writable_workbook = null;
            try {
                writable_workbook = Workbook.createWorkbook(new File(file_name));
                writable_workbook.createSheet("sheet_1", 0);
                writable_workbook.write();
            } catch (Exception e) {
                throw e;
            } finally {
                if (writable_workbook != null) {
                    try {
                        writable_workbook.close();
                    } catch (Exception e) {
                        throw e;
                    }
                }
            }
        }
    }

    public boolean createTable(String table_name) {
        Workbook workbook = null;
        WritableWorkbook writable_workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File(file_name));
            if(workbook.getSheet(table_name) != null) {
                return false;
            }
            writable_workbook = Workbook.createWorkbook(new File(file_name), workbook);
            writable_workbook.createSheet(table_name, workbook.getNumberOfSheets());
            writable_workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    if(writable_workbook != null)
                        writable_workbook.close();
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public void deleteTable(String table_name) {
        Workbook workbook = null;
        WritableWorkbook writable_workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File(file_name));
            writable_workbook = Workbook.createWorkbook(new File(file_name), workbook);

            writable_workbook.removeSheet(Arrays.asList(workbook.getSheetNames()).indexOf(table_name));
            writable_workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    writable_workbook.close();
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void insert(String table_name, ArrayList<ArrayList<String>> rows) throws Exception {
        Workbook workbook = null;
        WritableWorkbook writable_workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File(file_name));
            Sheet sheet = workbook.getSheet(table_name);
            int last_row_number = sheet.getRows();

            writable_workbook = Workbook.createWorkbook(new File(file_name), workbook);
            WritableSheet writable_sheet = writable_workbook.getSheet(table_name);
            
            int col_num = 0, row_num = last_row_number;
            for(ArrayList<String> row : rows) {
                col_num = 0;
                for(String s : row) {
                    Label label = new Label(col_num, row_num, s);
                    writable_sheet.addCell(label);
                    col_num++;
                }
                row_num++;
            }
            writable_workbook.write();
        } catch (Exception e) {
            throw e;
        } finally {
            if (workbook != null) {
                try {
                    writable_workbook.close();
                    workbook.close();
                } catch (Exception e) {
                    throw e;
                }
            }
        }
    }

    public ArrayList<ArrayList<String>> select(String table_name, List<String[]> search_conditions) {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File(file_name));
            app_data = new HashMap<String, ArrayList<ArrayList<String>>>();
            for(int s = 0; s < workbook.getNumberOfSheets(); s++) {
                Sheet sheet = workbook.getSheet(s);
                if(sheet.getName().toUpperCase().equals(table_name.toUpperCase())) {
                    for(int r = 0; r < sheet.getRows(); r++) {
                        boolean add_row = false;
                        for(String[] search_condition : search_conditions) {
                            if(sheet.getCell(Integer.parseInt(search_condition[0]), r).getContents().toUpperCase().equals(search_condition[1].toUpperCase())) {
                                add_row = true;
                            }
                            else add_row = false;

                        }
                        if(search_conditions.size() == 0) add_row = true;
                        if(add_row) {
                            ArrayList<String> column = new ArrayList<String>();
                            for(int c = 0; c < sheet.getColumns(); c++) {
                                column.add(sheet.getCell(c, r).getContents());
                            }                        
                            result.add(column);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
        return result;
    }

    public ArrayList<ArrayList<String>> selectFromMemory(String table_name, List<String[]> search_conditions) {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        for(Map.Entry<String, ArrayList<ArrayList<String>>> table : app_data.entrySet()) {
            if(table.getKey().toUpperCase().equals(table_name.toUpperCase())) {
                ArrayList<ArrayList<String>> table_content = table.getValue();
                for(ArrayList<String> row : table_content) {
                    boolean add_row = false;
                    for(String[] search_condition : search_conditions) {
                        if(row.get(Integer.parseInt(search_condition[0])).toUpperCase().equals(search_condition[1].toUpperCase())) {
                            add_row = true;
                        }
                        else add_row = false;
                    }
                    if(search_conditions.size() == 0) add_row = true;
                    if(add_row) { 
                        result.add(row);
                    }
                }
            }
        }
        return result;
    }

    public void copyAppDataToMemory(ArrayList<String> table_names) {
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File(file_name));
            app_data = new HashMap<String, ArrayList<ArrayList<String>>>();
            boolean copyTable = false;
            for(int s = 0; s < workbook.getNumberOfSheets(); s++) {
                Sheet sheet = workbook.getSheet(s);
                
                for(String tbl : table_names) {
                    copyTable = false;
                    if(tbl.toUpperCase().equals(sheet.getName().toUpperCase())) {
                        copyTable = true;
                        break;
                    }
                }
                if(table_names.size() == 0) copyTable = true;
                if(copyTable == true) {
                    ArrayList<ArrayList<String>> row = new ArrayList<ArrayList<String>>();
                    for(int r = 0; r < sheet.getRows(); r++) {
                        ArrayList<String> column = new ArrayList<String>();
                        for(int c = 0; c < sheet.getColumns(); c++) {
                            column.add(sheet.getCell(c, r).getContents());
                        }
                        row.add(column);
                    }
                    app_data.put(sheet.getName(), row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }
}