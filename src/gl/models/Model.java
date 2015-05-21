package gl.models;

import gl.glUtils.BufferControls;
import org.lwjgl.opengl.GL11;

public abstract class Model extends AbstractModel
{

    private int vertexCount;

    public Model()
    {
    }

    @Override
    protected void setVertexData(float[] vertexData, int vertexCount)
    {
        BufferControls.bindVBO(vbo);
        BufferControls.bindVAO(vao);
        BufferControls.fillVBO_Static(vertexData);
        this.vertexCount = vertexCount;
    }

    @Override
    public void draw()
    {
        BufferControls.bindVBO(vbo);
        BufferControls.bindVAO(vao);

        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);

        clearBinds();
    }
}
