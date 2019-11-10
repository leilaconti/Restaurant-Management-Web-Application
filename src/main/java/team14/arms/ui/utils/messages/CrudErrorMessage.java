package team14.arms.ui.utils.messages;

public final class CrudErrorMessage {

    private CrudErrorMessage() {}

    public static final String ENTITY_NOT_FOUND = "The selected entity was not found.";

    public static final String CONCURRENT_UPDATE = "Somebody else might have updated the data. Please refresh and try again.";

    public static final String OPERATION_PREVENTED_BY_REFERENCES = "The operation could not be executed as there are references to an entity within the database.";

    public static final String REQUIRED_FIELDS_MISSING = "Please fill out all required fields before proceeding.";

}
