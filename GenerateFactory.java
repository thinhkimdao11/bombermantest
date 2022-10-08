package mangobomb.bombermango;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static mangobomb.bombermango.BombermanType.*;
import static mangobomb.bombermango.HelloApplication.SCALED_SIZE;

public class GenerateFactory implements EntityFactory {

    @Spawns("Player")
    public Entity newPlayer(SpawnData data) {
        play("gameaudio.wav");

        FXGL.<HelloApplication>getAppCast();
        return entityBuilder(data)
                .atAnchored(new Point2D(24, 24), new Point2D(72, 168))
                .type(PLAYER)
                .zIndex(10)
                .scale(HelloApplication.ZOOM_RATIO, HelloApplication.ZOOM_RATIO)
                .with(new CellMoveComponent(SCALED_SIZE, SCALED_SIZE,150))
                .with(new AStarMoveComponent(FXGL.<HelloApplication>getAppCast().getGrid()))
                .with(new PlayerComponent())
                .buildAndAttach();
    }

    @Spawns("EnemyBalloom")
    public Entity newEnemyBalloom(SpawnData data) {
        var channel = new AnimationChannel(List.of(
                image("sprites/balloom_left1.png"),
                image("sprites/balloom_left2.png"),
                image("sprites/balloom_left3.png")
        ), Duration.seconds(0.5));

        return entityBuilder()
                .at(data.getX(), data.getY())
                .view(new AnimatedTexture(channel).loop())
                .scale(HelloApplication.ZOOM_RATIO, HelloApplication.ZOOM_RATIO)
                .buildAndAttach();
    }

    @Spawns("EnemyMinvo")
    public Entity newEnemyMinvo(SpawnData data) {
        var channel = new AnimationChannel(List.of(
                image("sprites/minvo_left1.png"),
                image("sprites/minvo_left2.png"),
                image("sprites/minvo_left3.png")
        ), Duration.seconds(0.5));

        return entityBuilder()
                .at(data.getX(), data.getY())
                .view(new AnimatedTexture(channel).loop())
                .scale(HelloApplication.ZOOM_RATIO, HelloApplication.ZOOM_RATIO)
                .buildAndAttach();
    }

    @Spawns("PowerupPass")
    public Entity newPowerupPass(SpawnData data) {
        return entityBuilder()
                .view("sprites/powerup_bombpass.png")
                .at(data.getX(), data.getY())
                .scale(HelloApplication.ZOOM_RATIO, HelloApplication.ZOOM_RATIO)
                .type(POWERUP)
                .build();
    }
    @Spawns("PowerupBombs")
    public Entity newPowerupBombs(SpawnData data) {
        return entityBuilder()
                .view("sprites/powerup_bombs.png")
                .at(data.getX(), data.getY())
                .scale(HelloApplication.ZOOM_RATIO, HelloApplication.ZOOM_RATIO)
                .type(POWERUP)
                .build();
    }

    @Spawns("PowerupDetonator")
    public Entity newPowerupDetonator(SpawnData data) {
        return entityBuilder()
                .view("sprites/powerup_detonator.png")
                .at(data.getX(), data.getY())
                .scale(HelloApplication.ZOOM_RATIO, HelloApplication.ZOOM_RATIO)
                .type(POWERUP)
                .build();
    }

    @Spawns("PowerupFlamepass")
    public Entity newPowerupFlamepass(SpawnData data) {
        return entityBuilder()
                .view("sprites/powerup_flamepass.png")
                .at(data.getX(), data.getY())
                .scale(HelloApplication.ZOOM_RATIO, HelloApplication.ZOOM_RATIO)
                .type(POWERUP)
                .build();
    }

    @Spawns("PowerupSpeed")
    public Entity newPowerupSpeed(SpawnData data) {
        return entityBuilder()
                .view("sprites/powerup_speed.png")
                .at(data.getX(), data.getY())
                .scale(HelloApplication.ZOOM_RATIO, HelloApplication.ZOOM_RATIO)
                .type(POWERUP)
                .build();
    }

    @Spawns("Portal")
    public Entity newPortal(SpawnData data) {
        return entityBuilder()
                .view("sprites/portal.png")
                .at(data.getX(), data.getY())
                .scale(HelloApplication.ZOOM_RATIO, HelloApplication.ZOOM_RATIO)
                .type(POWERUP)
                .build();
    }

    @Spawns("Brick,0")
    public Entity newBrick(SpawnData data) {
        return entityBuilder(data)
                .viewWithBBox(texture("sprites/brick.png", 48, 48))
                .type(BRICK)
                .build();
    }

    @Spawns("Wall,1")
    public Entity newWall(SpawnData data) {
        return entityBuilder(data)
                .viewWithBBox(texture("sprites/wall.png", 48, 48))
                .type(WALL)
                .build();
    }

