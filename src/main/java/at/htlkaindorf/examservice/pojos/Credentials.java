package at.htlkaindorf.examservice.pojos;

import lombok.Data;

/**
 * Class that holds a username + password combination
 * In this case also the Student class could be used with an alias on the `name` property,
 * however, I find this solution much more readable
 */
@Data
public class Credentials {
    private String username;
    private String password;
}
