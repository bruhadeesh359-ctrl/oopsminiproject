package narayana;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Module 2 – Exception Handling & Streams
 * Main runnable class: FileHandler
 * Demonstrates: custom exceptions + object serialization
 */

class RecipeNotFoundException extends Exception {
    public RecipeNotFoundException(String msg) {
        super(msg);
    }
}

class InvalidInputException extends Exception {
    public InvalidInputException(String msg) {
        super(msg);
    }
}

public class FileHandler {

    // Save list of recipes to file
    public static void saveRecipes(List<Recipe> recipes, String filePath)
            throws IOException, InvalidInputException {
        if (recipes == null || recipes.isEmpty()) {
            throw new InvalidInputException("Recipe list is empty.");
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(recipes);
            System.out.println("✅ Recipes saved to: " + filePath);
        }
    }

    // Load list of recipes from file
    @SuppressWarnings("unchecked")
    public static List<Recipe> loadRecipes(String filePath)
            throws IOException, ClassNotFoundException, RecipeNotFoundException {

        File f = new File(filePath);
        if (!f.exists() || !f.isFile()) {
            throw new RecipeNotFoundException("File not found: " + filePath);
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            Object obj = in.readObject();
            if (obj instanceof List<?>) {
                return (List<Recipe>) obj;
            } else {
                throw new ClassNotFoundException("File content not a List<Recipe>.");
            }
        }
    }

    // ---------- MAIN runnable demo ----------
    public static void main(String[] args) {
        String file = "recipes_data.dat";

        // sample recipes (use Module 1 classes)
        VegRecipe veg = new VegRecipe("Paneer Curry", "Cook paneer with spices.");
        veg.addIngredient(new Ingredient("Paneer", "200 g"));
        veg.addIngredient(new Ingredient("Tomato", "2 pcs"));

        NonVegRecipe nonVeg = new NonVegRecipe("Egg Curry", "Boil eggs and cook with curry.");
        nonVeg.addIngredient(new Ingredient("Eggs", "3 pcs"));

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(veg);
        recipes.add(nonVeg);

        try {
            saveRecipes(recipes, file);
        } catch (InvalidInputException | IOException e) {
            System.err.println("Error saving: " + e.getMessage());
        }

        try {
            List<Recipe> loaded = loadRecipes(file);
            System.out.println("\n📖 Loaded recipes from file:");
            for (Recipe r : loaded) {
                r.display();
                System.out.println();
            }
        } catch (RecipeNotFoundException | IOException | ClassNotFoundException e) {
            System.err.println("Error loading: " + e.getMessage());
        }

        System.out.println("\n✅ Module 2 completed successfully!");
    }
}