    @Spawns("BG")
    public Entity newBackground(SpawnData data) {
        return entityBuilder()
                .at(0, 0)
                .view(new Rectangle(HelloApplication.SCREEN_WIDTH, HelloApplication.SCREEN_HEIGHT, Color.GREEN))
                .zIndex(-1)
                .build();
    }

    @Spawns("GreyBG,2")
    public Entity newGreyBG(SpawnData data) {
        return entityBuilder(data)
                .view(new Rectangle(SCALED_SIZE, SCALED_SIZE, Color.GREY))
                .type(BG)
                .build();
    }

    @Spawns("Bomb")
    public Entity newBomb(SpawnData data) {
        play("putbomb.wav");

        var channel = new AnimationChannel(List.of(
                image("sprites/bomb.png"),
                image("sprites/bomb_1.png"),
                image("sprites/bomb_2.png")
        ), Duration.seconds(0.5));

        return entityBuilder()
                .atAnchored(new Point2D(24,24), new Point2D(data.getX() + 24, data.getY() + 24))
                .with(new Bomb(48))
                .view(new AnimatedTexture(channel).loop())
                .scale(HelloApplication.ZOOM_RATIO, HelloApplication.ZOOM_RATIO)
                .buildAndAttach();
    }

    @Spawns("BombExploded")
    public Entity newBombExploded(SpawnData data) {
        var channel = new AnimationChannel(List.of(
                image("sprites/bomb_exploded.png"),
                image("sprites/bomb_exploded1.png"),
                image("sprites/bomb_exploded2.png"),
                image("sprites/bomb_exploded1.png"),
                image("sprites/bomb_exploded.png")
        ), Duration.seconds(0.8));

        return entityBuilder()
                .at(data.getX(), data.getY())
                .view(new AnimatedTexture(channel).loop())
                .scale(HelloApplication.ZOOM_RATIO, HelloApplication.ZOOM_RATIO)
                .buildAndAttach();
    }

    @Spawns("HorizontalLeft")
    public Entity newHorizontalLeft(SpawnData data) {
        var channel = new AnimationChannel(List.of(
                image("sprites/explosion_horizontal_left_last.png"),
                image("sprites/explosion_horizontal_left_last1.png"),
                image("sprites/explosion_horizontal_left_last2.png"),
                image("sprites/explosion_horizontal_left_last1.png"),
                image("sprites/explosion_horizontal_left_last.png")
        ), Duration.seconds(0.8));

        return entityBuilder()
                .at(data.getX(), data.getY())
                .view(new AnimatedTexture(channel).loop())
                .scale(HelloApplication.ZOOM_RATIO, HelloApplication.ZOOM_RATIO)
                .buildAndAttach();
    }

    @Spawns("HorizontalRight")
    public Entity newHorizontalRight(SpawnData data) {
        var channel = new AnimationChannel(List.of(
                image("sprites/explosion_horizontal_right_last.png"),
                image("sprites/explosion_horizontal_right_last1.png"),
                image("sprites/explosion_horizontal_right_last2.png"),
                image("sprites/explosion_horizontal_right_last1.png"),
                image("sprites/explosion_horizontal_right_last.png")
        ), Duration.seconds(0.8));

        return entityBuilder()
                .at(data.getX(), data.getY())
                .view(new AnimatedTexture(channel).loop())
                .scale(HelloApplication.ZOOM_RATIO, HelloApplication.ZOOM_RATIO)
                .buildAndAttach();
    }

    @Spawns("VerticalTop")
    public Entity newVerticalTop(SpawnData data) {
        var channel = new AnimationChannel(List.of(
                image("sprites/explosion_vertical_top_last.png"),
                image("sprites/explosion_vertical_top_last1.png"),
                image("sprites/explosion_vertical_top_last2.png"),
                image("sprites/explosion_vertical_top_last1.png"),
                image("sprites/explosion_vertical_top_last.png")
        ), Duration.seconds(0.8));

        return entityBuilder()
                .at(data.getX(), data.getY())
                .view(new AnimatedTexture(channel).loop())
                .scale(HelloApplication.ZOOM_RATIO, HelloApplication.ZOOM_RATIO)
                .buildAndAttach();
    }

    @Spawns("VerticalDown")
    public Entity newVerticalDown(SpawnData data) {
        var channel = new AnimationChannel(List.of(
                image("sprites/explosion_vertical_down_last.png"),
                image("sprites/explosion_vertical_down_last1.png"),
                image("sprites/explosion_vertical_down_last2.png"),
                image("sprites/explosion_vertical_down_last1.png"),
                image("sprites/explosion_vertical_down_last.png")
        ), Duration.seconds(0.8));

        return entityBuilder()
                .at(data.getX(), data.getY())
                .view(new AnimatedTexture(channel).loop())
                .scale(HelloApplication.ZOOM_RATIO, HelloApplication.ZOOM_RATIO)
                .buildAndAttach();
    }

}
