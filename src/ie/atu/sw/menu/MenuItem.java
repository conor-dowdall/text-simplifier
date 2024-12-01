package ie.atu.sw.menu;

/**
 * Represents a menu item in a menu system.
 * Each menu item has a shortcut, a label, and an action to execute.
 *
 * @param shortcut the keyboard shortcut for selecting the menu item
 * @param label    the descriptive label of the menu item
 * @param runnable the action to execute when the menu item is selected
 */
public record MenuItem(String shortcut, String label, Runnable runnable) {

    /**
     * Returns a string representation of the menu item in the format "[shortcut]
     * label".
     *
     * @return a formatted string representing the menu item
     */
    @Override
    public final String toString() {
        return String.format("[%s] %s", shortcut, label);
    }

}