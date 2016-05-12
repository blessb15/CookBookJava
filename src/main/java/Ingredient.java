import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Ingredient {
  private String name;
  private static int id;

  public Ingredient(String name){
    this.name = name;
  }

  public int getId(){
    return id;
  }
  public String getName(){
    return name;
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO ingredients (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .executeUpdate()
      .getKey();
    }
  }

  public static Ingredient find(int id){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM ingredients WHERE id = :id";
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Ingredient.class);
    }
  }

  @Override
  public boolean equals(Object otherIngredient) {
    if (!(otherIngredient instanceof Ingredient)){
      return false;
    } else {
      Ingredient newIngredient = (Ingredient) otherIngredient;
      return newIngredient.getName().equals(this.getName());
    }
  }

  public static List<Ingredient> all(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM ingredients";
      return con.createQuery(sql).executeAndFetch(Ingredient.class);
    }
  }
}
