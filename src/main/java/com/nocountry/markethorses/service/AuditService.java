package com.nocountry.markethorses.service;

import com.nocountry.markethorses.domain.audit.AuditLog;
import com.nocountry.markethorses.domain.audit.AuditAction;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuditService {

    private final List<AuditLog>logs = new ArrayList<>();
    private Long nextId = 1L;

    public void register(Long actorId, AuditAction action, String entity) {

        AuditLog log = new AuditLog(
                nextId++,
                actorId,
                action,
                entity,
                LocalDateTime.now()
        );
        logs.add(log);
    }

    public List<AuditLog> getAll(){
           return List.copyOf(logs);
    }

}
