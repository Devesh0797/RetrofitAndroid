package com.dev.retrofitandroid.Models;

public class LoginResponse {

    private boolean error;
    private String message;
    private User user;

    public LoginResponse(boolean error,String message,User user){
        this.error=error;
        this.message=message;
        this.user=user;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
