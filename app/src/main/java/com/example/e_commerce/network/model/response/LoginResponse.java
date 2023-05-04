package com.example.e_commerce.network.model.response;


public class LoginResponse {
   private LoginDetail tokens;

    public LoginResponse(LoginDetail tokens) {
        this.tokens = tokens;
    }

    public LoginDetail getTokens() {
        return tokens;
    }

    public void setTokens(LoginDetail tokens) {
        this.tokens = tokens;
    }
}
