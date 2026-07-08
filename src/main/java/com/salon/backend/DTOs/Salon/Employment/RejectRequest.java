package com.salon.backend.DTOs.Salon.Employment;


public record RejectRequest(
        Long requestId,
        String rejectionReason
) {}