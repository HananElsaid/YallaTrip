package iti.intake41.myapplication.models.Place.model;


public class Place{

    private float[] bbox;
    private String category;
    private String categoryTitle;
    private String completion;
    private int distance;
    private String highlightedTitle;
    private String highlightedVicinity;
    private String href;
    private String id;
    private float[] position;
    private String resultType;
    private String title;
    private String type;
    private String vicinity;

    public void setBbox(float[] bbox){
        this.bbox = bbox;
    }
    public float[] getBbox(){
        return this.bbox;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public String getCategory(){
        return this.category;
    }
    public void setCategoryTitle(String categoryTitle){
        this.categoryTitle = categoryTitle;
    }
    public String getCategoryTitle(){
        return this.categoryTitle;
    }
    public void setCompletion(String completion){
        this.completion = completion;
    }
    public String getCompletion(){
        return this.completion;
    }
    public void setDistance(int distance){
        this.distance = distance;
    }
    public int getDistance(){
        return this.distance;
    }
    public void setHighlightedTitle(String highlightedTitle){
        this.highlightedTitle = highlightedTitle;
    }
    public String getHighlightedTitle(){
        return this.highlightedTitle;
    }
    public void setHighlightedVicinity(String highlightedVicinity){
        this.highlightedVicinity = highlightedVicinity;
    }
    public String getHighlightedVicinity(){
        return this.highlightedVicinity;
    }
    public void setHref(String href){
        this.href = href;
    }
    public String getHref(){
        return this.href;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setPosition(float[] position){
        this.position = position;
    }
    public float[] getPosition(){
        return this.position;
    }
    public void setResultType(String resultType){
        this.resultType = resultType;
    }
    public String getResultType(){
        return this.resultType;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setVicinity(String vicinity){
        this.vicinity = vicinity;
    }
    public String getVicinity(){
        if(this.vicinity != null){
            this.vicinity = this.vicinity.replaceAll("\\<br/>", ", ");
        }
        return this.vicinity;
    }

}
