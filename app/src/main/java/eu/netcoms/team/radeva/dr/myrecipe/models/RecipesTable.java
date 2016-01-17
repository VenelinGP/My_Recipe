package eu.netcoms.team.radeva.dr.myrecipe.models;

import com.telerik.everlive.sdk.core.model.base.DataItem;
import com.telerik.everlive.sdk.core.serialization.ServerProperty;
import com.telerik.everlive.sdk.core.serialization.ServerType;

@ServerType("recipes")
public class RecipesTable extends DataItem {

    @ServerProperty("recipe_Id")
    private Integer recipe_id;

    @ServerProperty("name")
    private String name;

    @ServerProperty("description")
    private String description;

    @ServerProperty("image_link")
    private String image_link;

    @ServerProperty("is_uploaded")
    private Integer is_uploaded;

    public Integer getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(Integer recipe_id) {
        this.recipe_id = recipe_id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public Integer getIs_uploaded() {
        return is_uploaded;
    }

    public void setIs_uploaded(Integer is_uploaded) {
        this.is_uploaded = is_uploaded;
    }

}
