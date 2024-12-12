# HikariAPI

<b>Example usage MySQLProvider:</b>

````java
import dev.jp.hakariapi.database.MySQLProvider;
import dev.jp.hakariapi.misc.MySQLQuery;
import example.data.PlayerMoney;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import static example.misc.ExampleQuery.*;

public class ExampleMain extends JavaPlugin implements Listener {

    private MySQLProvider provider;
    private MySQLQuery query;

    @Override
    public void onEnable() {
        // Initialize MySQLProvider
        provider = new MySQLProvider(
                "ExamplePool",  // Pool name
                "0.0.0.0",      // Host
                3306,           // Port
                "database",     // Database
                "root",         // Username
                "root"          // Password
        );

        // Initialize connection
        try {
            provider.init();
            query = new MySQLQuery(provider);
            query.createTable(CREATE_TABLE);
            getLogger().info("Database connection established.");
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().severe("Failed to initialize database connection!");
        }

        // Register event listener
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Close database connection
        if (provider != null) {
            provider.closeConnection();
        }
        getLogger().info("Database connection closed.");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Example of setting player money
        PlayerMoney playerMoney = new PlayerMoney();
        playerMoney.setOwner(player.getUniqueId().toString()); // Store UUID instead of name
        playerMoney.setAccount(100.00);

        // Insert data into database
        try {
            query.insertRecord(INSERT, playerMoney.getOwner(), playerMoney.getAccount());
            player.sendMessage("Welcome! Your account has been credited with $100.00.");
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage("Failed to update your account in the database.");
        }
    }
}
````

<b>Example class query:</b>

````java
public class ExampleQuery { 
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS Example (" + 
            "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            "player VARCHAR(50), " + 
            "money DOUBLE);";
    public static final String INSERT = "INSERT INTO Example (player, money) VALUES (?, ?);"; 
    public static final String UPDATE = "UPDATE Example SET money = ? WHERE player = ?;"; 
    public static final String SELECT_ONE = "SELECT * FROM Example WHERE player = ?;";
    public static final String SELECT_ALL = "SELECT * FROM Example;";
}
````
