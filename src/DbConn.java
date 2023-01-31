import java.sql.*;
// import oracle.jdbc.driver.*;

public class DbConn  {
    Connection session;

    DbConn (String db_url, String user_name, String password) throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        session = DriverManager.getConnection(db_url, user_name, password);

        //step3 create the statement object  
        Statement stmt=session.createStatement();  
        
        //step4 execute query  
        ResultSet rs=stmt.executeQuery("select count(*) from v$session");  
        while(rs.next())  
        System.out.println(rs.getInt(1));  
        
        //step5 close the connection object  
        // session.close();  
    }
}
