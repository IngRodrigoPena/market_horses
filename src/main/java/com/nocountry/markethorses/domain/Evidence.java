package com.nocountry.markethorses.domain;

public class Evidence {

    private final Long id;
    private final EvidenceType type;
    private final String fileUrl;
    private EvidenceStatus status;

    public Evidence(Long id, EvidenceType type, String fileUrl) {
        this.id = id;
        this.type = type;
        this.fileUrl = fileUrl;
        this.status = EvidenceStatus.UPLOADED;
    }

    public void markAsPendingVerification() {
        if (this.status != EvidenceStatus.UPLOADED) {
            throw new IllegalStateException("Evidence cannot move to verification from current state");
        }
        this.status = EvidenceStatus.PENDIENTE_VERIFICACION;
    }

    //getters and Setters
    public Long getId() {
        return id;
    }

    public EvidenceType getType() {
        return type;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public EvidenceStatus getStatus() {
        return status;
    }

    /*
    public void setStatus(EvidenceStatus status) {
        this.status = status;
    }*/
}
