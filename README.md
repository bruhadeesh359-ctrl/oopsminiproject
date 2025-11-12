# OOPS MINI PROJECT REPORT

**Project Title:** Recipe Management System

**Developed by:** R bruhadeesh narayana

**Database User:** root

**Database Password:** murugan06

---

## Expt. No. : 16  RECIPE MANAGEMENT SYSTEM

### PO–PSO Mapping Table

#### PO Mapping

| PO   | Description                     | Relevance (1–3) | Justification                                                                                                      |
| ---- | ------------------------------- | --------------: | ------------------------------------------------------------------------------------------------------------------ |
| PO1  | Engineering Knowledge           |               3 | Applies Java fundamentals, OOP principles, and database concepts to build a modular recipe management application. |
| PO2  | Problem Analysis                |               2 | Identifies and resolves issues like duplicate recipes, invalid data, and inconsistent database records.            |
| PO3  | Design/Development of Solutions |               3 | Designs a complete system integrating backend logic, JDBC database, and JavaFX/Swing user interface.               |
| PO4  | Conduct Investigations          |               2 | Analyzes data consistency, connection handling, and GUI interaction for performance and reliability.               |
| PO5  | Modern Tool Usage               |               3 | Utilizes tools like Eclipse IDE, MySQL database, and JavaFX/Swing for GUI-based implementation.                    |
| PO6  | Engineer and Society            |               1 | Promotes digital organization and accessibility of personal and shared recipes.                                    |
| PO8  | Ethics                          |               3 | Ensures ethical software design through proper data validation, transparency, and user-friendly access.            |
| PO9  | Individual & Team Work          |               1 | Encourages collaborative coding, debugging, and database design while maintaining modular development.             |
| PO10 | Communication                   |               2 | Demonstrates effective communication through structured coding, documentation, and GUI-based feedback.             |

#### PSO Mapping

| PSO  | Description                       | Relevance | Justification                                                                                     |
| ---- | --------------------------------- | --------: | ------------------------------------------------------------------------------------------------- |
| PSO1 | Apply core computing knowledge    |         3 | Uses Java OOP, encapsulation, inheritance, and polymorphism to design a recipe management system. |
| PSO2 | Design and implement solutions    |         2 | Develops functional modules such as recipe management, file storage, and database connectivity.   |
| PSO3 | Use modern tools and technologies |         2 | Implements JDBC and JavaFX/Swing for database interaction and user interface design.              |

---

## Introduction

The Recipe Management System is a Java-based desktop application designed to manage, store, and retrieve recipes efficiently. It enables users to add, search, sort, and store recipe information using an intuitive interface. The system integrates the major concepts from the Java curriculum — OOP, Exception Handling, File I/O, Multithreading, JDBC, and GUI (JavaFX or Swing).

Developed in five modules (matching your syllabus units), this project demonstrates end-to-end software design — from object-oriented architecture to a database-driven GUI.

---

## Project Distribution

| Module | Concept (Unit)                         | Description                                                                                                           |
| ------ | -------------------------------------- | --------------------------------------------------------------------------------------------------------------------- |
| mod1   | Unit I – OOP Fundamentals              | Defines the `Recipe`, `Ingredient`, and `User` classes with attributes, constructors, encapsulation, and abstraction. |
| mod2   | Unit II – Exception Handling & Streams | Handles recipe data saving and loading using Object Streams and custom exceptions.                                    |
| mod3   | Unit III – Generics & Multithreading   | Manages recipe collections with generics and an auto-save background thread.                                          |
| mod4   | Unit IV – JDBC Connectivity            | Connects to MySQL to store and retrieve recipe details using JDBC.                                                    |
| mod5   | Unit V – GUI Programming (JavaFX)      | Provides an interactive GUI for CRUD operations (Create, Read, Update, Delete).                                       |

---

## System Architecture

A modular layered architecture:

* Presentation Layer: JavaFX (or Swing) GUI for user interaction (mod5).
* Business Logic Layer: RecipeManager (generics, synchronization) and controllers (mod3).
* Persistence Layer: FileHandler for serialization (mod2) and RecipeDAO for JDBC (mod4).
* Core Model: Recipe, Ingredient, User classes (mod1).

---

## Coding

