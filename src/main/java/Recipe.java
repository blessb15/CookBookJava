import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Recipe {
  private String name;
  private int rating;
  private static int id;
  private ArrayList<Ingredient> recipeIngredients = new ArrayList();

  public Recipe(String name, ArrayList<Ingredient> arry){
    this.name = name;
    this.rating = 0;
    this.recipeIngredients = arry;
  }

  public int getId(){
    return id;
  }
  public String getName(){
    return name;
  }

  public void rate(int num){
    this.rating = num;
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO recipes (rating) VALUES (:rating)";
      con.createQuery(sql)
      .addParameter("rating", num)
      .executeUpdate();
    }
  } // TEST THIS

  public int getRating(){
    return rating;
  } // TEST THIS

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO recipes (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .executeUpdate()
      .getKey();


    for(Ingredient ingredients : recipeIngredients){
      String sql2 = "INSERT INTO cookbook (ingredient_id, recipe_id) VALUES (:ingredient_id, :recipe_id)";
      con.createQuery(sql2)
      .addParameter("ingredient_id", ingredients.getId())
      .addParameter("recipe_id", this.getId())
      .executeUpdate();
    }
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
      return newRecipe.getName().equals(this.getName());
    }
  }

  public static List<Recipe> all(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM recipes";
      return con.createQuery(sql).executeAndFetch(Recipe.class);
    }
  }

  public void addIngredient(String ingredient){
    Ingredient addIngredient = new Ingredient(ingredient);
    addIngredient.save();
    recipeIngredients.add(addIngredient);
     try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO cookbook (ingredient_id, recipe_id) VALUES (:ingredient_id, :recipe_id)";
      con.createQuery(sql)
      .addParameter("ingredient_id", addIngredient.getId())
      .addParameter("recipe_id", this.getId())
      .executeUpdate();
    }
  }

  public List<Ingredient> getIngredientsWithData(){
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
