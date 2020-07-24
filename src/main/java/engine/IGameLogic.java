package engine;

public interface IGameLogic {
    void init() throws Exception;
    void input(Window window);
    void update(Window window,float interval);
    void render(Window window);
    void cleanup();
}
