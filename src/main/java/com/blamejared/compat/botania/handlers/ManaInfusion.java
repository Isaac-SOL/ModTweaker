package com.blamejared.compat.botania.handlers;

import com.blamejared.api.annotations.Handler;
import com.blamejared.mtlib.helpers.LogHelper;
import com.blamejared.mtlib.utils.BaseListAddition;
import com.blamejared.mtlib.utils.BaseListRemoval;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeManaInfusion;

import java.util.LinkedList;
import java.util.List;

import static com.blamejared.mtlib.helpers.InputHelper.*;
import static com.blamejared.mtlib.helpers.StackHelper.matches;


@ZenClass("mods.botania.ManaInfusion")
@Handler("botania")
public class ManaInfusion {
    
    protected static final String name = "Botania Mana Infusion";
    
    @ZenMethod
    public static void addInfusion(IItemStack output, IIngredient input, int mana) {
        MineTweakerAPI.apply(new Add(new RecipeManaInfusion(toStack(output), toObject(input), mana)));
    }

    @ZenMethod
    public static void addAlchemy(IItemStack output, IIngredient input, int mana) {
        RecipeManaInfusion recipe = new RecipeManaInfusion(toStack(output), toObject(input), mana);
        recipe.setCatalyst(RecipeManaInfusion.alchemyState);
        MineTweakerAPI.apply(new Add(recipe));
    }

    @ZenMethod
    public static void addConjuration(IItemStack output, IIngredient input, int mana) {
        RecipeManaInfusion recipe = new RecipeManaInfusion(toStack(output), toObject(input), mana);
        recipe.setCatalyst(RecipeManaInfusion.conjurationState);
        MineTweakerAPI.apply(new Add(recipe));
    }

    private static class Add extends BaseListAddition<RecipeManaInfusion> {
        public Add(RecipeManaInfusion recipe) {
            super(ManaInfusion.name, BotaniaAPI.manaInfusionRecipes);
            recipes.add(recipe);
        }

        @Override
        public String getRecipeInfo(RecipeManaInfusion recipe) {
            return LogHelper.getStackDescription(recipe.getOutput());
        }

        @Override
        public String getJEICategory(RecipeManaInfusion recipe) {
            return "botania.manaPool";
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @ZenMethod
    public static void removeRecipe(IIngredient output) {
        // Get list of existing recipes, matching with parameter
        List<RecipeManaInfusion> recipes = new LinkedList<RecipeManaInfusion>();
        
        for (RecipeManaInfusion r : BotaniaAPI.manaInfusionRecipes) {
            if (r.getOutput() != null && matches(output, toIItemStack(r.getOutput()))) {
                recipes.add(r);
            }
        }
        
        // Check if we found the recipes and apply the action
        if(!recipes.isEmpty()) {
            MineTweakerAPI.apply(new Remove(recipes));
        } else {
            LogHelper.logWarning(String.format("No %s Recipe found for %s. Command ignored!", ManaInfusion.name, output.toString()));
        }
    }

    private static class Remove extends BaseListRemoval<RecipeManaInfusion> {
        public Remove(List<RecipeManaInfusion> recipes) {
            super(ManaInfusion.name, BotaniaAPI.manaInfusionRecipes, recipes);
        }

        @Override
        public String getRecipeInfo(RecipeManaInfusion recipe) {
            return LogHelper.getStackDescription(recipe.getOutput());
        }

        @Override
        public String getJEICategory(RecipeManaInfusion recipe) {
            return "botania.manaPool";
        }
    }
}
