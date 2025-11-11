
package narayana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Module 5 – GUI Programming (Swing + MVC pattern)
 * Runnable main class: RecipeAppGUI
 * Requires Recipe.java (Module 1) and RecipeDAO.java (from Module 4)
 */

public class RecipeAppGUI extends JFrame {

    private JTextField nameField;
    private JComboBox<String> categoryBox;
    private JTextArea instructionsArea;
    private JTextArea displayArea;
    private RecipeDAO dao = new RecipeDAO(); // reuse JDBC DAO

    public RecipeAppGUI() {
        super("🍲 Recipe Management System");

        // ensure table exists
        dao.createTable();

        // Layout and components
        setLayout(new BorderLayout(10, 10));
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        inputPanel.add(new JLabel("Recipe Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Category:"));
        categoryBox = new JComboBox<>(new String[]{"Vegetarian", "Non-Vegetarian"});
        inputPanel.add(categoryBox);

        inputPanel.add(new JLabel("Instructions:"));
        instructionsArea = new JTextArea(3, 20);
        JScrollPane insScroll = new JScrollPane(instructionsArea);
        inputPanel.add(insScroll);

        JButton addButton = new JButton("Add Recipe");
        JButton viewButton = new JButton("View All Recipes");
        inputPanel.add(addButton);
        inputPanel.add(viewButton);

        add(inputPanel, BorderLayout.NORTH);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(displayArea);
        add(scroll, BorderLayout.CENTER);

        // Event handlers
        addButton.addActionListener(e -> addRecipe());
        viewButton.addActionListener(e -> viewRecipes());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Add recipe to DB
    private void addRecipe() {
        String name = nameField.getText().trim();
        String category = categoryBox.getSelectedItem().toString();
        String instructions = instructionsArea.getText().trim();

        if (name.isEmpty() || instructions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Recipe r;
        if (category.equalsIgnoreCase("Vegetarian"))
            r = new VegRecipe(name, instructions);
        else
            r = new NonVegRecipe(name, instructions);

        dao.insertRecipe(r);
        JOptionPane.showMessageDialog(this, "✅ Recipe added successfully!");
        nameField.setText("");
        instructionsArea.setText("");
    }

    // View all recipes
    private void viewRecipes() {
        displayArea.setText("");
        List<Recipe> recipes = dao.getAllRecipes();
        if (recipes.isEmpty()) {
            displayArea.append("No recipes found.\n");
            return;
        }
        for (Recipe r : recipes) {
            displayArea.append(r.getCategory() + " - " + r.getName() + "\n");
            displayArea.append("Instructions: " + r.getInstructions() + "\n\n");
        }
    }

    // ---- main() ----
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecipeAppGUI());
    }
}