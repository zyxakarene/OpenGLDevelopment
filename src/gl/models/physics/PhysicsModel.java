package gl.models.physics;

import game.world.basic.Positioning;
import gl.models.ElementBuffer;
import gl.glUtils.ShaderControls;
import gl.shaders.ClickShader;
import gl.shaders.PhysShader;
import gl.shaders.ShaderLoader;
import gl.shaders.ShaderType;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import gl.shaders.SharedShaderObjects;
import gl.shaders.SimpleDepthShader;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import utils.interfaces.IClickable;
import utils.interfaces.IShadowable;

public class PhysicsModel extends ElementBuffer implements IShadowable, IClickable
{

    private static final Matrix4f SHARED_TRANSFORM = SharedShaderObjects.SHARED_MODEL_TRANSFORM;
    private Positioning info;
    public float[] physMatrix;

    public PhysicsModel(float[] vertices, int[] elements)
    {
        physMatrix = new float[16];
        setVertexData(vertices, elements.length);

        setElementData(elements);

        setup();
        clearBinds();
    }

    @Override
    protected void setup()
    {
        int positionAttribute = ShaderControls.createAttribute(PhysShader.shader().getProgram(), "position");
        ShaderControls.assignAtribute(positionAttribute, 3, 8, 0);
        //3 components, for X, Y and Z
        //8 stride, for 8 floats per vertex
        //0 offset, for starts at 0

        int textureAttribute = ShaderControls.createAttribute(PhysShader.shader().getProgram(), "texcoord");
        ShaderControls.enableTextureAttrib(textureAttribute);
        ShaderControls.assignAtribute(textureAttribute, 2, 8, 3);
        //2 components, for U and V
        //8 stride, for 8 floats per vertex
        //3 offset, for starts at 3
        
        int normalAttribute = ShaderControls.createAttribute(PhysShader.shader().getProgram(), "normal");
        ShaderControls.assignAtribute(normalAttribute, 3, 8, 5);
        //3 components, for X, Y and Z
        //8 stride, for 8 floats per vertex
        //5 offset, for starts at 5
        
        setupForShadows();
        setupForClick();
    }
    
    private void setupForShadows()
    {
        int positionAttribute = ShaderControls.createAttribute(SimpleDepthShader.shader().getProgram(), "position");
        ShaderControls.assignAtribute(positionAttribute, 3, 8, 0);
        //3 components, for X, Y and Z
        //8 stride, for 8 floats per vertex
        //0 offset, for starts at 0
        
        int textureAttribute = ShaderControls.createAttribute(SimpleDepthShader.shader().getProgram(), "texcoord");
        ShaderControls.enableTextureAttrib(textureAttribute);
        ShaderControls.assignAtribute(textureAttribute, 2, 8, 3);
        //2 components, for U and V
        //8 stride, for 8 floats per vertex
        //3 offset, for starts at 3
        
        int normalAttribute = ShaderControls.createAttribute(SimpleDepthShader.shader().getProgram(), "normal");
        ShaderControls.assignAtribute(normalAttribute, 3, 8, 5);
        //3 components, for X, Y and Z
        //8 stride, for 8 floats per vertex
        //5 offset, for starts at 5
    }
    
    private void setupForClick()
    {
        int positionAttribute = ShaderControls.createAttribute(ClickShader.shader().getProgram(), "position");
        ShaderControls.assignAtribute(positionAttribute, 3, 8, 0);
        //3 components, for X, Y and Z
        //8 stride, for 8 floats per vertex
        //0 offset, for starts at 0
    }

    @Override
    public void draw()
    {
        doTransformation();
//        TransformShader.shader().updateModelUniform();
        super.draw();
    }
    
    @Override
    public void drawClick()
    {
        doSimpleTransformation();
        ClickShader.shader().updateModelUniform();
        super.draw();
    }

    private static final Vector3f SCALE = new Vector3f();
    
    private void doSimpleTransformation()
    {
        SCALE.set(info.scale, info.scale, info.scale);    
        
        FloatBuffer b = BufferUtils.createFloatBuffer(physMatrix.length);
        b.put(physMatrix);
        b.flip();
        
        SHARED_TRANSFORM.load(b);
        SHARED_TRANSFORM.scale(SCALE);
    }
    
    private void doTransformation()
    {
        SCALE.set(info.scale, info.scale, info.scale);    
        
        FloatBuffer b = BufferUtils.createFloatBuffer(physMatrix.length);
        b.put(physMatrix);
        b.flip();
        
        SHARED_TRANSFORM.load(b);
        SHARED_TRANSFORM.scale(SCALE);
        ShaderLoader.activateShader(ShaderType.PHYS);
        PhysShader.shader().updateModelUniform();
    }

    public void setPositionInfo(Positioning positionInfo)
    {
        this.info = positionInfo;
    }

    @Override
    public void drawShadow()
    {
        doSimpleTransformation();
        SimpleDepthShader.shader().updateModelUniform();
        super.draw();
    }

    @Override
    public void onClick(int mouseKey)
    {
        throw new UnsupportedOperationException("Implement");
    }
}
