package tweet;

import twitter4j.Status;

public class Tweet {
    private long id;
    private String username;
    private String text;
    private String date;
    private Double latitude;
    private Double longitude;
    private String userPicURL;
    private String category;
        
    public Tweet(long id, String username, String text, 
                String date, Double latitude, Double longitude, String userPicURL) {
        this.id = id;
        this.username = username;
        this.text = text;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userPicURL = userPicURL;
    }
    
    public Tweet(long id, String username, String text, 
                String date, String latitude, String longitude) {
        this(id, username, text, date, (Double) null, (Double) null, "");
        
        if (!latitude.isEmpty()) {
            this.latitude = Double.valueOf(latitude);
        }
        if (!longitude.isEmpty()) {
            this.longitude = Double.valueOf(longitude);
        }
    }

    public String getUsername() {
        return username;
    }
    
    public String getText() {
        return text;
    }
    
    public String getCleanText() {
        return Category.cleanUp(text);
    }
    
    public String getDate() {
        return date;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
        
    public Category getCategory() {
        return Category.extract(text);
    }
    
    public boolean isSingleCategory() {
        int count = Category.countCategories(text);
        return count == 1;
    }
    
    public String getUserPicURL(){
    	return userPicURL;
    }

    public String getFinalCategory(){
    	return category;
    }
    
    public void setFinalCategory(String category){
    	this.category = category;
    }
}
