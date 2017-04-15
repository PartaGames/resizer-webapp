package com.partagames.resizer.webapp.storage

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "upload-dir"

    String getLocation() {
        return location
    }

    void setLocation(String location) {
        this.location = location
    }

}