package trekwars.screens;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import java.util.Iterator;
import trekwars.core.InputMappings;
import trekwars.players.IPlayer;

public class BasicStarfield implements IScreen {
    
    private final Node _rootNode;
    private final IPlayer _player;

    public BasicStarfield(
            IPlayer player, 
            Iterable<IPlayer> enemyWaveOne,
            Iterable<IPlayer> enemyWaveTwo,
            Iterable<IPlayer> enemyWaveThree,
            AssetManager assetManager){
        if(player == null){
            //TODO: throw new ArgumentNullException();
        }
      
        _rootNode = new Node();
        _player = player;
        
        Sphere sphere = new Sphere(100, 100, 100);
        sphere.scaleTextureCoordinates(new Vector2f(10,10));
        Geometry starSphere = new Geometry("StarSphere", sphere);
        starSphere.scale(-1);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture starTexture = assetManager.loadTexture("Textures/starscape.jpg");
        starTexture.setWrap(Texture.WrapMode.Repeat);
        mat.setTexture("ColorMap", starTexture);
        starSphere.setMaterial(mat);
        
        _rootNode.attachChild(starSphere);
        _rootNode.attachChild(player.getRootNode());
        attachRootNodes(enemyWaveOne);
        attachRootNodes(enemyWaveTwo);
        attachRootNodes(enemyWaveThree);
        
        player.getRootNode().setLocalTranslation(0, 0, -10);
    }
    
    private void attachRootNodes(Iterable<IPlayer> players){
        if(players == null) return;
        Iterator<IPlayer> iterator = players.iterator();
        while(iterator.hasNext()){
            _rootNode.attachChild(iterator.next().getRootNode());
        }
    }
    
    public Node getRootNode() {
        return _rootNode;
    }

    public void update(float tpf) {
        // Update here
    }

    public void onAnalog(String name, float keyPressed, float tpf) {
        if(name == null) return;
        
        if (name.equals(InputMappings.left)) {
            _player.turnLeft(tpf);
        }
        else if (name.equals(InputMappings.right)) {
            _player.turnRight(tpf);
        }
        else if (name.equals(InputMappings.boost)) {
            _player.boost(tpf);
        }
        else if (name.equals(InputMappings.fire)){
            _player.fire(tpf);
        }
    }
}
