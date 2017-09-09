package com.user.canopas.custom_contentprovider;

/**
 * Created by canopas on 26/08/17.
 */

public class User {
    String name;
    String contact_no;

    public User(String name, String contact_no) {
        this.name = name;
        this.contact_no = contact_no;
    }

    public String getName() {
        return name;
    }
    public String getContact_no() {
        return contact_no;
    }


}
