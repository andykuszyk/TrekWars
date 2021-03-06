package trekwars.players;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public interface IPlayer {
    void turnRight();
    void turnLeft();
    void boost();
    void fire();
    void stop();
    void update(float tpf);
    Node getRootNode();
    Iterable<Node> getRootNodes();
    PlayerType getPlayerType();
    Iterable<Spatial> getWeaponSpatials();
}
