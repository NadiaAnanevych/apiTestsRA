package constants;

import models.User;

public class Constants {
    public static final String BASE_URL = "https://petstore.swagger.io";
    public static String USER_ENDPOINT = "/v2/user";

    public static final User DEFAULT_USER = new User(0, "string", "string","string","string","string","string", 0);

    public static User expectedUser = new User(9223372036854755000L, "string", "string","string","string","string","string", 0);
    public static User UPDATED_USER = new User(1, "string2", "string2","string2","string2","string2","string2", 1);
}
