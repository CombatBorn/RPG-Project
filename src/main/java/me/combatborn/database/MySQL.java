package me.combatborn.database;

import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {
    private String host;
    private String port;
    private String database;
    private String username;
    private String password;
    String type;
    private Connection connection;

    public MySQL(String type) {
        this.type = type;
        if (type.equals("local")) {
            this.host = "localhost";
            this.port = "3306";
            this.database = "rpg-project";
            this.username = "root";
            this.password = "";
        }
        this.connect();
    }

    public boolean isConnected() {
        return isConnected(null);
    }

    public boolean isConnected(String type) {
        if (type != null && this.connection == null) {
            Bukkit.getLogger().info("Unable to load " + type + ", the SQL database not connected.");
        }
        return (this.connection != null);
    }

    public void connect() {
        if (!this.isConnected()) {
            try {
                this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port +
                        "/" + this.database + "?useSSL=false", this.username, this.password);
                Bukkit.getLogger().info("[RPG-Project] Connected to the \"" + this.type + "\" SQL Server.");
            } catch (SQLException e) {
                Bukkit.getLogger().info("Error establishing SQL Connection.");
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void attemptMySQLDisconnect() {
        try {
            this.disconnect();
        } catch (Exception e) {
            Bukkit.getLogger().info("[RPG-Project] MySQL failed to disconnect.");
        } finally {
            if (!(this.isConnected())) {
                Bukkit.getLogger().info("[RPG-Project] MySQL has been disconnected.");
            }
        }
    }
}
