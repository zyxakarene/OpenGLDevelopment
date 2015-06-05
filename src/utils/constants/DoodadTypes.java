package utils.constants;

public class DoodadTypes
{

    private static String[] idToName = new String[2];
    
    public static final int DOUBLE_ROCKS = 0;
    public static final int GRASS = 1;
    public static final String DOUBLE_ROCKS_NAME = "DoubleRocks";
    public static final String GRASS_NAME = "Grass";
    public static final String PLANE_GRASS = "Plane_Grass";
    public static final String CLIFF_STRAIGHT_GRASS = "Cliff_Straight_Grass";

    static
    {
        idToName[DOUBLE_ROCKS] = DOUBLE_ROCKS_NAME;
        idToName[GRASS] = GRASS_NAME;
    }
    
    public static String[] getAllNames()
    {
        return idToName;
    }

    public static String idToName(int id)
    {
        return idToName[id];
    }
}
