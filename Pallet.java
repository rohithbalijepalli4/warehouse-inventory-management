/**
 * This code stores a number of one type of item.
 * @author Rohith Balijepalli Fall 2026 CS18000-BLK
 * @version Oct 18, 2026
 */
public class Pallet {
    private Item item;
    private int count;

    public Pallet(Item item, int count) {
        if (item == null || count < 0
                || count > Policies.getPalletCapacity(item.getSize())) {
            this.item = null;
            this.count = 0;
        } else {
            this.item = item;
            this.count = count;
        }
    }

    public Item getItem() { return item; }
    public int getCount() { return count; }

    public int getCapacity() {
        return item == null ? -1 : Policies.getPalletCapacity(item.getSize());
    }

    public int getRemainingCapacity() {
        return item == null ? -1 : getCapacity() - count;
    }

    public boolean isFull() {
        return item != null && count == getCapacity();
    }

    public boolean isEmpty() {
        return item == null || count == 0;
    }

    public double getWeight() {
        return item == null ? 0.0 : item.getWeight() * count;
    }

    public int addItems(Item item, int quantity) {
        if (quantity <= 0) { return quantity; }
        if (item == null) { return quantity; }
        if (this.item != null && !this.item.equals(item)) { return quantity; }
        int capacity = Policies.getPalletCapacity(item.getSize());
        if (capacity < 0) { return quantity; }
        int amount = Math.min(quantity, capacity - count);
        if (amount <= 0) { return quantity; }
        this.item = item;
        count += amount;
        return quantity - amount;
    }

    public int removeItems(Item item, int quantity) {
        if (quantity <= 0 || this.item == null || !this.item.equals(item)) {
            return 0;
        }
        int removed = Math.min(quantity, count);
        count -= removed;
        if (count == 0) { this.item = null; }
        return removed;
    }

    @Override
    public String toString() {
        return item + " x " + count;
    }
}
