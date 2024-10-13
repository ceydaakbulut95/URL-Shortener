package org.example.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;

public class UrlRequestDTO {

    @NotBlank(message = "Long URL is required and cannot be blank.")
    private String longUrl;

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
