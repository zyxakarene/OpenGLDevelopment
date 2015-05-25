package gl.models.transforms;

import game.world.basic.Positioning;
import gl.models.ElementBuffer;
import gl.glUtils.ShaderControls;
import gl.shaders.ClickShader;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import gl.shaders.SharedShaderObjects;
import gl.shaders.ShaderLoader;
import gl.shaders.ShaderType;
import gl.shaders.SimpleDepthShader;
import gl.shaders.TransformShader;
import utils.interfaces.IClickable;
import utils.interfaces.IShadowable;

public class TransformModel extends ElementBuffer implements IShadowable, IClickable
{

    private static final Matrix4f SHARED_TRANSFORM = SharedShaderObjects.SHARED_MODEL_TRANSFORM;
    private Positioning info;

    public TransformModel(float[] vertices, int[] elements)
    {
        setVertexData(vertices, elements.length);

        setElementData(elements);

        setup();
        clearBinds();
    }

    @Override
    protected void setup()
    {
        int positionAttribute = ShaderControls.createAttribute(TransformShader.shader().getProgram(), "position");
        ShaderControls.assignAtribute(positionAttribute, 3, 8, 0);
        //3 components, for X, Y and Z
        //8 stride, for 8 floats per vertex
        //0 offset, for starts at 0

        int textureAttribute = ShaderControls.createAttribute(TransformShader.shader().getProgram(), "texcoord");
        ShaderControls.enableTextureAttrib(textureAttribute);
        ShaderControls.assignAtribute(textureAttribute, 2, 8, 3);
        //2 components, for U and V
        //8 stride, for 8 floats per vertex
        //3 offset, for starts at 3
        
        int normalAttribute = ShaderControls.createAttribute(TransformShader.shader().getProgram(), "normal");
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
        TransformShader.shader().updateModelUniform();
        super.draw();
    }
    
    @Override
    public void drawClick()
    {
        doTransformation();
        ClickShader.shader().updateModelUniform();
        super.draw();
    }

    private void doTransformation()
    {
        SHARED_TRANSFORM.setIdentity();
        SHARED_TRANSFORM.translate(new Vector3f(info.x, info.y, info.z));
        SHARED_TRANSFORM.rotate((float) Math.toRadians(info.pitch), new Vector3f(1.0f, 0.0f, 0.0f));
        SHARED_TRANSFORM.rotate((float) Math.toRadians(info.roll), new Vector3f(0.0f, 1.0f, 0.0f));
        SHARED_TRANSFORM.rotate((float) Math.toRadians(info.yaw), new Vector3f(0.0f, 0.0f, 1.0f));
        SHARED_TRANSFORM.scale(new Vector3f(info.scale, info.scale, info.scale));
    }

    public void setPositionInfo(Positioning positionInfo)
    {
        this.info = positionInfo;
    }

    @Override
    public void drawShadow()
    {
        doTransformation();
        SimpleDepthShader.shader().updateModelUniform();
        super.draw();
    }

    @Override
    public void onClick(int mouseKey)
    {
        throw new UnsupportedOperationException("Implement");
    }
}
