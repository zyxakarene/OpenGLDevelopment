package gl.shaders;

public class ClickShader extends AbstractShader
{

    private static ClickShader instance;
    
    private int clickColorUniform;
    private int modelUniform;
    private int viewUniform;
    private int projUniform;

    ClickShader()
    {
        instance = this;
    }

    public static ClickShader shader()
    {
        return instance;
    }

    @Override
    protected String getVertexName()
    {
        return "ClickVertex.shader";
    }

    @Override
    protected String getFragmentName()
    {
        return "ClickFragment.shader";
    }
}
