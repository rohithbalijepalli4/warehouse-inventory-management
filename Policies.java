/**
 * Policies.java
 *
 * This class contains all warehouse policies.
 * Do not modify this file.
 * @author Rohith Balijepalli Purdue_CS
 * @version Ver 1.0
 */
public class Policies {

    /**
     * Returns the maximum number of items that may be stored
     * on a pallet based on item size.
     *
     * @param size Item size ('S', 'M', or 'L')
     * @return Maximum pallet capacity
     */
    public static int getPalletCapacity(char size) {
        switch (size) {
            case 'S':
                return 100;
            case 'M':
                return 50;
            case 'L':
                return 20;
            default:
                return -1;
        }
    }

    /**
     * Returns the number of pallet positions available
     * on each shelf.
     *
     * @return Pallets per shelf
     */
    public static int getShelfCapacity() {
        return 5;
    }

    /**
     * Returns the maximum weight that may be stored
     * on a shelf.
     *
     * @return Shelf weight limit
     */
    public static double getShelfWeightLimit() {
        return 3000.0;
    }

    /**
     * Returns the delta value that should be used
     * when comparing doubles for equality.
     *
     * Example:
     * Math.abs(a - b) < Policies.getDelta()
     *
     * @return Delta value
     */
    public static double getDelta() {
        return 0.0001;
    }
}
