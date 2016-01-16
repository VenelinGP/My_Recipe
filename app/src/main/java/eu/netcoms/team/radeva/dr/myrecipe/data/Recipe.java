package eu.netcoms.team.radeva.dr.myrecipe.data;

public class Recipe {
    public int recipeId;
    public String name;
    public String description;
    public String image_link;
    public int is_uploaded;

    public Recipe(){
        super();
    }

    public Recipe(int recipeId, String name, String description){
        super();
        this.setRecipeId(recipeId);
        this.setName(name);
        this.setDescription(description);
        this.setImage_link(image_link);
        this.setIs_uploaded(0);
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
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

    public int getIs_uploaded() {
        return is_uploaded;
    }

    public void setIs_uploaded(int is_uploaded) {
        this.is_uploaded = is_uploaded;
    }
}
