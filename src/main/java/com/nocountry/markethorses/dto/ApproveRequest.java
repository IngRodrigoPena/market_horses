package com.nocountry.markethorses.dto;

public class ApproveRequest {
    private Long userId;
    private String comment;

    public ApproveRequest() {}

    public Long getUserId() {
        return userId;
    }

    public String getComment() {
        return comment;
    }

}
