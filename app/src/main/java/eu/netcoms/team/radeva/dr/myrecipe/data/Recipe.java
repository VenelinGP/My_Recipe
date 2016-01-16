package eu.netcoms.team.radeva.dr.myrecipe.data;

public class Recipe {
    public int recipe_id;
    public String title;
    public String image_link;
    public int is_uploaded;

    public Recipe(){
        super();
    }

    public Recipe(int recipe_id, String title, String image_link){
        super();
        this.setRecipe_id(recipe_id);
        this.setTitle(title);
        this.setImage_link(image_link);
        this.setIs_uploaded(0);
    }

    public Integer getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(Integer recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
