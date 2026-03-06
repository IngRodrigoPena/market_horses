package com.nocountry.markethorses.dto;

public class EvidenceRequest {

    private Long userId;
    private String fileUrl;
    private String type;

    public EvidenceRequest() {}

    public Long getUserId() {
        return userId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getType() {
        return type;
    }

}
