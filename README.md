# HikariAPI

<div>
    How to usage?
</div>
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
