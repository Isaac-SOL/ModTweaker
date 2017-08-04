package com.blamejared.compat.botania.handlers;

import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.recipes.ShapedRecipe;
import minetweaker.mc1112.recipes.RecipeConverter;
import com.blamejared.compat.botania.BotaniaHelper;
import com.blamejared.compat.botania.lexicon.*;
import net.minecraft.entity.EntityList;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.*;
import vazkii.botania.api.recipe.*;
import vazkii.botania.common.lexicon.page.*;

import java.util.ArrayList;
import java.util.List;

import static com.blamejared.mtlib.helpers.InputHelper.*;

@ZenClass("mods.botania.Lexicon")
public class Lexicon {

    @ZenMethod
    public static void addBrewPage(String name, String entry, int page_number, String brew, IIngredient[] recipe, String bottomText) {
    	LexiconEntry lexiconEntry= BotaniaHelper.findEntry(entry);
    	if(lexiconEntry==null)
    	{
    		MineTweakerAPI.getLogger().logError("Cannot find lexicon entry "+entry);
    		return;
    	}
    	if(page_number>lexiconEntry.pages.size())
    	{
    		MineTweakerAPI.getLogger().logError("Page Number " + page_number + " out of bounds for "+entry);
    		return;
    	}
    	if(BotaniaAPI.getBrewFromKey(brew)==null)
    	{
    		MineTweakerAPI.getLogger().logError("Cannot find brew "+brew);
    		return;
    	}
    	RecipeBrew page_recipe=new RecipeBrew(BotaniaAPI.getBrewFromKey(brew),toObjects(recipe));
    	LexiconPage page=new PageBrew(page_recipe,name,bottomText);
        MineTweakerAPI.apply(new AddPage(name,lexiconEntry,page,page_number));
    }

    @ZenMethod
    public static void addCraftingPage(String name, String entry, int page_number, IItemStack[] outputs, IIngredient[][][] inputs) {
    	LexiconEntry lexiconEntry=BotaniaHelper.findEntry(entry);
    	if(lexiconEntry==null)
    	{
    		MineTweakerAPI.getLogger().logError("Cannot find lexicon entry "+entry);
    		return;
    	}
    	if(page_number>lexiconEntry.pages.size())
    	{
    		MineTweakerAPI.getLogger().logError("Page Number " + page_number + " out of bounds for "+entry);
    		return;
    	}
    	if(outputs.length!=inputs.length)
    	{
    		MineTweakerAPI.getLogger().logError("Length of input and output must match");
    		return;
    	}
    	List<IRecipe> recipes=new ArrayList<IRecipe>();
    	for(int i=0;i<outputs.length;i++)
    	{
    		recipes.add(RecipeConverter.convert(new ShapedRecipe(outputs[i],inputs[i],null,null,false)));
    	}
    	LexiconPage page=new PageCraftingRecipe(name, recipes);
        MineTweakerAPI.apply(new AddPage(name,lexiconEntry,page,page_number));
    }
    
    @ZenMethod
    public static void addElvenPage(String name, String entry, int page_number, IItemStack[] outputs, IIngredient[][] inputs) {
    	LexiconEntry lexiconEntry=BotaniaHelper.findEntry(entry);
    	if(lexiconEntry==null)
    	{
    		MineTweakerAPI.getLogger().logError("Cannot find lexicon entry "+entry);
    		return;
    	}
    	if(page_number>lexiconEntry.pages.size())
    	{
    		MineTweakerAPI.getLogger().logError("Page Number " + page_number + " out of bounds for "+entry);
    		return;
    	}
    	if(outputs.length!=inputs.length)
    	{
    		MineTweakerAPI.getLogger().logError("Length of input and output must match");
    		return;
    	}
    	List<RecipeElvenTrade> recipes=new ArrayList<RecipeElvenTrade>();
    	for(int i=0;i<outputs.length;i++)
    	{
    		//TODO test
			recipes.add(new RecipeElvenTrade(toStacks(outputs),toObjects(inputs[i])));
    	}


		LexiconPage page=new PageElvenRecipe(name,recipes);
        MineTweakerAPI.apply(new AddPage(name,lexiconEntry,page,page_number));
    }
    
    @ZenMethod
    public static void addEntityPage(String name, String entry, int page_number, String entity, int size) {
    	LexiconEntry lexiconEntry=BotaniaHelper.findEntry(entry);
    	if(lexiconEntry==null)
    	{
    		MineTweakerAPI.getLogger().logError("Cannot find lexicon entry "+entry);
    		return;
    	}
    	if(page_number>lexiconEntry.pages.size())
    	{
    		MineTweakerAPI.getLogger().logError("Page Number " + page_number + " out of bounds for "+entry);
    		return;
    	}
    	if(!EntityList.isRegistered(new ResourceLocation(entity)))	//I have no idea what I am doing
    	{
    		MineTweakerAPI.getLogger().logError("No such entity "+entity);
    		return;
    	}
    	LexiconPage page=new PageEntity(entity, entity, size);
        MineTweakerAPI.apply(new AddPage(name,lexiconEntry,page,page_number));
    }
    
