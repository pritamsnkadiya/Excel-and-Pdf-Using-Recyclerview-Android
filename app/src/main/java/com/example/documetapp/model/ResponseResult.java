package com.example.documetapp.model;

import java.io.Serializable;
import java.util.List;

public class ResponseResult implements Serializable {

    public String id;
    public String message;
    public List<UserList> userList;
    public List<AidsList> aids;
}
