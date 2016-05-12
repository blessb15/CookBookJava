import org.sql2o.*;
import org.junit.*;
import java.util.ArrayList;
import java.util.List;
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
    Recipe newRecipe = new Recipe("Drunken Noodles");
    newRecipe.save();
    assertTrue(newRecipe instanceof Recipe);
  }

  @Test
  public void Recipe_GetName() {
    Recipe newRecipe = new Recipe("Drunken Noodles");
    newRecipe.save();
    assertEquals("Drunken Noodles", newRecipe.getName());
  }

  @Test
  public void Recipe_recipeSavesToDatabase(){
    Recipe newRecipe = new Recipe("Drunken Noodles");
    newRecipe.save();
    assertEquals(newRecipe, Recipe.find(newRecipe.getId()));
  }

  @Test
  public void Recipe_allReturnsAll(){
    Recipe newRecipe = new Recipe("Drunken Noodles");
    newRecipe.save();
    assertEquals(1, Recipe.all().size());
  }

  @Test
  public void Recipe_addIngredients(){
    Ingredient newIngredient = new Ingredient("soy sauce, butter");
    newIngredient.save();
    Recipe newRecipe = new Recipe("Drunken Noodles");
    newRecipe.save();
    newRecipe.addIngredient(newIngredient);
    assertTrue(newRecipe.getIngredients().size() == 1);
  }

  @Test
  public void Recipe_getRating(){
    Recipe newRecipe = new Recipe("Drunken Noodles");
    newRecipe.save();
    newRecipe.rate(5);
    assertTrue(newRecipe.getRating() == 5);
  }

}
