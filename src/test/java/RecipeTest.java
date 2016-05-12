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
      String sql2 = "DELETE FROM cookbook *";
      con.createQuery(sql2).executeUpdate();
    }
  }

  @Test
  public void Recipe_recipeInstantiatesCorrectly_true() {
    Recipe newRecipe = new Recipe("Drunken Noodles", "Shake and Bake");
    newRecipe.save();
    assertTrue(newRecipe instanceof Recipe);
  }

  @Test
  public void Recipe_GetName() {
    Recipe newRecipe = new Recipe("Drunken Noodles", "Shake and Bake");
    newRecipe.save();
    assertEquals("Drunken Noodles", newRecipe.getName());
  }

  @Test
  public void Recipe_recipeSavesToDatabase(){
    Recipe newRecipe = new Recipe("Drunken Noodles", "Shake and Bake");
    newRecipe.save();
    assertEquals(newRecipe, Recipe.find(newRecipe.getId()));
  }

  @Test
  public void Recipe_allReturnsAll(){
    Recipe newRecipe = new Recipe("Drunken Noodles", "Shake and Bake");
    newRecipe.save();
    assertEquals(1, Recipe.all().size());
  }

  @Test
  public void Recipe_addIngredients(){
    Recipe newRecipe = new Recipe("Drunken Noodles", "Shake and Bake");
    newRecipe.save();
    Ingredient newIngredient = new Ingredient("butter");
    newIngredient.save();
    newRecipe.addIngredient(newIngredient);
    Ingredient newIngredient2 = new Ingredient("soy sauce");
    newIngredient2.save();
    newRecipe.addIngredient(newIngredient2);
    Ingredient newIngredient3 = new Ingredient("salt");
    newIngredient3.save();
    newRecipe.addIngredient(newIngredient3);
    assertEquals(3, newRecipe.getIngredients().size());
  }

  @Test
  public void Recipe_getRating(){
    Recipe newRecipe = new Recipe("Drunken Noodles", "Shake and Bake");
    newRecipe.save();
    newRecipe.rate(5);
    assertTrue(newRecipe.getRating() == 5);
  }

  @Test
  public void addCategory(){
    Recipe newRecipe = new Recipe("Drunken Noodles", "Shake and Bake");
    newRecipe.save();
    Category newCategory2 = new Category("Noodles");
    newRecipe.addCategory(newCategory2);
    Category newCategory = new Category("Pasta");
    newRecipe.addCategory(newCategory);
    assertTrue(newRecipe.getCategories().size() == 2);
  }
}
