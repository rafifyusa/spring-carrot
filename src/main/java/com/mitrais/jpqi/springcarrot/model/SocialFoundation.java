package com.mitrais.jpqi.springcarrot.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document("socialFoundation")
public class SocialFoundation {
    @Id
    private String id;
    private String name;
    private Boolean status; //TRUE if available, FALSE if closed
    private double total_carrot;
    private double min_carrot;
    private String description; // describe why is it closed, or for other details information

    public SocialFoundation(){}

    public SocialFoundation(String id, String name, Boolean status, double total_carrot, double min_carrot, String description){
        this.id = id;
        this.name = name;
        this.status = status;
        this.total_carrot = total_carrot;
        this.min_carrot = min_carrot;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public double getTotal_carrot() {
        return total_carrot;
    }

    public void setTotal_carrot(double total_carrot) {
        this.total_carrot = total_carrot;
    }

    public double getMin_carrot() {
        return min_carrot;
    }

    public void setMin_carrot(double min_carrot) {
        this.min_carrot = min_carrot;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
