public class GameLogic {
    DialogueHandler dialogue;
    Player player;
    Cookie cookie;

    public GameLogic() {
        this.dialogue = new DialogueHandler();
        this.player = new Player();
        this.cookie = new Cookie();
    }

    public void play() {
        dialogue.displayDialogue("src/dialogue/intro.txt");
    }
}
