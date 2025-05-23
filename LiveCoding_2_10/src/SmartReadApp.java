import controller.SmartReadController;
import model.Inventory;
import view.SmartReadView;

public class SmartReadApp {
    public static void main(String[] args) {
        Inventory inventory = new Inventory("inventory.txt");
        SmartReadView view = new SmartReadView();
        new SmartReadController(view, inventory);
    }
}