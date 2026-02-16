package com.nocountry.markethorses.controller;

import com.nocountry.markethorses.domain.audit.AuditLog;
import com.nocountry.markethorses.service.AuditService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService){
        this.auditService = auditService;
    }

    @GetMapping
    public List<AuditLog> getLogs(){
        return auditService.getAll();
    }
}
