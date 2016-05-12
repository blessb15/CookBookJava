import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class User {
  private String name;
  private int id;
  private List<Recipe> userRecipes = new List<Recipe>();

  public User(String name){
    this.name = name;
  }
  public String getName(){
    return name;
  }

  public ArrayList<Recipe> getMyRecipes(){
    return userRecipes;
  }

  public void addRecipe(Recipe recipe){
    userRecipes.add(recipe);

  }

}