    @ZenMethod
    public static void addImagePage(String name, String entry, int page_number, String resource) {
    	LexiconEntry lexiconEntry=BotaniaHelper.findEntry(entry);
    	if(lexiconEntry==null)
    	{
    		MineTweakerAPI.getLogger().logError("Cannot find lexicon entry "+entry);
    		return;
    	}
    	if(page_number>lexiconEntry.pages.size())
    	{
    		MineTweakerAPI.getLogger().logError("Page Number " + page_number + " out of bounds for "+entry);
    		return;
    	}
    	LexiconPage page=new PageImage(name, resource);
        MineTweakerAPI.apply(new AddPage(name,lexiconEntry,page,page_number));
    }
    
    @ZenMethod
    public static void addLorePage(String name, String entry, int page_number) {
    	LexiconEntry lexiconEntry=BotaniaHelper.findEntry(entry);
    	if(lexiconEntry==null)
    	{
    		MineTweakerAPI.getLogger().logError("Cannot find lexicon entry "+entry);
    		return;
    	}
    	if(page_number>lexiconEntry.pages.size())
    	{
    		MineTweakerAPI.getLogger().logError("Page Number " + page_number + " out of bounds for "+entry);
    		return;
    	}
    	LexiconPage page=new PageLoreText(name);
        MineTweakerAPI.apply(new AddPage(name,lexiconEntry,page,page_number));
    }

    @ZenMethod
    public static void addInfusionPage(String name, String entry, int page_number, IItemStack[] outputs, IIngredient[] inputs, int[] mana) {
    	LexiconEntry lexiconEntry=BotaniaHelper.findEntry(entry);
    	if(lexiconEntry==null)
    	{
    		MineTweakerAPI.getLogger().logError("Cannot find lexicon entry "+entry);
    		return;
    	}
    	if(page_number>lexiconEntry.pages.size())
    	{
    		MineTweakerAPI.getLogger().logError("Page Number " + page_number + " out of bounds for "+entry);
    		return;
    	}
    	if(outputs.length!=inputs.length || outputs.length!=mana.length)
    	{
    		MineTweakerAPI.getLogger().logError("Length of input and output must match");
    		return;
    	}
    	List<RecipeManaInfusion> recipes=new ArrayList<RecipeManaInfusion>();
    	for(int i=0;i<outputs.length;i++)
    	{
    		recipes.add(new RecipeManaInfusion(toStack(outputs[i]),toObject(inputs[i]),mana[i]));
    	}
    	LexiconPage page=new PageManaInfusionRecipe(name,recipes);
        MineTweakerAPI.apply(new AddPage(name,lexiconEntry,page,page_number));
    }

    @ZenMethod
    public static void addAlchemyPage(String name, String entry, int page_number, IItemStack[] outputs, IIngredient[] inputs, int[] mana) {
    	LexiconEntry lexiconEntry=BotaniaHelper.findEntry(entry);
    	if(lexiconEntry==null)
    	{
    		MineTweakerAPI.getLogger().logError("Cannot find lexicon entry "+entry);
    		return;
    	}
    	if(page_number>lexiconEntry.pages.size())
    	{
    		MineTweakerAPI.getLogger().logError("Page Number " + page_number + " out of bounds for "+entry);
    		return;
    	}
    	if(outputs.length!=inputs.length || outputs.length!=mana.length)
    	{
    		MineTweakerAPI.getLogger().logError("Length of input and output must match");
    		return;
    	}
    	List<RecipeManaInfusion> recipes=new ArrayList<RecipeManaInfusion>();
    	for(int i=0;i<outputs.length;i++)
    	{
    		RecipeManaInfusion current_recipe=new RecipeManaInfusion(toStack(outputs[i]),toObject(inputs[i]),mana[i]);
    		current_recipe.setAlchemy(true);
    		recipes.add(current_recipe);
    	}
    	LexiconPage page=new PageManaInfusionRecipe(name,recipes);
        MineTweakerAPI.apply(new AddPage(name,lexiconEntry,page,page_number));
    }