> Note: Save each module as indicated (package `narayana`). These classes are written to compile and run in Eclipse with JDK 11+ and MySQL Connector/J on the build path.

---

### Module 1 (Unit I – OOP Fundamentals)

**File:** `Recipe.java` (package `narayana`)

```java
package narayana;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Recipe implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int recipeCount = 0;

    protected int id;
    protected String name;
    protected String category; // e.g., Vegetarian, Non-Vegetarian
    protected List<Ingredient> ingredients;
    protected String instructions;

    public Recipe(String name, String category, String instructions) {
        this.id = ++recipeCount;
        this.name = name;
        this.category = category;
        this.instructions = instructions;
        this.ingredients = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getInstructions() { return instructions; }
    public List<Ingredient> getIngredients() { return ingredients; }

    public void addIngredient(Ingredient ing) { ingredients.add(ing); }

    public abstract void display();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;
        Recipe r = (Recipe) o;
        return id == r.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}

// VegRecipe subclass
class VegRecipe extends Recipe {
    public VegRecipe(String name, String instructions) {
        super(name, "Vegetarian", instructions);
    }

    @Override
    public void display() {
        System.out.println("Veg Recipe [" + id + "]: " + name);
        System.out.println("Category: " + category);
        System.out.println("Instructions: " + instructions);
        System.out.println("Ingredients:");
        for (Ingredient ing : ingredients) System.out.println(" - " + ing);
    }
}

// NonVegRecipe subclass
class NonVegRecipe extends Recipe {
    public NonVegRecipe(String name, String instructions) {
        super(name, "Non-Vegetarian", instructions);
    }

    @Override
    public void display() {
        System.out.println("Non-Veg Recipe [" + id + "]: " + name);
        System.out.println("Category: " + category);
        System.out.println("Instructions: " + instructions);
        System.out.println("Ingredients:");
        for (Ingredient ing : ingredients) System.out.println(" - " + ing);
    }
}

// Ingredient class
class Ingredient implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String qty;

    public Ingredient(String name, String qty) {
        this.name = name;
        this.qty = qty;
    }

    public String getName() { return name; }
    public String getQty() { return qty; }
    @Override
    public String toString() { return name + " (" + qty + ")"; }
}

// User class
class User {
    private int userId;
    private String username;
    private String email;

    public User(int id, String uname, String email) {
        this.userId = id;
        this.username = uname;
        this.email = email;
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
}
```

---

### Module 2 (Unit II – Exception Handling & Streams)

**File:** `FileHandler.java` (package `narayana`)

```java
package narayana;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

class RecipeNotFoundException extends Exception {
    public RecipeNotFoundException(String msg) { super(msg); }
}

class InvalidInputException extends Exception {
    public InvalidInputException(String msg) { super(msg); }
}

public class FileHandler {
    private static final String FILE_NAME = "recipes.dat";

    public static void saveRecipes(List<Recipe> recipes, String filePath) throws IOException, InvalidInputException {
        if (recipes == null || recipes.isEmpty()) throw new InvalidInputException("Recipe list cannot be null or empty");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(recipes);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Recipe> loadRecipes(String filePath) throws IOException, ClassNotFoundException, RecipeNotFoundException {
        File f = new File(filePath);
        if (!f.exists()) throw new RecipeNotFoundException("File not found: " + filePath);
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
            return (List<Recipe>) in.readObject();
        }
    }

    // Simple test main
    public static void main(String[] args) {
        try {
            List<Recipe> list = new ArrayList<>();
            VegRecipe v = new VegRecipe("Tomato Soup", "Boil tomatoes and blend.");
            v.addIngredient(new Ingredient("Tomato", "4 pcs"));
            list.add(v);
            saveRecipes(list, FILE_NAME);
            System.out.println("Saved "+list.size()+" recipes to " + FILE_NAME);

            List<Recipe> loaded = loadRecipes(FILE_NAME);
            System.out.println("Loaded " + loaded.size() + " recipes:");
            loaded.forEach(Recipe::display);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
```

---

### Module 3 (Unit III – Generics & Multithreading)

**File:** `RecipeManager.java` (package `narayana`)

