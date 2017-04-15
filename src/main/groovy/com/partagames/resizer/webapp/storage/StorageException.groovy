package com.partagames.resizer.webapp.storage

class StorageException extends RuntimeException {

    StorageException(String message) {
        super(message)
    }

    StorageException(String message, Throwable cause) {
        super(message, cause)
    }
}