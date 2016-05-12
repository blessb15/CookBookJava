import org.sql2o.*;
import java.util.*;
import java.util.ArrayList;

public class Recipe {
  private String name;
  private String instructions;
  private int rating;
  private static int id;


  public Recipe(String name, String instructions){
    this.name = name;
    this.instructions = instructions;
  }

  public int getId(){
    return id;
  }

  public String getName(){
    return name;
  }

  public String getInstructions(){
    return instructions;
  }

  public int getRating(){
    return rating;
  }

  public void rate(int num){
    this.rating += num;
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO recipes (rating) VALUES (:rating)";
      con.createQuery(sql)
      .addParameter("rating", num)
      .executeUpdate();
    }
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO recipes (name, instructions) VALUES (:name, :instructions)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("instructions", this.instructions)
      .executeUpdate()
      .getKey();
    }
  }

  public static Recipe find(int id){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM recipes WHERE id = :id";
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Recipe.class);
    }
  }

  @Override
  public boolean equals(Object otherRecipe) {
    if (!(otherRecipe instanceof Recipe)){
      return false;
    } else {
      Recipe newRecipe = (Recipe) otherRecipe;
      return newRecipe.getName().equals(this.getName())
       && newRecipe.getInstructions().equals(this.getInstructions())
       && newRecipe.getId() == (this.getId())
       && newRecipe.getRating() == (this.getRating());
    }
  }

  public static List<Recipe> all(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM recipes";
      return con.createQuery(sql).executeAndFetch(Recipe.class);
    }
  }

  public void addIngredient(Ingredient ingredient){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO cookbook (recipe_id, ingredient_id) VALUES (:recipe_id, :ingredient_id)";
      con.createQuery(sql)
      .addParameter("recipe_id", this.getId())
      .addParameter("ingredient_id", ingredient.getId())
      .executeUpdate();
    }
  }

  public List<Ingredient> getIngredients(){
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT ingredient_id FROM cookbook WHERE recipe_id = :recipe_id";
      List<Integer> ingredient_ids = con.createQuery(joinQuery)
      .addParameter("recipe_id", this.getId())
      .executeAndFetch(Integer.class);

      List<Ingredient> ingredients = new ArrayList<Ingredient>();

      for(Integer ingredient_id : ingredient_ids) {
        String sql = "SELECT * FROM ingredients WHERE id = :ingredient_id";
        Ingredient ingredient = con.createQuery(sql)
        .addParameter("ingredient_id", ingredient_id)
        .executeAndFetchFirst(Ingredient.class);
        ingredients.add(ingredient);
      }
      return ingredients;
    }
  }

  public void addCategory(Category category){
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO category_recipes (category_id, recipe_id) VALUES (:category_id, :recipe_id)";
      con.createQuery(sql)
      .addParameter("category_id", category.getId())
      .addParameter("recipe_id", this.getId())
      .executeUpdate();
    }
  }

  public List<Category> getCategories(){
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT category_id FROM category_recipes WHERE recipe_id = :recipe_id";
      List<Integer> category_ids = con.createQuery(joinQuery)
      .addParameter("recipe_id", this.getId())
      .executeAndFetch(Integer.class);

      List<Category> categories = new ArrayList<Category>();

      for(Integer category_id : category_ids) {
        String sql = "SELECT * FROM categories WHERE id = :category_id";
        Category category = con.createQuery(sql)
        .addParameter("category_id", category_id)
        .executeAndFetchFirst(Category.class);
        categories.add(category);
      }
      return categories;
    }
  }
}
