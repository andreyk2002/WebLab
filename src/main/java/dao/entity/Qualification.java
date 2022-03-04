package dao.entity;

public enum Qualification {
    LOW(0),
    MIDDLE(1),
    HIGH(2),
    GOD_TIER(3);

    private final int value;

    Qualification(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
