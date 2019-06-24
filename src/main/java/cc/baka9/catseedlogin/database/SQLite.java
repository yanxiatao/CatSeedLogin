package cc.baka9.catseedlogin.database;

import cc.baka9.catseedlogin.CatSeedLogin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite extends SQL {
    CatSeedLogin plugin = CatSeedLogin.getInstance();
    private Connection connection;

    @Override
    public void createBD() throws Exception{
        flush(new BufferStatement("CREATE TABLE accounts (name CHAR(255),password CHAR(255),lastAction TIMESTAMP)"));
    }

    @Override
    public Connection getConnection() throws SQLException{

        if (this.connection != null && !this.connection.isClosed()) {
            return this.connection;
        }
        if (plugin.getDataFolder().exists()) {
            try {
                Class.forName("org.sqlite.JDBC");
                this.connection = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder().getAbsolutePath() + "/accounts.db");
                return this.connection;
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            final boolean mkdir = plugin.getDataFolder().mkdir();
            return this.getConnection();
        }
    }

}