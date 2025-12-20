package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;

/**
 * Lightweight DTO exposing only id and email for user search results.
 */
public record UserEmailDto(@Nullable Long id, String email) {

}
