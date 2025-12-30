package com.canbagi.common.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component(value = "springSecurityAuditorAware")
@RequiredArgsConstructor
@Slf4j
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        log.info("[AUDIT] Getting current user");

         var context = SecurityContextHolder.getContext();
         var authentication = context.getAuthentication();

         if (authentication == null || !authentication.isAuthenticated()) {
             log.info("[AUDIT] No authenticated user");
             return Optional.empty();
         }

         var currentUser = authentication.getName();

         if ("anonymousUser".equalsIgnoreCase(currentUser)){
             log.info("[AUDIT] Anonymous user — returning empty");
             return Optional.empty();
         }

         log.info("[AUDIT] Authenticated user: {}", currentUser);

        return Optional.of(currentUser);
    }
}
