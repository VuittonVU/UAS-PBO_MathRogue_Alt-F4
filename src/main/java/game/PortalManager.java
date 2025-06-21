package game;

public class PortalManager {
    private int currentRoom = 1;
    private final int totalRooms = 12;
    private String currentPortalColor = "red";

    public void setPortalColor(String newColor) {
        // Validasi sederhana untuk memastikan hanya "blue" atau "red" yang dimasukkan
        if (newColor.equalsIgnoreCase("blue") || newColor.equalsIgnoreCase("red")) {
            this.currentPortalColor = newColor.toLowerCase();
        } else {
            System.out.println("Peringatan: Usaha untuk mengatur warna portal yang tidak valid: " + newColor);
        }
    }

    public void nextRoom() {
        if (!isGameOver()) {
            currentRoom++;
        }
    }

    public boolean isBossRoom() {
        return currentRoom == 12;
    }

    public boolean isGameOver() {
        return currentRoom > totalRooms;
    }

    public int getCurrentRoom() {
        return currentRoom;
    }

    public String getCurrentPortalColor() {
        return currentPortalColor;
    }
}