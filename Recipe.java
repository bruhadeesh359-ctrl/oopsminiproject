package narayana;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Module 1 - Core OOP
 * Classes: Recipe (abstract), VegRecipe, NonVegRecipe, Ingredient, User
 */

public abstract class Recipe implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int recipeCount = 0; // static member

    protected int id;
    protected String name;
    protected String category;
    protected List<Ingredient> ingredients;
    protected String instructions;

    public Recipe(String name, String category, String instructions) {
        this.id = ++recipeCount;
        this.name = name;
        this.category = category;
        this.instructions = instructions;
        this.ingredients = new ArrayList<>();
    }

    // Encapsulation: getters/setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getInstructions() { return instructions; }
    public List<Ingredient> getIngredients() { return ingredients; }

    public void addIngredient(Ingredient ing) { ingredients.add(ing); }

    // Polymorphic method
    public abstract void display();

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    // ✅ MAIN METHOD (for testing this module)
    public static void main(String[] args) {
        // Create recipes
        VegRecipe veg = new VegRecipe("Tomato Soup", "Boil tomatoes and blend.");
        veg.addIngredient(new Ingredient("Tomato", "4 pcs"));
        veg.addIngredient(new Ingredient("Salt", "1 tsp"));

        NonVegRecipe nonVeg = new NonVegRecipe("Grilled Chicken", "Grill marinated chicken.");
        nonVeg.addIngredient(new Ingredient("Chicken", "500g"));
        nonVeg.addIngredient(new Ingredient("Spices", "2 tbsp"));

        // Display them
        veg.display();
        System.out.println();
        nonVeg.display();

        // Show User example
        User user = new User(1, "Devesh", "devesh@example.com");
        System.out.println("\nUser Details:");
        System.out.println("ID: " + user.getUserId());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
    }
}

// --- VegRecipe.java (subclass)
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
        for (Ingredient ing : ingredients) {
            System.out.println(" - " + ing);
        }
    }
}

// --- NonVegRecipe.java (subclass)
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
        for (Ingredient ing : ingredients) {
            System.out.println(" - " + ing);
        }
    }
}

// --- Ingredient.java
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

// --- User.java
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
