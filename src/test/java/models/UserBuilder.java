package models;

import lombok.*;

@Data
@NoArgsConstructor
@ToString


public class UserBuilder {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private int userStatus;

    public UserBuilder(long id, String username, String lastName, int userStatus) {
        this.id = id;
        this.username = username;
        this.lastName = lastName;
        this.userStatus = userStatus;
    }

    //public UserBuilder(long id, String username) {
        //this.username = username;
        //this.id = id;
    //}

    public  UserBuilder id(long id) {
        this.id = id;
        return this;
    }

    public  UserBuilder username(String username) {
        this.username = username;
        return this;
    }


public  UserBuilder lastName(String lastName) {
    this.lastName = lastName;
    return this;

}

public  UserBuilder userStatus(int userStatus) {
    this.userStatus = userStatus;
    return this;

}

    public  UserBuilder build() {
        return new UserBuilder(id, username, lastName, userStatus);
    }

}
