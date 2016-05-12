import java.util.*;
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");


      get("/", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("recipes", Recipe.all());
        model.put("template", "templates/home.vtl");
        return new ModelAndView(model, "templates/layout.vtl");
      }, new VelocityTemplateEngine());

      post("/recipes", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();

        String userInputName = request.queryParams("name");
        String userInputCategory = request.queryParams("category");
        String userInputIngredient1 = request.queryParams("ingredient1");
        String userInputIngredient2 = request.queryParams("ingredient2");
        String userInputIngredient3 = request.queryParams("ingredient3");
        String userInputIngredient4 = request.queryParams("ingredient4");
        String userInputIngredient5 = request.queryParams("ingredient5");
        String userInputDirections = request.queryParams("directions");

        Recipe newRecipe = new Recipe(userInputName, userInputDirections);
        newRecipe.save();
        Category newCategory = new Category(userInputCategory);
        newRecipe.addCategory(newCategory);
        Ingredient newIngredient1 = new Ingredient(userInputIngredient1);
        newIngredient1.save();
        newRecipe.addIngredient(newIngredient1);
        Ingredient newIngredient2 = new Ingredient(userInputIngredient2);
        newIngredient2.save();
        newRecipe.addIngredient(newIngredient2);
        Ingredient newIngredient3 = new Ingredient(userInputIngredient3);
        newIngredient3.save();
        newRecipe.addIngredient(newIngredient3);
        Ingredient newIngredient4 = new Ingredient(userInputIngredient4);
        newIngredient4.save();
        newRecipe.addIngredient(newIngredient4);
        Ingredient newIngredient5 = new Ingredient(userInputIngredient5);
        newIngredient5.save();
        newRecipe.addIngredient(newIngredient5);

        model.put("newRecipe", newRecipe);
        model.put("template", "templates/detector.vtl");
        return new ModelAndView(model, "templates/layout.vtl");
      }, new VelocityTemplateEngine());
  }

}
