package com.askthem.user.dto.request;

import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class UserListRequest {
    private String fromDate;
    private String toDate;
    public UserListRequest(){}
    public UserListRequest(String from, String to){
        this.fromDate = from;
        this.toDate= to;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }
}