```java
package narayana;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecipeManager<T extends Recipe> {
    private final List<T> recipes = Collections.synchronizedList(new ArrayList<>());

    public synchronized void addRecipe(T r) {
        if (r != null) recipes.add(r);
    }

    public synchronized List<T> getAllRecipes() { return new ArrayList<>(recipes); }

    public synchronized T findByName(String name) {
        for (T r : recipes) if (r.getName().equalsIgnoreCase(name)) return r;
        return null;
    }

    // Auto-save thread
    public static class AutoSaveThread extends Thread {
        private final RecipeManager<?> manager;
        private final String filePath;
        private final long intervalMs;
        private volatile boolean running = true;

        public AutoSaveThread(RecipeManager<?> manager, String filePath, long intervalMs) {
            this.manager = manager;
            this.filePath = filePath;
            this.intervalMs = intervalMs;
            setDaemon(true);
        }

        @Override
        public void run() {
            while (running) {
                try {
                    Thread.sleep(intervalMs);
                    FileHandler.saveRecipes((List<Recipe>) manager.getAllRecipes(), filePath);
                    System.out.println("Auto-saved recipes to " + filePath);
                } catch (InterruptedException e) { Thread.currentThread().interrupt(); break; }
                catch (IOException e) { System.err.println("Auto-save IO error: " + e.getMessage()); }
                catch (Exception e) { System.err.println("Auto-save error: " + e.getMessage()); }
            }
        }

        public void stopTask() { running = false; interrupt(); }
    }

    // Test main
    public static void main(String[] args) throws InterruptedException {
        RecipeManager<Recipe> manager = new RecipeManager<>();
        VegRecipe r = new VegRecipe("Veg Pulao", "Cook rice with veggies.");
        r.addIngredient(new Ingredient("Rice", "1 cup"));
        manager.addRecipe(r);
        AutoSaveThread t = new AutoSaveThread(manager, "autosave_recipes.dat", 5000);
        t.start();
        Thread.sleep(11000);
        t.stopTask();
    }
}
```

---

### Module 4 (Unit IV – JDBC Connectivity)

**File:** `Hi.java` (package `narayana`) — main runnable class for JDBC operations

```java
package narayana;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/recipe_db";
    private static final String USER = "root";
    private static final String PASSWORD = "murugan06";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

class RecipeDAO {
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS recipes (" +
                     "id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "name VARCHAR(100), category VARCHAR(50), instructions TEXT)";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'recipes' verified/created.");
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    public void insertRecipe(Recipe r) {
        String sql = "INSERT INTO recipes (name, category, instructions) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getName());
            ps.setString(2, r.getCategory());
            ps.setString(3, r.getInstructions());
            ps.executeUpdate();
            System.out.println("Inserted: " + r.getName());
        } catch (SQLException e) { System.err.println("Error inserting: " + e.getMessage()); }
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> list = new ArrayList<>();
        String sql = "SELECT * FROM recipes";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String name = rs.getString("name");
                String category = rs.getString("category");
                String instr = rs.getString("instructions");
                Recipe recipe = category.equalsIgnoreCase("Vegetarian") ? new VegRecipe(name, instr) : new NonVegRecipe(name, instr);
                list.add(recipe);
            }
        } catch (SQLException e) { System.err.println("Error retrieving: " + e.getMessage()); }
        return list;
    }

    public void deleteRecipeByName(String name) {
        String sql = "DELETE FROM recipes WHERE name = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Deleted: " + name : "No recipe found: " + name);
        } catch (SQLException e) { System.err.println("Error deleting: " + e.getMessage()); }
    }
}

public class Hi {
    public static void main(String[] args) {
        RecipeDAO dao = new RecipeDAO();
        dao.createTable();
        VegRecipe veg = new VegRecipe("Veg Biryani", "Cook rice with veggies and masala.");
        NonVegRecipe nonveg = new NonVegRecipe("Chicken Curry", "Cook chicken with spices.");
        dao.insertRecipe(veg);
        dao.insertRecipe(nonveg);
        System.out.println("Recipes in DB:");
        for (Recipe r : dao.getAllRecipes()) { r.display(); System.out.println(); }
        dao.deleteRecipeByName("Veg Biryani");
    }
}
```

**SQL to run once before using module 4:**

```sql
CREATE DATABASE recipe_db;
USE recipe_db;
CREATE TABLE recipes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  category VARCHAR(50),
  instructions TEXT
);
```

---

