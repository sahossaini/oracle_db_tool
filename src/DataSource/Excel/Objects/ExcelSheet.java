package DataSource.Excel.Objects;

import java.util.ArrayList;

public class ExcelSheet {
    public String sheet_name;
    public ArrayList<ArrayList<String>> rows;

    public int total_columns;
    public int total_rows;

    public ExcelSheet (String sheet_name) {
        this.sheet_name = sheet_name;
        rows = new ArrayList<ArrayList<String>>();

        total_columns = total_rows = 0;
    }
}
