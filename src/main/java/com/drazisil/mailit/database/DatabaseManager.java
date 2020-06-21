/*
 * Mailit is a Minecraft Bukkit plugin to allow for player mailboxes
 * Copyright (C) 2020 Joe Becher (Drazisil)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.drazisil.mailit.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static com.drazisil.mailit.Mailit.logger;
import static com.drazisil.mailit.Mailit.plugin;

public class DatabaseManager {

    private final String connectionUrl = "jdbc:sqlite:./" + plugin.getDataFolder().getPath() + "/packages.db";
    private Connection conn = null;

    public void connect() throws SQLException {
        try {
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            // create a connection to the database
            conn = DriverManager.getConnection(connectionUrl);

            logger.info("Connection to SQLite has been established.");

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTables() throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate("CREATE table if not exists packages (id text unique , 'from' text, 'to' text, contents text)");
    }
}
