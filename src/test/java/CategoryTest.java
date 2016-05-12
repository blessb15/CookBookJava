import org.sql2o.*;
import org.junit.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class CategoryTest {


  @Before
  public void setUp(){
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/cook_book_test", null, null);
  }

  @After
  public void teardown(){
    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM categories*";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void Category_CategoryInstantiatesCorrectly_true() {
    Category newCategory = new Category("Lunch");
    newCategory.save();
    assertTrue(newCategory instanceof Category);
  }
}
