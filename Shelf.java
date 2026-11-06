/**
 * Holds pallets by looking at the slot and weight limits.
 * @author Rohith Balijepalli Fall 2026 CS18000-BLK
 * @version Oct 18, 2026
 */
public class Shelf {
    private Pallet[] pallets;
    private double weightLimit;

    public Shelf(int palletCapacity, double weightLimit) {
        pallets = new Pallet[palletCapacity];
        this.weightLimit = weightLimit;
    }

    public Pallet[] getPallets() { return pallets; }
    public double getWeightLimit() { return weightLimit; }

    public double getCurrentWeight() {
        double total = 0.0;
        for (Pallet pallet : pallets) {
            if (pallet != null) { total += pallet.getWeight(); }
        }
        return total;
    }

    public double getAvailableCapacity() {
        if (!hasOpenPositions()) { return -1.0; }
        return weightLimit - getCurrentWeight();
    }

    public boolean hasOpenPositions() {
        for (Pallet pallet : pallets) {
            if (pallet == null) { return true; }
        }
        return false;
    }

    public boolean addPallet(Pallet pallet) {
        if (pallet == null || pallet.getItem() == null || pallet.getCount() == 0
                || getCurrentWeight() + pallet.getWeight()
                        > weightLimit + Policies.getDelta()) {
            return false;
        }
        for (int i = 0; i < pallets.length; i++) {
            if (pallets[i] == null) {
                pallets[i] = pallet;
                return true;
            }
        }
        return false;
    }

    public int findItem(Item item) {
        for (int i = 0; i < pallets.length; i++) {
            if (pallets[i] != null && pallets[i].getItem() != null
                    && pallets[i].getItem().equals(item)) {
                return i;
            }
        }
        return -1;
    }

    public Pallet removePallet(int position) {
        if (position < 0 || position >= pallets.length) { return null; }
        Pallet removed = pallets[position];
        pallets[position] = null;
        return removed;
    }

    public void clean() {
        for (int i = 0; i < pallets.length; i++) {
            if (pallets[i] != null && (pallets[i].getItem() == null
                    || pallets[i].getCount() == 0)) {
                pallets[i] = null;
            }
        }
    }

    @Override
    public String toString() {
        int used = 0;
        for (Pallet pallet : pallets) {
            if (pallet != null) { used++; }
        }
        return String.format("%d/%d - %.2f/%.2f lbs",
                used, pallets.length, getCurrentWeight(), weightLimit);
    }
}
