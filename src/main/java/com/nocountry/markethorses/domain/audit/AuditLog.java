package com.nocountry.markethorses.domain.audit;

import java.time.LocalDateTime;

public class AuditLog {

    private final Long id;
    private final Long actorId;
    private final AuditAction action;
    private final String entity;
    private final LocalDateTime timestamp;

    public AuditLog(Long id,
                    Long actorId,
                    AuditAction action,
                    String entity,
                    LocalDateTime timestamp){
        this.id = id;
        this.actorId = actorId;
        this.action = action;
        this.entity = entity;
        this.timestamp = timestamp;
    }

    //geters
    public Long getId() {
        return id;
    }

    public Long getActorId() {
        return actorId;
    }

    public AuditAction getAction() {
        return action;
    }

    public String getEntity() {
        return entity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
