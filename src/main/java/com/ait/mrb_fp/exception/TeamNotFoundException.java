package com.ait.mrb_fp.exception;

/**
 * ⚠️ Thrown when the specified team is not found.
 * Example: /api/teams/T01 → No such team in database.
 */
public class TeamNotFoundException extends ResourceNotFoundException {
    public TeamNotFoundException(String teamId) {
        super("Team not found with ID: " + teamId);
    }
}
