package resources;

public enum APIResources {
    addPlaceAPI("/maps/api/place/add/json"),
    getPlaceAPI("/maps/api/place/get/json"),
    deletePlaceAPI("/maps/api/place/add/json");
    private String value;
    // as soon constructor called value is loaded from the external file assigned
    // to local "value" to which we use internal method return get "value"
    APIResources(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    @Override
    public String toString(){
        return this.getValue();
    }
}
