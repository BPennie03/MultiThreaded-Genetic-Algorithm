public class Item {

    private final String name;
    // A label for the item

    private final double weight;
    // The weight for the item (in pounds)

    private final int value;
    // The value of the item (rounded to the nearest dollar)

    private boolean included;
    // Indicates whether the item is, or isn't, included

    // Initializes Item's fields to the values that are passed in; the included field is initialized to false
    public Item(String name, double weight, int value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.included = false;
    }

    // Initializes this item's fields to be the same as the other item's
    public Item(Item other) {
        this.name = other.name;
        this.weight = other.weight;
        this.value = other.value;
        this.included = other.included;
    }

    // Getter methods:
    public double getWeight() {
        return weight;
    }
    public int getValue() {
        return value;
    }
    public boolean isIncluded() {
        return included;
    }


    // Setter for the item's included field (no need for setters for other fields)
    public void setIncluded(boolean included) {
        this.included = included;
    }

    // Over-written toString method to display the item in format:
    // <name> (<weight> lbs, $<value)
    public String toString() {
        return name + " (" + weight + " lbs, $" + value + ") ";
    }

}