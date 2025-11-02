package com.ait.mrbfp.exception;

public class TeamNotFoundException extends ResourceNotFoundException {
    public TeamNotFoundException(String teamId) {
        super("Team not found with ID: " + teamId);
    }
}
