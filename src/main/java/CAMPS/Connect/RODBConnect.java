package CAMPS.Connect;

import java.sql.*;
import java.util.*;
import org.apache.tomcat.jdbc.pool.*;

public class RODBConnect implements AutoCloseable {

    private static DataSource theDataSource;
    Properties prop = new Properties();
    private static String connectionURL;
    public Connection connection;
    Statement statement;
    public ResultSet rs, rs1, rs2;
    public String exc;
    public int SQLexceptionErrorCode;
    public int statusCode;
    public int iEffectedRows = 0;

    private synchronized static boolean makeDataSource() {
        if (theDataSource != null) {
            return true;
        }
        ResourceBundle rb = ResourceBundle.getBundle("CAMPS.properties.DBconfig");
        connectionURL = "jdbc:mysql://" + rb.getString("server.name") + ":" + rb.getString("port.no") + "/" + rb.getString("database.name") + "?zeroDateTimeBehavior=convertToNull";
        try {
            PoolProperties p = new PoolProperties();
            p.setUrl(connectionURL);
            p.setDriverClassName(rb.getString("driver.name"));
            p.setUsername(rb.getString("rouser.name"));
            p.setPassword(rb.getString("rouser.password"));
            p.setTestOnBorrow(true);
            p.setValidationQuery("SELECT 1");
            
            p.setMinIdle(5);           
            p.setMaxIdle(50);
            p.setInitialSize(1);
            p.setMaxActive(6000);
            p.setMaxWait(10000);
            
            p.setRemoveAbandoned(true);
            p.setRemoveAbandonedTimeout(600);
            p.setLogAbandoned(true);
            
            p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
            theDataSource = new DataSource(p);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public RODBConnect() {
        rs = null;
        statement = null;
        iEffectedRows = 0;
    }

    public void getConnection() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        makeDataSource();
        connection = theDataSource.getConnection();
    }

    public void read(String s) throws SQLException {
        statement = connection.createStatement();
        connection.createStatement (ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = statement.executeQuery(s);
    }

    public void read1(String s) throws SQLException {
        statement = connection.createStatement();
        connection.createStatement (ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs1 = statement.executeQuery(s);
    }

    public void read2(String s) throws SQLException {
        statement = connection.createStatement();
        connection.createStatement (ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs2 = statement.executeQuery(s);
    }

     

    

    public void closeConnection() throws SQLException {

        if (rs != null) {
            rs.close();
        }
        if (rs1 != null) {
            rs1.close();
        }
        if (rs2 != null) {
            rs2.close();
        }
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
        
        iEffectedRows = 0;
        rs = null;
        rs1 = null;
        rs2 = null;
        statement = null;
        connection = null;
    }
    
    @Override
    public void close() {
        try {
            closeConnection();
        } catch (SQLException e) {
            // log if required, but NEVER throw from close()
            //e.printStackTrace();
        }
    }

}

