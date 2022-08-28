package br.com.waytech.simplecontactlist.config;

public class AppConstraints {
	
	// API

	public static final String WEB_SOCKET_PATH = "/ws";
	public static final String API_USER_PATH = "/users";
	public static final String API_AUTH_PATH = "/auth";
	public static final String API_ADMIN_PATH = "/admin";
	public static final String API_DELIVERYMAN_PATH = "/deliveryman";
	public static final String API_ORGANIZATION_PATH = "/organizations";
	public static final String API_INTEGRATION_ROBOT_PATH = "/robots";
	public static final String PATH_VARIABLE_ORGANIZATION_ID = "organizationId";
		
	// Bean Validation
	
	public static final int ORGANIZATION_PREFIX_MAX_SIZE = 20;
	public static final int ORGANIZATION_NAME_MIN_SIZE = 3;
	public static final int ORGANIZATION_NAME_MAX_SIZE = 200;
	public static final int ORGANIZATION_UNIT_NAME_MIN_SIZE = 3;
	public static final int ORGANIZATION_UNIT_NAME_MAX_SIZE = 200;
	public static final int ORGANIZATION_ROLE_NAME_MIN_SIZE = 3;
	public static final int ORGANIZATION_ROLE_NAME_MAX_SIZE = 200;
	public static final int ORGANIZATION_INVITEE_NAME_MAX_SIZE = 200;
	public static final int ORGANIZATION_DELIVERYMAN_GROUP_NAME_MAX_SIZE = 200;
	public static final int USER_NAME_MIN_SIZE = 3;
	public static final int USER_NAME_MAX_SIZE = 200;
	public static final int USER_PASS_MIN_SIZE = 8;
	public static final int USER_PASS_MAX_SIZE = 200;
	public static final int USER_PHONE_MAX_SIZE = 5;
	public static final int ROBOT_DESCRIPTION_MAX_SIZE = 200;
	public static final int ROBOT_PREFIX_SIZE = 18;
	public static final int ROBOT_TOKEN_SIZE = 24;
	public static final int VEHICLE_MODEL_MAX_SIZE = 120;
	public static final int VEHICLE_LICENSE_NUMBER_MAX_SIZE = 120;
	public static final int VEHICLE_LICENSE_ISSUER_MAX_SIZE = 120;
	public static final int VEHICLE_PLATE_NUMBER_MAX_SIZE = 40;
	public static final int DELIVERYMAN_MAX_ACTIVE_VEHICLES = 5;
	
	// Security
	
	public static final String REQUEST_ATTRIBUTE_USER = "user";
	public static final String REQUEST_ATTRIBUTE_ADMIN_USER = "admin_user";
	public static final String REQUEST_ATTRIBUTE_ROLE_USER = "role_user";
	public static final String REQUEST_ATTRIBUTE_DELIVERYMAN = "deliveryman";
	public static final String REQUEST_ATTRIBUTE_ROBOT = "robot_credential";
	public static final String REQUEST_ATTRIBUTE_ORGANIZATION = "organization";
	public static final String REQUEST_ATTRIBUTE_ROLE = "role";
	public static final String SESSION_ATTRIBUTE_LOCATION = "location";
	public static final String HEADER_TOKEN = "token";
	public static final String HEADER_USER_TOKEN = "user_token";
	public static final String HEADER_ROLE = "role";
	public static final String HEADER_ORGANIZATION_ID = "organization_id";
	
	// TTLs
	
	public static int ORGANIZATION_INVITE_TTL_IN_DAYS = 30;
	public static int DEFAULT_TIME_FOT_ORDER_TO_EXPIRE_IN_MINUTES = 15;
	public static int DEFAULT_TIME_TO_PICK_UP_IN_MINUTES = 10;
	
	// Geo Configs
	
	public static int DELIVERYMAN_VISIBILITY_RADIUS = 10;
	
}
