package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection  {
    private static Connection connect;
    private static String password = "";
    private static String dbName = "testpersonne";
    private static String portNumber = "3306";
    private static String userName = "root";
    private static String serverName = "localhost";


    public DBConnection(){

            // creation de la connection
            Properties connectionProps = new Properties();
            connectionProps.put("user", this.userName);
            connectionProps.put("password", this.password);
            String urlDB = "jdbc:mysql://" + this.serverName + ":";
            urlDB += this.portNumber + "/" + this.dbName;
            try {
                connect = DriverManager.getConnection(urlDB, connectionProps);
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
    }

    public static synchronized Connection getInstance(){
        if (connect == null) {
            new DBConnection();
        }
        return connect;
    }

    public static void setNomDB(String databaseName) {
        dbName = databaseName;
        connect = null;
    }
}
