package com.nocountry.markethorses.domain;

public class Verification {
    Long id;
    Long adId;
    VerificationStatus status;

    public Verification(Long id, Long adId){
        this.id = id;
        this.adId = adId;
        this.status = VerificationStatus.INICIADA;
    }

    //getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public VerificationStatus getStatus() {
        return status;
    }

    public void setStatus(VerificationStatus status) {
        this.status = status;
    }
}

