import model.Game;
import view.MainInterface;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello Java");

        Game game = new Game(5*11);

        MainInterface myinterface = new MainInterface();
    }
}