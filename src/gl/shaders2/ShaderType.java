package gl.shaders2;

public enum ShaderType
{

    TRANSFORM(0),
    HUD(1),
    DEBTH(2);
    public final int index;
    public AbstractShader shader;

    private ShaderType(int value)
    {
        index = value;
    }
}
