package game.physics;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.StaticPlaneShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MatrixUtil;
import com.bulletphysics.linearmath.QuaternionUtil;
import com.bulletphysics.linearmath.Transform;
import game.camera.Camera;
import game.physics.debug.PhysDebugger;
import java.util.ArrayList;
import java.util.HashMap;
import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import utils.FloatMath;

public class PhysicsManager
{

    public static PhysicsManager instance = new PhysicsManager();
    private DiscreteDynamicsWorld world;
    private Transform trans = new Transform();
    private ArrayList<PhysObject> objects;
    private HashMap<PhysObject, RigidBody> bodies;

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
    }

    public void addEntity(PhysObject object)
    {
        objects.add(object);
        addBodyToEntity(object);
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

//        for (int i = 0; i < 300; i++)
//        {
//            world.stepSimulation(1 / 60.f, 10);
//
//            Transform trans = new Transform();
//            fallRigidBody.getMotionState().getWorldTransform(trans);
//
//
//            System.out.println("sphere height: " + trans.origin.y);
//        }

    }

    public void draw()
    {
        world.debugDrawWorld();
    }

    public void update(float elapsedTime)
    {
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
        
//        phys.setPos(trans.origin.x, trans.origin.y, trans.origin.z);
//        Vector3f vec = columnMatrixToAngles(trans.basis);
//
////        float yaw = QuatHelper.getYaw(rotation);
////        float pitch = QuatHelper.getPitch(rotation);
////        float roll = QuatHelper.getRoll(rotation);
//        phys.setPitch(vec.x);
//        phys.setYaw(0);
//        phys.setRoll(0);
//        System.out.println(vec);
    }

    private Vector3f columnMatrixToAngles(Matrix3f mat)
    {
        Quat4f q = new Quat4f();
        MatrixUtil.getRotation(mat, q);
//        System.out.println(q);
//        return new Vector3f(q.x, q.y, q.z);
return getRotationFromQuat(q);
//        if (mat.m20 != 1f && mat.m20 != -1f)
//        {
//            float x1 = -FloatMath.asin(mat.m20);
//            float x2 = FloatMath.PI - x1;
//
//            float y1 = FloatMath.atan2(mat.m21 / FloatMath.cos(x1), mat.m22 / FloatMath.cos(x1));
//            float y2 = FloatMath.atan2(mat.m21 / FloatMath.cos(x2), mat.m22 / FloatMath.cos(x2));
//
//            float z1 = FloatMath.atan2(mat.m21 / FloatMath.cos(x1), mat.m00 / FloatMath.cos(x1));
//            float z2 = FloatMath.atan2(mat.m21 / FloatMath.cos(x2), mat.m00 / FloatMath.cos(x2));
//            
//            return new Vector3f(x1, y1, z1);
//        }
//        else
//        {
//            float x = 0f;
//            float y = 0f;
//            float z = 0f;
//            
//            if (mat.m20 == -1f)
//            {
//                x = FloatMath.PI/2;
//                y = z + FloatMath.atan2(mat.m01, mat.m02);
//            }
//            else
//            {
//                x = -FloatMath.PI / 2;
//                y = -z + FloatMath.atan2(-mat.m01, -mat.m02);
//            }
//            return new Vector3f(x, y, z);
//        }

//        float x = -FloatMath.sin(mat.m20);
//        float x1 = -FloatMath.asin(mat.m20);
//        float x2 = FloatMath.PI + FloatMath.asin(mat.m20);
//
//        float y = FloatMath.atan2(mat.m21 / FloatMath.cos(x), mat.m22 / FloatMath.cos(x));
//        float y1 = FloatMath.atan2(mat.m21 / FloatMath.cos(x1), mat.m22 / FloatMath.cos(x1));
//        float y2 = FloatMath.atan2(mat.m21 / FloatMath.cos(x2), mat.m22 / FloatMath.cos(x2));
//
//        float z = FloatMath.atan2(mat.m21 / FloatMath.cos(x), mat.m00 / FloatMath.cos(x));
//        float z1 = FloatMath.atan2(mat.m21 / FloatMath.cos(x1), mat.m00 / FloatMath.cos(x1));
//        float z2 = FloatMath.atan2(mat.m21 / FloatMath.cos(x2), mat.m00 / FloatMath.cos(x2));
//
//
//        if (FloatMath.cos(x) != 0)
//        {
//            return new Vector3f(x1, y1, z1);
//        }
//        else
//        {
//            System.out.println("as0d8");
//        }
//
//        return new Vector3f();
//        float sinPitch, cosPitch, sinRoll, cosRoll, sinYaw, cosYaw;
//
//        sinPitch = -mat.m20;
//        cosPitch = FloatMath.sqrt(1 - sinPitch * sinPitch);
//
//        if (FloatMath.abs(cosPitch) > 0)
//        {
//            sinRoll = mat.m21 / cosPitch;
//            cosRoll = mat.m22 / cosPitch;
//            sinYaw = mat.m10 / cosPitch;
//            cosYaw = mat.m00 / cosPitch;
//        }
//        else
//        {
//            sinRoll = -mat.m12;
//            cosRoll = mat.m11;
//            sinYaw = 0;
//            cosYaw = 1;
//        }
//
//        Vector3f angles = new Vector3f();
//        angles.y = FloatMath.atan2(sinYaw, cosYaw) * 180 / FloatMath.PI;
//        angles.x = FloatMath.atan2(sinPitch, cosPitch) * 180 / FloatMath.PI;
//        angles.z = FloatMath.atan2(sinRoll, cosRoll) * 180 / FloatMath.PI;
//        return angles;
    }

    public static Vector3f getRotationFromQuat(Quat4f quat)
    {
        return new Vector3f(QuatHelper.getPitch(quat), QuatHelper.getYaw(quat), QuatHelper.getRoll(quat));
    }
}
