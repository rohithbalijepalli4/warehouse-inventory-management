/**
 * Manages inventory across a row-major grid of shelves.
 * @author Rohith Balijepalli Fall 2026 CS18000-BLK
 * @version Oct 18, 2026
 */
public class Warehouse {
    private Shelf[][] shelves;

    public Warehouse(int rows, int columns) {
        shelves = new Shelf[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                shelves[row][col] = new Shelf(Policies.getShelfCapacity(),
                        Policies.getShelfWeightLimit());
            }
        }
    }

    public Shelf[][] getShelves() { return shelves; }

    private int weightCapacity(Shelf shelf, Item item) {
        double remaining = shelf.getWeightLimit() - shelf.getCurrentWeight();
        if (item.getWeight() <= 0) { return Integer.MAX_VALUE; }
        double possible = Math.floor((remaining + Policies.getDelta())
                / item.getWeight());
        if (possible <= 0) { return 0; }
        return (int) Math.min(possible, Integer.MAX_VALUE);
    }

    public int addItems(Item item, int quantity) {
        if (item == null || quantity <= 0
                || Policies.getPalletCapacity(item.getSize()) <= 0) {
            return quantity;
        }
        int remaining = quantity;
        for (Shelf[] row : shelves) {
            for (Shelf shelf : row) {
                for (Pallet pallet : shelf.getPallets()) {
                    if (pallet != null && pallet.getItem() != null
                            && pallet.getItem().equals(item)) {
                        int amount = Math.min(remaining,
                                Math.min(pallet.getRemainingCapacity(),
                                        weightCapacity(shelf, item)));
                        if (amount > 0) {
                            pallet.addItems(item, amount);
                            remaining -= amount;
                        }
                        if (remaining == 0) { return 0; }
                    }
                }
            }
        }
        for (Shelf[] row : shelves) {
            for (Shelf shelf : row) {
                while (remaining > 0 && shelf.hasOpenPositions()) {
                    int amount = Math.min(remaining,
                            Math.min(Policies.getPalletCapacity(item.getSize()),
                                    weightCapacity(shelf, item)));
                    if (amount <= 0) { break; }
                    if (!shelf.addPallet(new Pallet(item, amount))) { break; }
                    remaining -= amount;
                }
                if (remaining == 0) { return 0; }
            }
        }
        return remaining;
    }

    public int removeItems(Item item, int quantity) {
        if (item == null || quantity <= 0) { return 0; }
        int removed = 0;
        for (Shelf[] row : shelves) {
            for (Shelf shelf : row) {
                Pallet[] pallets = shelf.getPallets();
                for (int i = 0; i < pallets.length; i++) {
                    Pallet pallet = pallets[i];
                    if (pallet != null) {
                        int amount = pallet.removeItems(item, quantity - removed);
                        removed += amount;
                        if (pallet.isEmpty()) { shelf.removePallet(i); }
                        if (removed == quantity) { return removed; }
                    }
                }
            }
        }
        return removed;
    }

    public int getItemCount(Item item) {
        int count = 0;
        if (item == null) { return 0; }
        for (Shelf[] row : shelves) {
            for (Shelf shelf : row) {
                for (Pallet pallet : shelf.getPallets()) {
                    if (pallet != null && item.equals(pallet.getItem())) {
                        count += pallet.getCount();
                    }
                }
            }
        }
        return count;
    }

    public int[] findItem(Item item) {
        if (item != null) {
            for (int row = 0; row < shelves.length; row++) {
                for (int col = 0; col < shelves[row].length; col++) {
                    if (shelves[row][col].findItem(item) != -1) {
                        return new int[] {row, col};
                    }
                }
            }
        }
        return new int[] {-1, -1};
    }

    private boolean validShelf(int row, int col) {
        return row >= 0 && row < shelves.length
                && col >= 0 && col < shelves[row].length;
    }

    public boolean movePallet(int sourceRow, int sourceColumn, int palletIndex,
                              int destinationRow, int destinationColumn) {
        if (!validShelf(sourceRow, sourceColumn)
                || !validShelf(destinationRow, destinationColumn)
                || (sourceRow == destinationRow
                        && sourceColumn == destinationColumn)) {
            return false;
        }
        Shelf source = shelves[sourceRow][sourceColumn];
        Shelf destination = shelves[destinationRow][destinationColumn];
        if (palletIndex < 0 || palletIndex >= source.getPallets().length) {
            return false;
        }
        Pallet pallet = source.getPallets()[palletIndex];
        if (pallet == null) { return false; }
        if (!destination.addPallet(pallet)) { return false; }
        source.removePallet(palletIndex);
        return true;
    }
}
