import java.util.Set;

public class GameLogic {
    Player player;
    Cookie cookie;

    public GameLogic() {
        this.player = new Player();
        this.cookie = new Cookie();
    }

    public void play() {
        Set<String> cmds = Set.of("quit", "rebirth");
        String userInput = "";
        while (!cmds.contains(userInput)) {
            userInput = Dialogue.promptUser("Cookies: " + player.getNumCookies() + " | ");
            player.incrementCookies(cookie.click());
        }
    }

    public void launch() {
        Dialogue.displayAt("src/dialogue/intro.txt");
        play();
    }
}
