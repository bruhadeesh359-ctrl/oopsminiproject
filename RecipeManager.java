package narayana;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Module 3 - Generics and Multithreading
 * Concepts: Generics, Threads, Synchronization, Auto-save task
 */

public class RecipeManager<T extends Recipe> {

    // Thread-safe list
    private final List<T> recipes = Collections.synchronizedList(new ArrayList<>());

    // Add recipe (synchronized for thread safety)
    public synchronized void addRecipe(T recipe) {
        if (recipe != null) {
            recipes.add(recipe);
            System.out.println("✅ Recipe added: " + recipe.getName());
        }
    }

    // Get all recipes
    public synchronized List<T> getAllRecipes() {
        return new ArrayList<>(recipes);
    }

    // Find a recipe by name
    public synchronized T findRecipeByName(String name) {
        for (T r : recipes) {
            if (r.getName().equalsIgnoreCase(name)) {
                return r;
            }
        }
        return null;
    }

    // Inner thread class for auto-saving recipes to file periodically
    public static class AutoSaveThread extends Thread {
        private final RecipeManager<?> manager;
        private final String filePath;
        private final long intervalMs;
        private boolean running = true;

        public AutoSaveThread(RecipeManager<?> manager, String filePath, long intervalMs) {
            this.manager = manager;
            this.filePath = filePath;
            this.intervalMs = intervalMs;
            setDaemon(true); // Daemon thread stops when app exits
        }

        @Override
        public void run() {
            while (running) {
                try {
                    Thread.sleep(intervalMs);
                    FileHandler.saveRecipes((List<Recipe>) manager.getAllRecipes(), filePath);
                    System.out.println("💾 Auto-saved recipes to file.");
                } catch (IOException e) {
                    System.err.println("❌ Auto-save failed: " + e.getMessage());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    System.err.println("❌ Unexpected error: " + e.getMessage());
                }
            }
        }

        public void stopAutoSave() {
            running = false;
            this.interrupt();
        }
    }

    // =========== MAIN METHOD (for testing Module 3) ===========
    public static void main(String[] args) {
        System.out.println("=== Module 3: Generics & Multithreading Demo ===");

        RecipeManager<Recipe> manager = new RecipeManager<>();

        // Create sample recipes
        VegRecipe veg = new VegRecipe("Veg Pulao", "Cook rice with vegetables and spices.");
        veg.addIngredient(new Ingredient("Rice", "1 cup"));
        veg.addIngredient(new Ingredient("Carrot", "½ cup"));

        NonVegRecipe nonVeg = new NonVegRecipe("Fish Fry", "Marinate and fry the fish.");
        nonVeg.addIngredient(new Ingredient("Fish", "250g"));
        nonVeg.addIngredient(new Ingredient("Oil", "2 tbsp"));

        // Add them to manager
        manager.addRecipe(veg);
        manager.addRecipe(nonVeg);

        // Display all recipes
        System.out.println("\n📖 All Recipes:");
        for (Recipe r : manager.getAllRecipes()) {
            r.display();
            System.out.println();
        }

        // Start auto-save thread
        String filePath = "autosave_recipes.dat";
        AutoSaveThread autoSave = new AutoSaveThread(manager, filePath, 5000); // every 5s
        autoSave.start();

        System.out.println("\n💡 Auto-save thread started (saves every 5 seconds).");
        System.out.println("Wait for a few seconds to see auto-save in action...\n");

        try {
            Thread.sleep(12000); // wait 12 seconds to see a couple of auto-saves
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        autoSave.stopAutoSave();
        System.out.println("🛑 Auto-save thread stopped. Exiting program.");
    }
}