package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
        log.debug("Loading bootstrap data");
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>(2);

        //get UOMs
        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");
        if(!eachUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM not found");
        }

        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon");
        if(!tableSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM not found");
        }

        Optional<UnitOfMeasure> teaSpoonOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        if(!teaSpoonOptional.isPresent()){
            throw new RuntimeException("Expected UOM not found");
        }

        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");
        if(!dashUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM not found");
        }

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");
        if(!pintUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM not found");
        }

        Optional<UnitOfMeasure> cupsUomOptional = unitOfMeasureRepository.findByDescription("Cup");
        if(!cupsUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM not found");
        }

        //get optionals
        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure teaSpoonUom = teaSpoonOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();
        UnitOfMeasure cupUom = cupsUomOptional.get();

        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");
        if(!americanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected category not found");
        }

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");
        if(!mexicanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected category not found");
        }
        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();

        //Yummy Giuac
        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("1. cut avocado" + "\n" + "2. Lore ipsum");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("read more" + "\n" + "in internet");

        guacRecipe.setNotes(guacNotes);
        //guacNotes.setRecipe(guacRecipe);


        guacRecipe.addIngredient(new Ingredient("ripe avocado", new BigDecimal(2),eachUom));
        guacRecipe.addIngredient(new Ingredient("kosher salt", new BigDecimal(.5),tableSpoonUom));

        guacRecipe.getCategories().add(mexicanCategory);
        guacRecipe.getCategories().add(americanCategory);
        recipes.add(guacRecipe);

        //Yummy tacos
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Perfect Tacos");
        tacosRecipe.setPrepTime(9);
        tacosRecipe.setCookTime(10);
        tacosRecipe.setDifficulty(Difficulty.MODERATE);
        tacosRecipe.setDirections("1.take chicken" + "\n" + "2. Lore ipsum" + "\n" + "3.bullshit");

        Notes tacosNotes = new Notes();
        tacosNotes.setRecipeNotes("ask" + "\n" + "your mom");
        tacosRecipe.setNotes(tacosNotes);
        //tacosNotes.setRecipe(tacosRecipe);

        tacosRecipe.addIngredient(new Ingredient("chicken", new BigDecimal(2),eachUom));
        tacosRecipe.addIngredient(new Ingredient("garlic", new BigDecimal(5),cupUom));

        tacosRecipe.getCategories().add(americanCategory);
        tacosRecipe.getCategories().add(mexicanCategory);

        recipes.add(guacRecipe);
        recipes.add(tacosRecipe);

        return recipes;
    }
}
