package game;

import engine.GameEngine;
import engine.IGameLogic;
import engine.Window;

public class Main {
    public static void main(String[] args) {
        Window window  = new Window("Ouri 2020",1280,720,false);
        IGameLogic game = new MyFirstGame();
        GameEngine engine = new GameEngine(window,game);
        engine.run();
    }
}
