package mangobomb.bombermango;

import java.awt.*;
import java.util.List;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class PlayerComponent extends Component {
    public CellMoveComponent cell;
    public AStarMoveComponent astar;

    private int maxBombs = 1;
    private int bombsPlaced = 0;

    public void increaseMaxBombs() {
        maxBombs++;
    }

    public void placeBomb() {
        if (bombsPlaced == maxBombs) {
            return;
        }

        bombsPlaced++;

        Entity bomb = spawn("Bomb", new SpawnData(cell.getCellX() * 48, cell.getCellY() * 48).put("radius", HelloApplication.SCALED_SIZE / 2));

        getGameTimer().runOnceAfter(() -> {
            bomb.getComponent(Bomb.class).explode();
            bombsPlaced--;
        }, Duration.seconds(2));
    }


    private PhysicsComponent physics;
    private AnimatedTexture texture;
    private AnimationChannel PlayerWalk, PlayerIdle;

    public PlayerComponent() {
        PlayerWalk = new AnimationChannel(List.of(
                image("sprites/player_right.png"),
                image("sprites/player_right_1.png"),
                image("sprites/player_right_2.png")
        ), Duration.seconds(0.5));

        PlayerIdle = new AnimationChannel(List.of(
                image("sprites/player_down.png"),
                image("sprites/player_down.png"),
                image("sprites/player_down.png")
        ), Duration.seconds(0.5));

        texture = new AnimatedTexture(PlayerWalk);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);

    }

    @Override
    public void onUpdate(double tpf) {
        if (physics.isMovingX()) {
            if (texture.getAnimationChannel() != PlayerWalk) {
                texture.loopAnimationChannel(PlayerWalk);
            }
        } else {
            if (texture.getAnimationChannel() != PlayerIdle) {
                texture.loopAnimationChannel(PlayerIdle);
            }
        }
    }

    public void moveLeft() {
        getEntity().setScaleX(-1);
        physics.setVelocityX(-5);
    }
    public void moveRight() {
        getEntity().setScaleX(1);
        physics.setVelocityX(5);
    }
    public void moveUp() {
        physics.setVelocityY(-5);
    }
    public void moveDown() {
        physics.setVelocityY(5);
    }

}
