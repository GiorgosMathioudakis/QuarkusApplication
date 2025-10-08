//package org.acme;
//
//import java.sql.*;
//
//
//public class JDBCExample {
//
//    static final String DB_URL = "jdbc:postgresql://localhost:5432/mydatabase";
//    static final String USER = "myuser";
//    static final String PASS = "0000";
//
//    private Connection connection = null;
//
//    public void connect() {
//        try {
//            // Step 2: Establish the connection
//            System.out.println("Connecting to database...");
//            connection = DriverManager.getConnection(DB_URL, USER, PASS);
//            connection.setAutoCommit(false); // Start transaction block
//            System.out.println("Connection established successfully.");
//        } catch (SQLException e) {
//            System.err.println("Connection failed!");
//            e.printStackTrace();
//            // Exit if connection fails
//            System.exit(1);
//        }
//    }
//
//
//    public void createTable() {
//        // SQL to create a table with a SERIAL (auto-increment) primary key
//        final String CREATE_SQL =
//                "CREATE TABLE IF NOT EXISTS Employees (" +
//                        "id SERIAL PRIMARY KEY, " +
//                        "name VARCHAR(100) NOT NULL, " +
//                        "salary NUMERIC(10, 2) NOT NULL" +
//                        ");";
//
//        // Use try-with-resources for Statement to ensure it's closed
//        try (Statement stmt = connection.createStatement()) {
//            System.out.println("Executing SQL: CREATE TABLE...");
//            stmt.execute(CREATE_SQL);
//            System.out.println("Table 'Employees' is ready.");
//        } catch (SQLException e) {
//            System.err.println("Error creating table: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    // ------------------------------------------------------------------
//
//    /**
//     * Inserts a single employee using a PreparedStatement.
//     */
//    public void insertEmployee(String name, double salary) throws SQLException {
//        // SQL with '?' placeholders
//        final String INSERT_SQL = "INSERT INTO Employees (name, salary) VALUES (?, ?)";
//
//        // Use try-with-resources for PreparedStatement to ensure it's closed
//        try (PreparedStatement pstmt = connection.prepareStatement(INSERT_SQL)) {
//            // Set the values for the placeholders (1-indexed)
//            pstmt.setString(1, name);
//            pstmt.setDouble(2, salary);
//
//            // Execute the update
//            pstmt.executeUpdate();
//            System.out.println("  -> Inserted: " + name);
//
//        } catch (SQLException e) {
//            System.err.println("Error inserting employee " + name + ": " + e.getMessage());
//            throw e; // Re-throw to allow calling method to handle commit/rollback
//        }
//    }
//
//
//    public void selectEmployees() {
//        final String SELECT_SQL = "SELECT id, name, salary FROM Employees ORDER BY id";
//
//        System.out.println("\n--- Employee Records ---");
//
//        // Use try-with-resources for Statement and ResultSet
//        try (Statement stmt = connection.createStatement();
//             ResultSet rs = stmt.executeQuery(SELECT_SQL)) {
//
//            // Step 4: Process the results
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                double salary = rs.getDouble("salary");
//
//                System.out.printf("ID: %d | Name: %s | Salary: $%.2f%n", id, name, salary);
//            }
//            System.out.println("------------------------");
//
//        } catch (SQLException e) {
//            System.err.println("Error selecting data: " + e.getMessage());
//        }
//    }
//
//    // ------------------------------------------------------------------
//
//    /**
//     * Closes the database connection.
//     */
//    public void close() {
//        if (connection != null) {
//            try {
//                connection.close();
//                System.out.println("Connection closed.");
//            } catch (SQLException e) {
//                System.err.println("Error closing connection: " + e.getMessage());
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        JDBCExample dbManager = new JDBCExample();
//
//        try {
//            dbManager.connect();
//            dbManager.createTable();
//
//            // 3. Insert 3 employees
//            System.out.println("\nInserting 3 records...");
//            dbManager.insertEmployee("Maria Rodriguez", 85000.00);
//            dbManager.insertEmployee("Kenji Tanaka", 95000.75);
//            dbManager.insertEmployee("Lee Chen", 70000.00);
//
//            // Commit the transaction to save all insertions
//            dbManager.connection.commit();
//            System.out.println("\nTransaction Committed: Data saved to database.");
//
//            // 4. Select and display the data
//            dbManager.selectEmployees();
//
//        } catch (SQLException e) {
//            // If any operation fails, roll back the entire transaction
//            if (dbManager.connection != null) {
//                try {
//                    dbManager.connection.rollback();
//                    System.err.println("\nTransaction Rolled Back: No changes were saved.");
//                } catch (SQLException ex) {
//                    System.err.println("Rollback failed: " + ex.getMessage());
//                }
//            }
//        } finally {
//            dbManager.close();
//        }
//    }
//
//}
