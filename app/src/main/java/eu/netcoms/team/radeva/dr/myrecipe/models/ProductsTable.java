package eu.netcoms.team.radeva.dr.myrecipe.models;

public class ProductsTable {

    private Integer recipe_id;

    private String product;

    public String getProduct() {
        return product;
    }

    public void setName(String product) {
        this.product = product;
    }

    public Integer getRecipeId() {
        return recipe_id;
    }

    public void setRecipeId(Integer recipe_id) {
        this.recipe_id = recipe_id;
    }
}
