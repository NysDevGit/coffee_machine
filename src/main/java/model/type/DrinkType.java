package model.type;

public enum DrinkType {
    CHOCOLATE("H"),
    COFFEE("C"),
    TEA("T");

    private final String code;

    DrinkType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
