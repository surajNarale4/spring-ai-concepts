package com.prod.auth;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditAwareImpl implements AuditorAware<String> {
    /**
     * Returns the current auditor of the application.
     *
     * @return the current auditor.
     *
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Suraj narale");
    }
}
