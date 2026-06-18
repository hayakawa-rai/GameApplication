package Item;
import Character.Sengoku;
<<<<<<< HEAD
=======

>>>>>>> branch 'master' of https://github.com/hayakawa-rai/GameApplication.git
import javafx.scene.Node;

public abstract class Item {
    protected int score;
    protected Node view; // CircleもImageViewもNodeとして一括管理

    public Item(int score, Node view) {
        this.score = score;
        this.view = view;
    }

    public Node getView() { return view; }
    public abstract void onEaten(Sengoku player);
}