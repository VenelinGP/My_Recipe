package eu.netcoms.team.radeva.dr.myrecipe.models;

import com.telerik.everlive.sdk.core.model.base.DataItem;
import com.telerik.everlive.sdk.core.serialization.ServerProperty;
import com.telerik.everlive.sdk.core.serialization.ServerType;

@ServerType("preparation_recipe")
public class PreparationRecipeTable extends DataItem {

    @ServerProperty("recipe_id")
    private Integer recipe_id;

    @ServerProperty("preparation")
    private String preparation;


    public Integer getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(Integer recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }
}
