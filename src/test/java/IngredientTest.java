import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class IngredientTest {


  @Before
  public void setUp(){
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/cook_book_test", null, null);
  }

  @After
  public void teardown(){
    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM ingredients *";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void Ingredient_recipeInstantiatesCorrectly_true() {
    Ingredient newIngredient = new Ingredient("apple");
    assertTrue(newIngredient instanceof Ingredient);
  }

  @Test
  public void Ingredient_GetName() {
    Ingredient newIngredient = new Ingredient("apple");
    assertEquals("apple", newIngredient.getName());
  }

  @Test
  public void Ingredient_recipeSavesToDatabase(){
    Ingredient newIngredient = new Ingredient("apple");
    newIngredient.save();
    assertEquals(newIngredient, Ingredient.find(newIngredient.getId()));
  }

  @Test
  public void Ingredient_allReturnsAll(){
    Ingredient newIngredient = new Ingredient("apple");
    newIngredient.save();
    assertEquals(1, Ingredient.all().size());
  }

}
