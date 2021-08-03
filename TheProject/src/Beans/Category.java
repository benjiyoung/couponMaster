package Beans;

public enum Category {
    FOOD(1),
    ELECTRICITY(2),
    RESTAURANT(3),
    VACATION(4),
    HOME(5),
    SPORT(6),
    FASHION(7),
    BEAUTY(8),
    PETS(9);

    /**
     * Categories in the table COUPONS are saved by an ID and not by their name, so this field is the equivalent in java
     */
    private final int ID;
    /**
     * An array of all categories in order to iterate on all of the categories in the indexToCategory method
     */
    public static Category[] categories = {FOOD, ELECTRICITY, RESTAURANT, VACATION, HOME, SPORT, FASHION, BEAUTY, PETS};

    Category(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    /**
     * Converting category's ID, a column name in table COUPONS, with it's enum, with the help of the categories array
     * @param ID Category's field and it's ID in the database
     * @return Category enum of the given ID
     */
    public static Category indexToCategory(int ID) {
        Category result = null;
        for (Category category : categories) {
            if (category.getID() == ID) {
                result = category;
                break;
            }
        }
        return result;
    }
}