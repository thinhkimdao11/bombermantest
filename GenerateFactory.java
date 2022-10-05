package mangobomb.bombermango;

import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.texture.AnimationChannel;
import com.sun.scenario.effect.impl.sw.java.JSWBlend_GREENPeer;
import javafx.collections.WeakListChangeListener;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.awt.*;
import java.util.List;
import com.almasb.fxgl.texture.AnimatedTexture;
import static com.almasb.fxgl.dsl.FXGL.*;
import mangobomb.bombermango.BombermanType;
import org.jetbrains.annotations.NotNull;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static mangobomb.bombermango.BombermanType.*;
import static mangobomb.bombermango.HelloApplication.SCALED_SIZE;

public class GenerateFactory implements EntityFactory {

    @Spawns("Player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
//        physics.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(16, 38), BoundingShape.box(6, 8)));
//
//        // this avoids player sticking to walls
//        physics.setFixtureDef(new FixtureDef().friction(0.0f));


        FXGL.<HelloApplication>getAppCast();
        return entityBuilder(data)
                .atAnchored(new Point2D(24, 24), new Point2D(72, 168))
                .type(PLAYER)
                .zIndex(10)
                .scale(HelloApplication.ZOOM_RATIO, HelloApplication.ZOOM_RATIO)
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new IrremovableComponent())
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
