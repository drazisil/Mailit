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

package com.drazisil.mailit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

import static com.drazisil.mailit.Mailit.logger;
import static com.drazisil.mailit.Mailit.plugin;
import static java.lang.String.format;

public class DatabaseManager {

    private final String connectionUrl = "jdbc:sqlite:./" + plugin.getDataFolder().getPath() + "/packages.db";
    private Connection conn = null;

    public void connect() {
        try {
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            // create a connection to the database
            conn = DriverManager.getConnection(connectionUrl);

            logger.info("Connection to SQLite has been established.");
            createTables();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    public void createTables() throws SQLException {
        String stm = "CREATE table if not exists packages (\"id\" text unique , \"from\" text, \"to\" text, \"contents\" text)";
        logger.warning(stm);

        conn.createStatement()
                .executeUpdate(stm);
    }

    public void update(MailPackage mailPackage) throws SQLException {
        String stm = format("insert into packages (\"id\", \"from\", \"to\", \"contents\") values ('%s', '%s', '%s', '%s') on conflict(\"id\") do update set \"contents\" = '%s'",
                mailPackage.getId(),
                mailPackage.getFrom().getName(),
                mailPackage.getTo().getName(),
                Arrays.toString(mailPackage.getInventory().getStorageContents()),
                Arrays.toString(mailPackage.getInventory().getStorageContents()));
        logger.warning(stm);

        conn.createStatement()
                .executeUpdate(stm);
    }

    public void delete(MailPackage mailPackage) throws SQLException {
        String stm = format("delete from packages where id='%s'",
                mailPackage.getId());
        logger.warning(stm);

        conn.createStatement()
                .executeUpdate(stm);
    }

//    public ArrayList<MailPackage> retrieveByReceiver(String name) throws SQLException {
//        String stm = format("select \"id\", \"from\", \"to\", \"contents\" where \"to\" = '%s'",
//                name);
//        logger.warning(stm);
//
//        Statement st =  conn.createStatement();
//
//        ArrayList<MailPackage> packages = new ArrayList<>();
//
//        ResultSet rs = null;
//        try {
//            rs = st.executeQuery(stm);
//            while (rs.next()) {
//
//                packages.add(new MailPackage(
//                        rs.getString("id"),
//                        rs.getString("from"),
//                        rs.getString("to"),
//                        rs.getString("contents")));
//
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return packages;
//    }
}
