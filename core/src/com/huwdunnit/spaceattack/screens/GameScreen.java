package com.huwdunnit.spaceattack.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.huwdunnit.spaceattack.config.GameConfig;
import com.huwdunnit.spaceattack.entities.Player;
import com.huwdunnit.spaceattack.utils.GdxUtils;
import com.huwdunnit.spaceattack.utils.ViewportUtils;
import com.huwdunnit.spaceattack.utils.debug.DebugCameraController;

/**
 * Game screen.
 *
 * @author Huw
 */
public class GameScreen implements Screen {

    private static final Logger log = new Logger(GameScreen.class.getName(), Logger.DEBUG);

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private Player player;
    private DebugCameraController debugCameraController;

    @Override
    public void show () {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        // Create player
        player = new Player();

        // Calculate position
        float startPlayerX = GameConfig.WORLD_WIDTH / 2;
        float startPlayerY = 1;

        // Position player
        player.setPosition(startPlayerX, startPlayerY);

        // Create debug camera controller
        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);
    }

    @Override
    public void render (float delta) {
        // Update debug camera controller
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        // Update world
        update(delta);

        // Clear screen
        GdxUtils.clearScreen();

        // Render debug graphics
        renderDebug();
    }

    private void update(float delta) {
        updatePlayer();
    }

    private void updatePlayer() {
        player.update();
        blockPlayerFromLeavingTheWorld();
    }

    private void blockPlayerFromLeavingTheWorld() {
        float playerX = MathUtils.clamp(player.getX(),
                player.getWidth() / 2,
                GameConfig.WORLD_WIDTH - player.getWidth() / 2);

        player.setPosition(playerX, player.getY());
    }

    private void renderDebug() {
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

        renderer.end();
        ViewportUtils.drawGrid(viewport, renderer);
    }

    private void drawDebug() {
        player.drawDebug(renderer);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        ViewportUtils.debugPixelPerUnit(viewport);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose () {
        renderer.dispose();
    }
}
