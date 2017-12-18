package trekwars.players;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Brel extends AbstractPlayer {
    
    private final Spatial _brel;
    private final Node _spatialNode;
    private final DisrupterPulseList _pulses;
    private Date _lastPulseTime = new Date();
    private final float _secondsBetweenPulses = 1;
    private final Node _disrupterNode;
    
    public Brel(AssetManager assetManager, PlayerType playerType){
        super(playerType);
       
        _brel = assetManager.loadModel("Models/brel.j3o");
        Material brel_material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        _brel.setMaterial(brel_material);
        _brel.setLocalScale(0.3f);
        
        _spatialNode = new Node();
        _spatialNode.attachChild(_brel);
        _disrupterNode = new Node();
        _pulses = new DisrupterPulseList(
                assetManager,
                _disrupterNode,
                50,
                500,
                0.5f,
                2,
                new Vector3f()
        );
        
        attachChild(_spatialNode);
    }
    
    @Override
    protected Collection<Node> getOtherNodes() {
        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(_disrupterNode);
        return nodes;
    }

    @Override
    protected float getRotationalSpeed() {
        return (float)(Math.PI / 4);
    }

    @Override
    protected float getTranslationalSpeed() {
        return (float) 1.0;
    }
    
    @Override
    protected float getBoostMultiplier() {
        return (float)3.0f;
    }

    @Override
    protected float getBoostCapacity() {
        return 1;
    }

    @Override
    protected void autopilot(float tpf) {
        this.fire();
    }

    @Override
    protected Node getSpatialNode() {
        return _spatialNode;
    }

    @Override
    protected void onFireStart(float tpf) {
         if(((new Date().getTime() - _lastPulseTime.getTime()) / 1000f) > _secondsBetweenPulses){
            _lastPulseTime = new Date();
            _pulses.addPulse(
                _spatialNode.getWorldTranslation(),
                _spatialNode.getWorldRotation(),
                new Vector3f(-2f, -0.5f, 0f)
            );
            _pulses.addPulse(
                _spatialNode.getWorldTranslation(),
                _spatialNode.getWorldRotation(),
                new Vector3f(2f, -0.5f, 0f)
            );
        }
    }

    @Override
    protected void onFireStop(float tpf) {
        
    }
    
    @Override
    protected void onUpdate(float tpf) {
        _pulses.update(tpf);
    }
}
