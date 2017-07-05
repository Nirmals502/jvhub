package com.example.deepsingh.jbhub.Service_handler;

public interface SERVER {

    String Server = "http://app.jvhub.co.uk";

    String LOGIN = Server + "/api/users/login";
    String SIGNUP = Server + "/api/users/register";
    String APP_SETUP_API = Server + "/api/users/appsetup";
    String CHANGE_IMAGE = "http://app.jvhub.co.uk/api/users/profileimage";
    String CHECKIN = Server + "/api/users/checkin";
    String SET_USER_SEARCH_CRITERIA = "http://app.jvhub.co.uk/api/UserCriteria/edit";
    String CREATE_PROPERTY = "http://app.jvhub.co.uk/api/properties/create";
    String SEARCH_RESULT = "http://app.jvhub.co.uk/api/properties/";
    String Get_Property_Detail_By_ID = "http://app.jvhub.co.uk/api/properties/show/";
    String UPDATE_PROFILE = "http://app.jvhub.co.uk/api/users/profileupdate";
    String Contact_Us ="http://app.jvhub.co.uk/api/users/contactus";

}