    @ZenMethod
    public static void addConjurationPage(String name, String entry, int page_number, IItemStack[] outputs, IIngredient[] inputs, int[] mana) {
    	LexiconEntry lexiconEntry=BotaniaHelper.findEntry(entry);
    	if(lexiconEntry==null)
    	{
    		MineTweakerAPI.getLogger().logError("Cannot find lexicon entry "+entry);
    		return;
    	}
    	if(page_number>lexiconEntry.pages.size())
    	{
    		MineTweakerAPI.getLogger().logError("Page Number " + page_number + " out of bounds for "+entry);
    		return;
    	}
    	if(outputs.length!=inputs.length || outputs.length!=mana.length)
    	{
    		MineTweakerAPI.getLogger().logError("Length of input and output must match");
    		return;
    	}
    	List<RecipeManaInfusion> recipes=new ArrayList<RecipeManaInfusion>();
    	for(int i=0;i<outputs.length;i++)
    	{
    		RecipeManaInfusion current_recipe=new RecipeManaInfusion(toStack(outputs[i]),toObject(inputs[i]),mana[i]);
    		current_recipe.setConjuration(true);
    		recipes.add(current_recipe);
    	}
    	LexiconPage page=new PageManaInfusionRecipe(name,recipes);
        MineTweakerAPI.apply(new AddPage(name,lexiconEntry,page,page_number));
    }

    @ZenMethod
    public static void addPetalPage(String name, String entry, int page_number, IItemStack[] outputs, IIngredient[][] inputs) {
    	LexiconEntry lexiconEntry=BotaniaHelper.findEntry(entry);
    	if(lexiconEntry==null)
    	{
    		MineTweakerAPI.getLogger().logError("Cannot find lexicon entry "+entry);
    		return;
    	}
    	if(page_number>lexiconEntry.pages.size())
    	{
    		MineTweakerAPI.getLogger().logError("Page Number " + page_number + " out of bounds for "+entry);
    		return;
    	}
    	if(outputs.length!=inputs.length)
    	{
    		MineTweakerAPI.getLogger().logError("Length of input and output must match");
    		return;
    	}
    	List<RecipePetals> recipes=new ArrayList<RecipePetals>();
    	for(int i=0;i<outputs.length;i++)
    	{
    		recipes.add(new RecipePetals(toStack(outputs[i]),toObjects(inputs[i])));
    	}
    	LexiconPage page=new PagePetalRecipe(name,recipes);
        MineTweakerAPI.apply(new AddPage(name,lexiconEntry,page,page_number));
    }

    @ZenMethod
    public static void addRunePage(String name, String entry, int page_number, IItemStack[] outputs, IIngredient[][] inputs, int[] mana) {
    	LexiconEntry lexiconEntry=BotaniaHelper.findEntry(entry);
    	if(lexiconEntry==null)
    	{
    		MineTweakerAPI.getLogger().logError("Cannot find lexicon entry "+entry);
    		return;
    	}
    	if(page_number>lexiconEntry.pages.size())
    	{
    		MineTweakerAPI.getLogger().logError("Page Number " + page_number + " out of bounds for "+entry);
    		return;
    	}
    	if(outputs.length!=inputs.length || outputs.length!=mana.length)
    	{
    		MineTweakerAPI.getLogger().logError("Length of input and output must match");
    		return;
    	}
    	List<RecipeRuneAltar> recipes=new ArrayList<RecipeRuneAltar>();
    	for(int i=0;i<outputs.length;i++)
    	{
    		recipes.add(new RecipeRuneAltar(toStack(outputs[i]),mana[i],toObjects(inputs[i])));
    	}
    	LexiconPage page=new PageRuneRecipe(name,recipes);
        MineTweakerAPI.apply(new AddPage(name,lexiconEntry,page,page_number));
    }

    @ZenMethod
    public static void addTextPage(String name, String entry, int page_number) {
    	LexiconEntry lexiconEntry=BotaniaHelper.findEntry(entry);
    	if(lexiconEntry==null)
    	{
    		MineTweakerAPI.getLogger().logError("Cannot find lexicon entry "+entry);
    		return;
    	}
    	if(page_number>lexiconEntry.pages.size())
    	{
    		MineTweakerAPI.getLogger().logError("Page Number " + page_number + " out of bounds for "+entry);
    		return;
    	}
    	LexiconPage page=new PageText(name);
        MineTweakerAPI.apply(new AddPage(name,lexiconEntry,page,page_number));
    }

    @ZenMethod
    public static void removePage(String entry, int page_number) {
    	LexiconEntry lexiconEntry=BotaniaHelper.findEntry(entry);
    	if(lexiconEntry==null)
    	{
    		MineTweakerAPI.getLogger().logError("Cannot find lexicon entry "+entry);
    		return;
    	}
    	if(page_number>=lexiconEntry.pages.size())
    	{
    		MineTweakerAPI.getLogger().logError("Page Number " + page_number + " out of bounds for "+entry);
    		return;
    	}
        MineTweakerAPI.apply(new RemovePage(lexiconEntry,page_number));
    }

