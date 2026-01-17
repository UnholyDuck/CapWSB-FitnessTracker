package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;

/**
 * Lightweight DTO exposing only basic user information (id, firstName, lastName).
 * Used for listing all users with minimal data.
 */
public record UserSimpleDto(@Nullable Long id, String firstName, String lastName) {

}
