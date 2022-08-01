package nl.bos;

public record OTDS (
        String token,
        String userId,
        String ticket,
        String resourceID,
        String failureReason,
        String passwordExpirationTime,
        boolean continuation,
        String continuationContext,
        String continuationData
    ) {
}

