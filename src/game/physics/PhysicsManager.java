package game.physics;

import com.bulletphysics.collision.broadphase.AxisSweep3;
import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.CollisionFilterGroups;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.CollisionFlags;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.dispatch.GhostPairCallback;
import com.bulletphysics.collision.dispatch.PairCachingGhostObject;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CapsuleShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.ConvexShape;
import com.bulletphysics.collision.shapes.StaticPlaneShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.character.KinematicCharacterController;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
import game.camera.Camera;
import game.physics.debug.LineModel;
import game.physics.debug.PhysDebugger;
import java.util.ArrayList;
import java.util.HashMap;
import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

public class PhysicsManager
{

    public static PhysicsManager instance = new PhysicsManager();
    private DiscreteDynamicsWorld world;
    private Transform trans = new Transform();
    private ArrayList<PhysObject> objects;
    private HashMap<PhysObject, RigidBody> bodies;
    private KinematicCharacterController character;
    private PairCachingGhostObject ghostObject;

    private PhysicsManager()
    {
        objects = new ArrayList<>();
        bodies = new HashMap<>();

        BroadphaseInterface broadphase = new DbvtBroadphase();
        DefaultCollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
        CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);

        SequentialImpulseConstraintSolver solver = new SequentialImpulseConstraintSolver();

        world = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);

        // set the gravity of our world
        world.setGravity(new Vector3f(0, 0, -10));

        // setup our collision shapes
        CollisionShape groundShape = new StaticPlaneShape(new Vector3f(0, 0, 1), -10);

        // setup the motion state
        DefaultMotionState groundMotionState = new DefaultMotionState(new Transform(new Matrix4f(new Quat4f(0, 0, 0, 1), new Vector3f(0, -1, 0), 1.0f)));

        RigidBodyConstructionInfo groundRigidBodyCI = new RigidBodyConstructionInfo(0, groundMotionState, groundShape, new Vector3f(0, 0, 0));
        RigidBody groundRigidBody = new RigidBody(groundRigidBodyCI);
        groundRigidBody.setFriction(0.8f);

        world.addRigidBody(groundRigidBody); // add our ground to the dynamic world.. 
        world.setDebugDrawer(new PhysDebugger());

        addCameraObject();
    }

    public void addEntity(PhysObject object)
    {
        objects.add(object);
        addBodyToEntity(object);
    }

    public void addStaticBockAt(float x, float y, float z)
    {
        // setup the motion state for the ball
        DefaultMotionState fallMotionState = new DefaultMotionState(new Transform(new Matrix4f(new Quat4f(0, 0, 0, 1), new Vector3f(x, y, z - 2.5f), 1.0f)));

        //This we're going to give mass so it responds to gravity 
        int mass = 0;

        Vector3f fallInertia = new Vector3f(0, 0, 0);
        CollisionShape fallShape = new BoxShape(new Vector3f(2.5f, 2.5f, 2.5f));
        fallShape.calculateLocalInertia(mass, fallInertia);

        RigidBodyConstructionInfo fallRigidBodyCI = new RigidBodyConstructionInfo(mass, fallMotionState, fallShape, fallInertia);
        RigidBody fallRigidBody = new RigidBody(fallRigidBodyCI);
        fallRigidBody.setFriction(0.9f);

        //now we add it to our physics simulation 
        world.addRigidBody(fallRigidBody);
    }

    private void addBodyToEntity(PhysObject object)
    {
        // setup the motion state for the ball
        DefaultMotionState fallMotionState = new DefaultMotionState(new Transform(new Matrix4f(new Quat4f(0, 0, 0, 1), new Vector3f(-object.getX(), -object.getY(), -object.getZ()), 1.0f)));

        //This we're going to give mass so it responds to gravity 
        int mass = 10;

        Vector3f fallInertia = new Vector3f(0, 0, 0);
        CollisionShape fallShape = new BoxShape(new Vector3f(1f, 1f, 1.5f));
        fallShape.calculateLocalInertia(mass, fallInertia);

        RigidBodyConstructionInfo fallRigidBodyCI = new RigidBodyConstructionInfo(mass, fallMotionState, fallShape, fallInertia);
        RigidBody fallRigidBody = new RigidBody(fallRigidBodyCI);
        org.lwjgl.util.vector.Vector3f dir = Camera.getDir();
        fallRigidBody.applyCentralForce(new Vector3f(dir.x * 10000, dir.y * 10000, 0));
        fallRigidBody.setFriction(0.9f);

        //now we add it to our physics simulation 
        world.addRigidBody(fallRigidBody);
        bodies.put(object, fallRigidBody);
    }

    public void draw()
    {
        world.debugDrawWorld();
    }

    public void update(float elapsedTime)
    {
        Transform transform = new Transform();
        transform.setIdentity();
        transform.origin.set(-Camera.getX(), -Camera.getY(), -Camera.getZ());
        org.lwjgl.util.vector.Vector3f dir = Camera.getDir();
//        character.setWalkDirection(new Vector3f(0.0000000000000000001f, 0, 0));

        world.stepSimulation(elapsedTime, 10);
    }

    void updateMe(PhysObject phys)
    {
        trans.setIdentity();
        RigidBody fallRigidBody = bodies.get(phys);
        fallRigidBody.getMotionState().getWorldTransform(trans);
        float[] glMatrix = new float[16];
        trans.getOpenGLMatrix(glMatrix);

        phys.updateFrom(glMatrix);
    }

    public void clicked(PhysObject model)
    {
        RigidBody fallRigidBody = bodies.get(model);
        fallRigidBody.activate(true);
        fallRigidBody.applyCentralForce(new Vector3f(0, 0, 10000f));
    }

    private void addCameraObject()
    {
        float characterScale = 1f;
        
        //from the source src\com\bulletphysics\demos\character\CharacterDemo.java
        Transform startTransform = new Transform();
        startTransform.setIdentity();
        startTransform.origin.set(30, 40, 0);

        Vector3f worldMin = new Vector3f(-1000f, -1000f, -1000f);
        Vector3f worldMax = new Vector3f(1000f, 1000f, 1000f);
        AxisSweep3 sweepBP = new AxisSweep3(worldMin, worldMax);

        ghostObject = new PairCachingGhostObject();
        ghostObject.setWorldTransform(startTransform);
        sweepBP.getOverlappingPairCache().setInternalGhostPairCallback(new GhostPairCallback());
        float characterHeight = 1.75f * characterScale;
        float characterWidth = 1.75f * characterScale;
        ConvexShape capsule = new CapsuleShape(characterWidth, characterHeight);
        ghostObject.setCollisionShape(capsule);
        ghostObject.setCollisionFlags(CollisionFlags.CHARACTER_OBJECT);

        float stepHeight = 0.35f * characterScale;
        character = new KinematicCharacterController(ghostObject, capsule, stepHeight);

        world.addCollisionObject(ghostObject, CollisionFilterGroups.CHARACTER_FILTER, (short) (CollisionFilterGroups.STATIC_FILTER | CollisionFilterGroups.DEFAULT_FILTER));

        world.addAction(character);
    }
}
