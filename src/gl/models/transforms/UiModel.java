package gl.models.transforms;

import game.world.basic.UiPositioning;
import gl.glUtils.ShaderControls;
import gl.models.ElementBuffer;
import gl.shaders.SharedShaderObjects;
import gl.shaders.TransformShader;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import utils.interfaces.IClickable;


public class UiModel extends ElementBuffer implements IClickable
{
    
    private static final Vector3f TRANSLATE = new Vector3f();
    private static final Vector3f SCALE = new Vector3f();
    
    private static final Matrix4f SHARED_TRANSFORM = SharedShaderObjects.SHARED_MODEL_TRANSFORM;
    private UiPositioning info;

    public UiModel(float[] vertices, int[] elements)
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
        ShaderControls.assignAtribute(positionAttribute, 3, 5, 0);
        //3 components, for X, Y
        //5 stride, for 5 floats per vertex
        //0 offset, for starts at 0

        int textureAttribute = ShaderControls.createAttribute(TransformShader.shader().getProgram(), "texcoord");
        ShaderControls.enableTextureAttrib(textureAttribute);
        ShaderControls.assignAtribute(textureAttribute, 2, 5, 2);
        //2 components, for U and V
        //5 stride, for 5 floats per vertex
        //2 offset, for starts at 2
        
        setupForClick();
    }
    
    @Override
    public void draw()
    {
        doTransformation();
        super.draw();
    }
    
    private void doTransformation()
    {
//        TRANSLATE.set(info.x, info.y, info.z);
//        SCALE.set(info.scale, info.scale, info.scale);
        
        SHARED_TRANSFORM.setIdentity();
        SHARED_TRANSFORM.translate(TRANSLATE);        
        SHARED_TRANSFORM.scale(SCALE);
    }
    
    private void setupForClick()
    {
//        int positionAttribute = ShaderControls.createAttribute(ClickShader.shader().getProgram(), "position");
//        ShaderControls.assignAtribute(positionAttribute, 3, 8, 0);
        //3 components, for X, Y and Z
        //8 stride, for 8 floats per vertex
        //0 offset, for starts at 0
    }

    @Override
    public void drawClick()
    {
    }

    @Override
    public void onClick(int mouseKey)
    {
    }
    
}
