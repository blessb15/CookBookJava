import org.sql2o.*;
import java.util.*;
import java.util.ArrayList;

public class Recipe {
  private String name;
  private int rating;
  private static int id;


  public Recipe(String name){
    this.name = name;
    this.rating = 0;
  }

  public int getId(){
    return id;
  }

  public String getName(){
    return name;
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
      String sql = "INSERT INTO recipes (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
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
      return newRecipe.getName().equals(this.getName()) &&
      newRecipe.getId()==(this.getId());
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
      String sql = "INSERT INTO cookbook (ingredient_id, recipe_id) VALUES (:ingredient_id, :recipe_id)";
      con.createQuery(sql)
      .addParameter("ingredient_id", ingredient.getId())
      .addParameter("recipe_id", this.getId())
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
      String sql = "INSERT INTO cookbook (category_id, recipe_id) VALUES (:category_id, :recipe_id)";
      con.createQuery(sql)
      .addParameter("category_id", category.getId())
      .addParameter("recipe_id", this.getId())
      .executeUpdate();
    }
  }

  public List<Ingredient> getCategories(){
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
}
