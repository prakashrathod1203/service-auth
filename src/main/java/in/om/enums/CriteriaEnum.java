package in.om.enums;

public enum CriteriaEnum {

    LESS_THAN("<"),  LESS_THAN_EQUAL_TO("<="),
    GREATER_THAN(">"), GREATER_THAN_EQUAL_TO(">="),
    EQUAL_TO("="),
    LIKE("like"),
    IN("in"),
    BETWEEN("between");

    private String name;

    private CriteriaEnum(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