### Module 5 (Unit V – GUI Programming)

**File:** `RecipeAppGUI.java` (package `narayana`) — JavaFX application

```java
package narayana;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.*;

public class RecipeAppGUI extends Application {
    private RecipeDAO dao = new RecipeDAO();
    private ObservableList<RecipeRow> data = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Recipe Management System");
        TableView<RecipeRow> table = new TableView<>();
        TableColumn<RecipeRow, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<RecipeRow, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<RecipeRow, String> catCol = new TableColumn<>("Category");
        catCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        table.getColumns().addAll(idCol, nameCol, catCol);
        table.setItems(data);

        TextField nameField = new TextField(); nameField.setPromptText("Name");
        TextField catField = new TextField(); catField.setPromptText("Category");
        TextArea instr = new TextArea(); instr.setPromptText("Instructions"); instr.setPrefRowCount(3);
        Button add = new Button("Add");
        Button refresh = new Button("Refresh");
        Button delete = new Button("Delete");

        add.setOnAction(e -> {
            String name = nameField.getText();
            String cat = catField.getText();
            String ins = instr.getText();
            if (name.isEmpty()) { showAlert("Validation", "Name required"); return; }
            Recipe r = cat.equalsIgnoreCase("Vegetarian") ? new VegRecipe(name, ins) : new NonVegRecipe(name, ins);
            dao.insertRecipe(r);
            showAlert("Saved", "Recipe saved to DB");
            loadData();
        });

        refresh.setOnAction(e -> loadData());

        delete.setOnAction(e -> {
            RecipeRow sel = table.getSelectionModel().getSelectedItem();
            if (sel == null) { showAlert("Select", "Select a row"); return; }
            dao.deleteRecipeByName(sel.getName());
            loadData();
        });

        HBox inputs = new HBox(10, nameField, catField, add, refresh, delete);
        VBox root = new VBox(10, table, instr, inputs);
        root.setPadding(new Insets(10));
        loadData();
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

    private void loadData() {
        data.clear();
        for (Recipe r : dao.getAllRecipes()) {
            data.add(new RecipeRow(r.getId(), r.getName(), r.getCategory()));
        }
    }

    private void showAlert(String t, String m) { Alert a = new Alert(Alert.AlertType.INFORMATION); a.setTitle(t); a.setContentText(m); a.showAndWait(); }

    public static void main(String[] args) { launch(args); }

    public static class RecipeRow {
        private int id; private String name; private String category;
        public RecipeRow(int id, String name, String category) { this.id = id; this.name = name; this.category = category; }
        public int getId() { return id; } public String getName() { return name; } public String getCategory() { return category; }
    }
}
```

> If you prefer Swing instead of JavaFX, I can provide a Swing-based GUI file matching the same functionality.

---

## Database (MySQL) — SQL Scripts & Sample Data

```sql
CREATE DATABASE recipe_db;
USE recipe_db;

CREATE TABLE recipes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  category VARCHAR(50),
  instructions TEXT
);

INSERT INTO recipes (name, category, instructions) VALUES
('Vegetable Soup', 'Vegetarian', 'Boil vegetables and blend.'),
('Egg Curry', 'Non-Vegetarian', 'Cook eggs with masala.');
```

---

## Screenshots & Output (Placeholders)

* Frontend Structure: (Use screenshots of RecipeAppGUI running)
* Database table snapshot: (Use MySQL Workbench export screenshot)

---

## Description & Conclusion

This Recipe Management System demonstrates core Java concepts — encapsulation, inheritance, exception handling, serialization, generics, multithreading, JDBC integration, and GUI design. The modular structure makes testing and grading straightforward. All modules are cohesive and ready for extension (user authentication, image upload, cloud DB).

## Future Work

1. Add user authentication/authorization.
2. Add recipe images and PDF export.
3. Add advanced search and filters (by ingredient, cook time).
4. Migrate DB to cloud (AWS RDS) and add REST API.

---

**Repository Recommendation:** Create a GitHub repo named `Recipe_Management_System` and commit all source files. Include a README that explains setup (JDK version, MySQL setup, adding connector jar, running GUI with VM args `-Djdbc.drivers=com.mysql.cj.jdbc.Driver -Duser.timezone=UTC`).

---

*Report generated using your provided template as reference* fileciteturn0file1

