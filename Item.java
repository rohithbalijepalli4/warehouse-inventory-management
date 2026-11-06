/**
 * Represents one type of product in the warehouse.
 * @author Rohith Balijepalli Fall 2026 CS18000-BLK
 * @version Oct 18, 2026
 */
public class Item {
    private String name;
    private int itemNumber;
    private double weight;
    private char size;

    public Item(String name, int itemNumber, double weight, char size) {
        this.name = name;
        this.itemNumber = itemNumber;
        this.weight = weight;
        this.size = size;
    }

    public String getName() { return name; }
    public int getItemNumber() { return itemNumber; }
    public double getWeight() { return weight; }
    public char getSize() { return size; }
    public void setName(String name) { this.name = name; }
    public void setItemNumber(int itemNumber) { this.itemNumber = itemNumber; }
    public void setWeight(double weight) { this.weight = weight; }
    public void setSize(char size) { this.size = size; }

    @Override
    public String toString() {
        return String.format("%s (#%d) - %.2f lbs - Size %c",
                name, itemNumber, weight, size);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (!(obj instanceof Item)) { return false; }
        Item other = (Item) obj;
        boolean sameName = name == null ? other.name == null : name.equals(other.name);
        return sameName && itemNumber == other.itemNumber
                && Math.abs(weight - other.weight) < Policies.getDelta()
                && size == other.size;
    }
}
