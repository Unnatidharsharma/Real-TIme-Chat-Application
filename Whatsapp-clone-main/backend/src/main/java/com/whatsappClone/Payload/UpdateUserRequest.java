package com.whatsappClone.Payload;

public class UpdateUserRequest {

    private String name;

    private String profile;

    private String language;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public UpdateUserRequest() {
    }

     public UpdateUserRequest(String name, String profile, String language) {
         this.name = name;
         this.profile = profile;
         this.language = language;
     }

    @Override
    public String toString() {
        return "UpdateUserRequest [name=" + name + ", profile=" + profile + ", language=" + language + "]";
    }

}
