package engine;

import utils.Timer;

public class GameEngine implements Runnable {

    public static final int TARGET_FPS = 30;
    public static final int TARGET_UPS = 30;

    private final Thread gameLoopThread;
    private final Window window;
    private final IGameLogic gameLogic;
    private final Timer timer;

    public GameEngine(Window window,IGameLogic gameLogic) {
        this.gameLoopThread = new Thread(this,"GAME_LOOP_THREAD");
        this.window = window;
        this.gameLogic = gameLogic;
        this.timer = new Timer();
    }

    public void start(){
        gameLoopThread.start();
    }

    @Override
    public void run() {
        try {
            init();
            gameLoop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void init() throws Exception{
        window.init();
        timer.init();
        gameLogic.init();
    }

    protected void gameLoop(){
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        boolean running = true;
        while (running&&!window.isShouldClose()){
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            input();

            while (accumulator > interval){
                update(interval);
                accumulator -= interval;
            }

            render();

            if (!window.isvSync()){
                sync();
            }
        }
    }

    private void sync(){
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime){
            try {
                Thread.sleep(1);
            }catch (InterruptedException e){

            }
        }
    }

    protected void input() {
        gameLogic.input(window);
    }

    protected void update(float interval){
        gameLogic.update(interval);
    }

    protected void render(){
        gameLogic.render(window);
        window.update();
    }
}
