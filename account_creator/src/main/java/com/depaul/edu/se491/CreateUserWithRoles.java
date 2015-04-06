package com.depaul.edu.se491;

import org.springframework.security.crypto.password.StandardPasswordEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by Tom Mitic on 3/14/15.
 */
public class CreateUserWithRoles {

    private static Properties props;
    static{
        props = new Properties();
        InputStream in = CreateUserWithRoles.class.getClassLoader().getResourceAsStream("app.properties");
        try {
            props.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Throwable{
        Connection conn = getConnection();
        StandardPasswordEncoder ec = new StandardPasswordEncoder(props.getProperty("app.secret"));
        String first_name = args[0];
        String last_name = args[1];
        String email = args[2];
        String encoded = ec.encode(args[3]);
        Boolean enabled = Boolean.parseBoolean(args[4]);
        String uuid = UUID.randomUUID().toString();
        if (args[5] == null) {
            throw new Throwable("You must provide at least one role, either ROLE_USER or ROLE_ADMIN");
        }
        String role_1 = args[5];
        String role_2 = null;
        if (args.length == 7) {
            role_2 = args[6];
        }

        String find_user_id = "select id from users where email = ?";
        String insert_user = "insert into users(uuid, first_name, last_name, email, password, enabled) " +
                             "values(?, ?, ?, ?, ?, ?)";
        String insert_role = "insert into user_roles(user_id, role) values " +
                             "(?, ?)";
        PreparedStatement p_s = null;

        try {
            conn.setAutoCommit(false);
            p_s = conn.prepareStatement(insert_user);
            p_s.setString(1, uuid);
            p_s.setString(2, first_name);
            p_s.setString(3, last_name);
            p_s.setString(4, email);
            p_s.setString(5, encoded);
            p_s.setBoolean(6, enabled);
            p_s.executeUpdate();
            conn.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (p_s != null) {
                p_s.close();
            }
        }

        p_s = null;
        Integer user_id = null;
        try {
            conn.setAutoCommit(false);
            p_s = conn.prepareStatement(find_user_id);
            p_s.setString(1, email);
            ResultSet rs = p_s.executeQuery();
            if (rs.next()) {
                user_id = rs.getInt("id");
            }
            conn.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (p_s != null) {
                p_s.close();
            }
        }

        try {
            conn.setAutoCommit(false);
            p_s = conn.prepareStatement(insert_role);
            p_s.setInt(1, user_id);
            p_s.setString(2, role_1);
            p_s.executeUpdate();
            if (role_2 != null) {
                p_s.setString(2, role_2);
                p_s.executeUpdate();
            }
            conn.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (p_s != null) {
                p_s.close();
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        Properties p = new Properties();
        p.put("user", props.get("app.postgres_username"));
        p.put("password", props.get("app.postgres_password"));
        return DriverManager.getConnection(props.getProperty("app.postgres_uri"), p);
    }
}
