import org.sql2o.*;
import org.junit.*;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class RecipeTest {


  @Before
  public void setUp(){
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/cook_book_test", null, null);
  }

  @After
  public void teardown(){
    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM recipes *";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void Recipe_recipeInstantiatesCorrectly_true() {
    ArrayList<Ingredient> ingredients = new ArrayList();
    Ingredient newIngredient = new Ingredient("soy sauce");
    Ingredient anotherIngredient = new Ingredient("butter");
    newIngredient.save();
    anotherIngredient.save();
    ingredients.add(newIngredient);
    ingredients.add(anotherIngredient);
    Recipe newRecipe = new Recipe("Drunken Noodles", ingredients);
    newRecipe.save();
    assertTrue(newRecipe instanceof Recipe);
  }

  @Test
  public void Recipe_GetName() {
    ArrayList<Ingredient> ingredients = new ArrayList();
    Ingredient newIngredient = new Ingredient("soy sauce");
    Ingredient anotherIngredient = new Ingredient("butter");
    newIngredient.save();
    anotherIngredient.save();
    ingredients.add(newIngredient);
    ingredients.add(anotherIngredient);
    Recipe newRecipe = new Recipe("Drunken Noodles", ingredients);
    newRecipe.save();
    assertEquals("Drunken Noodles", newRecipe.getName());
  }

  @Test
  public void Recipe_recipeSavesToDatabase(){
    ArrayList<Ingredient> ingredients = new ArrayList();
    Ingredient newIngredient = new Ingredient("soy sauce");
    Ingredient anotherIngredient = new Ingredient("butter");
    newIngredient.save();
    anotherIngredient.save();
    ingredients.add(newIngredient);
    ingredients.add(anotherIngredient);
    Recipe newRecipe = new Recipe("Drunken Noodles", ingredients);
    newRecipe.save();
    assertEquals(newRecipe, Recipe.find(newRecipe.getId()));
  }

  @Test
  public void Recipe_allReturnsAll(){
    ArrayList<Ingredient> ingredients = new ArrayList();
    Ingredient newIngredient = new Ingredient("soy sauce");
    Ingredient anotherIngredient = new Ingredient("butter");
    newIngredient.save();
    anotherIngredient.save();
    ingredients.add(newIngredient);
    ingredients.add(anotherIngredient);
    Recipe newRecipe = new Recipe("Drunken Noodles", ingredients);
    newRecipe.save();
    assertEquals(1, Recipe.all().size());
  }

  @Test
  public void Recipe_getIngredientsReturnsAll(){
    ArrayList<Ingredient> ingredients = new ArrayList();
    Recipe newRecipe = new Recipe("Drunken Noodles", ingredients);
    newRecipe.save();
    newRecipe.addIngredient("noodles");
    assertEquals("noodles", newRecipe.getIngredientsWithData().get(0).getName());
  }

  @Test
  public void Recipe_getRating(){
    ArrayList<Ingredient> ingredients = new ArrayList();
    Recipe newRecipe = new Recipe("Drunken Noodles", ingredients);
    newRecipe.save();
    newRecipe.rate(5);
    assertTrue(newRecipe.getRating() == 5);
  }

}
