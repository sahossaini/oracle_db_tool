package DataSource.Excel.Objects;

import java.util.ArrayList;

public class Excel {
    public String file_name;
    public ArrayList<ExcelSheet> sheets;

    public Excel (String file_name) {
        this.file_name = file_name;
        sheets = new ArrayList<ExcelSheet>();
    }
}
