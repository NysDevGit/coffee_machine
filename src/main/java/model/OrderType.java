package model;

public enum OrderType {
    CHOCOLATE("H"),
    COFFEE("C"),
    TEA("T");

    private final String code;

    OrderType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}