    @ZenMethod
    public static void addEntry(String entry, String catagory, IItemStack stack) {
    	LexiconCategory lexiconCategory=BotaniaHelper.findCatagory(catagory);
    	if(lexiconCategory==null)
    	{
    		MineTweakerAPI.getLogger().logError("Cannot find lexicon category "+catagory);
    		return;
    	}
    	LexiconEntry lexiconEntry=new LexiconEntry(entry,lexiconCategory);
    	lexiconEntry.setIcon(toStack(stack));
        MineTweakerAPI.apply(new AddEntry(lexiconEntry));
    }
    @ZenMethod
    public static void removeEntry(String entry) {
    	LexiconEntry lexiconEntry=BotaniaHelper.findEntry(entry);
    	if(lexiconEntry==null)
    	{
    		MineTweakerAPI.getLogger().logError("Cannot find lexicon entry "+entry);
    		return;
    	}
        MineTweakerAPI.apply(new RemoveEntry(lexiconEntry));
    }
    
    @ZenMethod
    public static void setEntryKnowledgeType(String entry, String knowledgeType) {
    	LexiconEntry lexiconEntry=BotaniaHelper.findEntry(entry);
    	KnowledgeType type=BotaniaHelper.findKnowledgeType(knowledgeType);
    	if(lexiconEntry==null)
    	{
    		MineTweakerAPI.getLogger().logError("Cannot find lexicon entry "+entry);
    		return;
    	}
    	if(type==null)
    	{
    		MineTweakerAPI.getLogger().logError("Cannot find knowledge type "+knowledgeType);
    		return;
    	}
        MineTweakerAPI.apply(new SetEntryKnowledgeType(lexiconEntry,type));
    }
    
    @ZenMethod
    public static void addCategory(String name) {
    	LexiconCategory lexiconCategory=new LexiconCategory(name);
        MineTweakerAPI.apply(new AddCategory(lexiconCategory));
    }
    
    @ZenMethod
    public static void removeCategory(String name) {
    	LexiconCategory lexiconCategory=BotaniaHelper.findCatagory(name);
    	if(lexiconCategory==null)
    	{
    		MineTweakerAPI.getLogger().logError("Cannot find lexicon category "+name);
    		return;
    	}
        MineTweakerAPI.apply(new RemoveCategory(lexiconCategory));
    }

    @ZenMethod
    public static void setCategoryPriority(String name, int priority) {
    	LexiconCategory lexiconCategory=BotaniaHelper.findCatagory(name);
    	if(lexiconCategory==null)
    	{
    		MineTweakerAPI.getLogger().logError("Cannot find lexicon category "+name);
    		return;
    	}
        MineTweakerAPI.apply(new SetCategoryPriority(lexiconCategory,priority));
    }
    
    @ZenMethod
    public static void setCategoryIcon(String name, String icon) {
    	LexiconCategory lexiconCategory=BotaniaHelper.findCatagory(name);
    	if(lexiconCategory==null)
    	{
    		MineTweakerAPI.getLogger().logError("Cannot find lexicon category "+name);
    		return;
    	}
        MineTweakerAPI.apply(new SetCategoryIcon(lexiconCategory,icon));
    }

    @ZenMethod
    public static void addRecipeMapping(IItemStack stack, String Entry, int page) {
    	LexiconEntry lexiconEntry=BotaniaHelper.findEntry(Entry);
    	if(LexiconRecipeMappings.getDataForStack(toStack(stack))!=null)
    	{
    		MineTweakerAPI.getLogger().logError("There is already a recipe mapping for "+stack);
    		return;
    	}
    	if(lexiconEntry==null)
    	{
    		MineTweakerAPI.getLogger().logError("Cannot find lexicon entry "+Entry);
    		return;
    	}
    	if(lexiconEntry.pages.size()<page)
    	{
    		MineTweakerAPI.getLogger().logError("Not enough pages in "+Entry);
    		return;
    	}
        MineTweakerAPI.apply(new AddRecipeMapping(toStack(stack),lexiconEntry,page));
    }

    @ZenMethod
    public static void removeRecipeMapping(IItemStack stack) {
    	if(LexiconRecipeMappings.getDataForStack(toStack(stack))==null)
    	{
    		MineTweakerAPI.getLogger().logError("There isn't a recipe mapping for "+stack);
    		return;
    	}
        MineTweakerAPI.apply(new RemoveRecipeMapping(toStack(stack)));
    }
}
