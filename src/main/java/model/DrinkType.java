package model;

public enum DrinkType {
    CHOCOLATE("H",0.5),
    COFFEE("C",0.6),
    TEA("T",0.4),
    ORANGE_JUICE("O",0.6);

    private final String code;
    private final double price;

    DrinkType(String code, double price) {
        this.code = code;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public double getPrice(){ return price;}
}
