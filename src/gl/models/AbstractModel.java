package gl.models;

import gl.glUtils.BufferControls;
import utils.interfaces.IDrawable;

public abstract class AbstractModel implements IDrawable
{

    protected int vbo;
    protected int vao;

    public AbstractModel()
    {
        vao = BufferControls.generateVAO();
        BufferControls.bindVAO(vao);
        
        vbo = BufferControls.generateVBO();
    }

    protected abstract void setVertexData(float[] vertexData, int renderCount);

    protected void clearBinds()
    {
        BufferControls.bindVAO(0);
        BufferControls.bindVBO(0);
    }

    @Override
    public abstract void draw();

    protected abstract void setup();
}
