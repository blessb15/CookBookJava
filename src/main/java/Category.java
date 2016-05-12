import org.sql2o.*;

public class Category {
  private String name;
  private static int id;

  public Category(String name){
    this.name = name;
  }

  public int getId(){
    return id;
  }

  public String getName(){
    return name;
  }

  @Override
  public boolean equals(Object otherCategory) {
    if (!(otherCategory instanceof Category)){
      return false;
    } else {
      Category newCategory = (Category) otherCategory;
      return newCategory.getName().equals(this.getName()) && newCategory.getId() == (this.getId());
    }
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO categories (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .executeUpdate()
      .getKey();
      }
    }

  public static Category find(int id){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM categories WHERE id = :id";
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Category.class);
    }
  }

  public void addRecipe(Recipe recipe){
     try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO cookbook (category_id, recipe_id) VALUES (:category_id, :recipe_id)";
      con.createQuery(sql)
      .addParameter("category_id", this.getId())
      .addParameter("recipe_id", recipe.getId())
      .executeUpdate();
    }
  }
}
