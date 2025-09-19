package co.com.jcuadrado.r2dbc.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class RepositoryConstants {
    public static final String LOG_USER_SAVED_SUCCESS = "User saved successfully: {}";
    public static final String LOG_ERROR_SAVING_USER = "Error saving user: {}";
    public static final String LOG_USER_RETRIEVED_BY_EMAIL_OR_DOCUMENT_SUCCESS = "User retrieved successfully by email or document number: {} / {}";
    public static final String LOG_USER_RETRIEVED_BY_DOCUMENT_SUCCESS = "User retrieved successfully by document number: {}";
    public static final String LOG_USER_RETRIEVED_BY_EMAIL_SUCCESS = "User retrieved successfully by email: {}";

    public static final String LOG_ERROR_RETRIEVING_ALL_USERS = "Error retrieving all users: {}";
    public static final String LOG_ERROR_RETRIEVING_USER_BY_EMAIL_OR_DOCUMENT = "Error retrieving user by email or document number: {}";
    public static final String LOG_ERROR_RETRIEVING_USER_BY_DOCUMENT = "Error retrieving user by document number: {}";
    public static final String LOG_ERROR_RETRIEVING_USER_BY_EMAIL = "Error retrieving user by email: {}";

    // Role-related logs
    public static final String LOG_ERROR_RETRIEVING_ROLE_BY_ID = "Error retrieving role by id: {}";
}
