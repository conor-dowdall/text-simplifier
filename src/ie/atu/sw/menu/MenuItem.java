package ie.atu.sw.menu;

public record MenuItem(String shortcut, String label, Runnable runnable) {
    @Override
    public final String toString() {
        return String.format("[%s] %s", shortcut, label);
    }
}