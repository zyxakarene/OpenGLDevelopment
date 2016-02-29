package gl.models;

import gl.glUtils.BufferControls;
import org.lwjgl.opengl.GL11;

public abstract class ElementBuffer extends AbstractModel
{

    protected int ebo;
    protected int elementCount;

    public ElementBuffer()
    {
        ebo = BufferControls.generateEBO();
    }

    @Override
    protected void setVertexData(float[] vertexData, int elementCount)
    {
        BufferControls.bindVBO(vbo);
        BufferControls.fillVBO_Static(vertexData);
        this.elementCount = elementCount;
    }

    protected void setElementData(int[] elements)
    {
        BufferControls.bindEBO(ebo);
        BufferControls.fillEBO_Static(elements);
    }

    @Override
    protected final void clearBinds()
    {
        BufferControls.bindEBO(0);
        super.clearBinds();
    }

    @Override
    public void draw()
    {
        BufferControls.bindVAO(vao);
        BufferControls.bindEBO(ebo);
        GL11.glDrawElements(GL11.GL_TRIANGLES, elementCount, GL11.GL_UNSIGNED_INT, 0);
        clearBinds();
    }
}
