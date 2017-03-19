package com.example.grupoes.projetoes.util;

/**
 * Created by Wesley on 17/03/2017.
 */

public class InvalidInput {
    private InputType type;
    private String error;

    public InvalidInput(InputType type, String error) {
        this.type = type;
        this.error = error;
    }

    public InputType getType() {
        return type;
    }

    public void setType(InputType type) {
        this.type = type;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
