import java.sql.*;

public class SqlDatabase
{
    public static void main(String[] args)
    {
        // Loading JDBC Drivers
        startDrivers();
        // Getting public key form a pre-defined CPR number
        try
        {
            // Defining SQL Statement
            Statement stmt = sqlConnection().createStatement();
            ResultSet rs;
            // Defineing CPR Number
            int maincpr = 10;
            // Querying with CPR number in table publickey
            rs = stmt.executeQuery("SELECT publickey FROM rsaregister WHERE cpr =" + maincpr);
            // Sout all matches
            while (rs.next())
            {
                String publickey = rs.getString("publickey");
                System.out.println("Public Key for CPR : " + maincpr);
                System.out.println(publickey);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Failed to execute Query! ( " + e + " )");
        }
        finally
        {
            // Closing the connection
            closeConnection();
        }
    }


    protected static Connection sqlConnection()
    {
        try
        {
            // Well aware constant database password is hardcoded ._.
            Connection conn = DriverManager.getConnection("jdbc:mysql://sw2a307b.cdxpyk6pliya.eu-west-2.rds.amazonaws.com/P2","sw2a307b","studentaaudk");
            return conn;
        }
        catch (SQLException e)
        {
            System.out.println("MySQL Failed to Connect ( " + e + " )");
        }
        return null;
    }

    protected static Connection startDrivers()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("JDBC Driver not found! ( " + e + " )");
        }
        return null;
    }

    protected static Connection closeConnection()
    {
        try
        {
            sqlConnection().close();
            System.out.println("Connection Closed!");
        }
        catch(SQLException se)
        {
            System.out.println("Connection could not be closed! ( " + se + " )");

        }
        return null;
    }
}


