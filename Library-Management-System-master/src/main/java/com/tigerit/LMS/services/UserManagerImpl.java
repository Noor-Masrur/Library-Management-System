package com.tigerit.LMS.services;
import com.tigerit.LMS.entities.Member;
import java.sql.*;
// ... other imports

public class UserManagerImpl implements UserManager {

    // Database connection details (replace with your actual values)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mylibrary";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "!tigerit36";

    @Override
    public void registerMember(Member member) {
        // ... (validation and password hashing)

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO members (name, email, contact, registered_date) VALUES (?, ?, ?, ?)"
            );
            statement.setString(1, member.getName());
            statement.setString(2, member.getEmail());
            statement.setString(3, member.getContact());
            statement.setDate(4, Date.valueOf(member.getRegisteredDate()));
            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error registering member: " + e.getMessage());
            throw new RuntimeException("Database error", e);
        }
    }

    // ... other methods using database interactions (similarly with prepared statements)
}