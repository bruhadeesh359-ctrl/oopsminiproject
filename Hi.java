package narayana;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Module 4 – JDBC Integration
 * Runnable main class: Hi
 * Demonstrates: connection, table creation, insert, select, delete
 */

// ---------- Database Connection ----------
class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/recipe_db";
    private static final String USER = "root";               // your MySQL username
    private static final String PASSWORD = "murugan06";    // your MySQL password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

// ---------- Data Access Object ----------
class RecipeDAO {

    // Create table if not exists
    public void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS recipes (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(100),
                category VARCHAR(50),
                instructions TEXT
            )
        """;
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ Table 'recipes' verified/created.");
        } catch (SQLException e) {
            System.err.println("❌ Error creating table: " + e.getMessage());
        }
    }

    // Insert record
    public void insertRecipe(Recipe r) {
        String sql = "INSERT INTO recipes (name, category, instructions) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getName());
            ps.setString(2, r.getCategory());
            ps.setString(3, r.getInstructions());
            ps.executeUpdate();
            System.out.println("✅ Inserted: " + r.getName());
        } catch (SQLException e) {
            System.err.println("❌ Insert failed: " + e.getMessage());
        }
    }

    // Retrieve all
    public List<Recipe> getAllRecipes() {
        List<Recipe> list = new ArrayList<>();
        String sql = "SELECT * FROM recipes";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String name = rs.getString("name");
                String category = rs.getString("category");
                String instructions = rs.getString("instructions");
                Recipe recipe = category.equalsIgnoreCase("Vegetarian")
                        ? new VegRecipe(name, instructions)
                        : new NonVegRecipe(name, instructions);
                list.add(recipe);
            }
        } catch (SQLException e) {
            System.err.println("❌ Retrieval error: " + e.getMessage());
        }
        return list;
    }

    // Delete by name
    public void deleteRecipeByName(String name) {
        String sql = "DELETE FROM recipes WHERE name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("🗑 Deleted: " + name);
            else
                System.out.println("⚠ No recipe found named " + name);
        } catch (SQLException e) {
            System.err.println("❌ Delete error: " + e.getMessage());
        }
    }
}

// ---------- Main Runnable ----------
public class Hi {
    public static void main(String[] args) {
        RecipeDAO dao = new RecipeDAO();
        dao.createTable();

        VegRecipe veg = new VegRecipe("Veg Biryani", "Cook rice with veggies and masala.");
        NonVegRecipe nonVeg = new NonVegRecipe("Chicken Curry", "Cook chicken with spices.");

        dao.insertRecipe(veg);
        dao.insertRecipe(nonVeg);

        System.out.println("\n📖 Recipes in Database:");
        for (Recipe r : dao.getAllRecipes()) {
            r.display();
            System.out.println();
        }

        dao.deleteRecipeByName("Veg Biryani");

        System.out.println("\n✅ JDBC operations completed successfully!");
    }
}