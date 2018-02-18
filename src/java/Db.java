import java.sql.*;

public class Db {

    private static Connection c = null;
    private static PreparedStatement prepStat = null;
    private static boolean hasData = false;

    public static void saveCommodity(String commodity, double purchasePrice, double amount,
                                     double currentPrice, double yield, double yieldPercent) throws SQLException, ClassNotFoundException {
        if (c == null) {
            getConnection();
        }

        String query = String.format("INSERT INTO commodities (commodity, purchasePrice, amount, currentPrice)" +
                " VALUES (?, %s, %s, %s, %s, %s,", purchasePrice, amount, currentPrice, yield, yieldPercent);
        prepStat = c.prepareStatement(query);
        prepStat.setString(1, commodity);

        prepStat.executeQuery();
    }

    public static ResultSet loadCommodity(int id) throws SQLException, ClassNotFoundException {
        getConnection();

        return prepStat.executeQuery("SELECT * FROM commodities WHERE id=" + id);
    }

    public static ResultSet loadCommodity(String commodity) throws SQLException, ClassNotFoundException {
        getConnection();

        prepStat = c.prepareStatement("SELECT * FROM commodities WHERE commodity=?");
        prepStat.setString(1, commodity);

        return prepStat.executeQuery();
    }

    private static void getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:src/resouces/db/commodities.db");

        if (!hasData) {
            Statement stat = c.createStatement();
            ResultSet result = stat.executeQuery("SELECT * FROM commodities");

            if (!result.next()) {
                prepStat.execute("CREATE TABLE commodities (id integer NOT NULL AUTO_INCREMENT, commodity varchar(255) NOT NULL," +
                        " purchasePrice DOUBLE NOT NULL, amount DOUBLE NOT NULL, currentPrice DOUBLE NOT NULL," +
                        " PRIMARY KEY (id);");
            }

            hasData = true;
        }
    }
}
