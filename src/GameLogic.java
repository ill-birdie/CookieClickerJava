public class GameLogic {
    Player player;
    Cookie cookie;

    public GameLogic() {
        this.player = new Player();
        this.cookie = new Cookie();
    }

    public void play() {
        Dialogue.displayAt("src/dialogue/intro.txt");
    }
}
