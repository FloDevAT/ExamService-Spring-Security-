package at.htlkaindorf.examservice.pojos;

import lombok.Data;

/**
 * Same as with the Credentials file I only created this class for better code design
 * The logic this class is required for can also be done using a HashMap, however,
 * I prefer statically typed elements, therefore I use this class
 */
@Data
public class AuthenticationErrorMessage {
    private int code;
    private String message;
    private String error;
}
