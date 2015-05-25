package utils.constants;

public class EnemyTypes
{

    private static String[] idToName = new String[1];
    
    public static final int TYPE_ONES = 0;
    public static final String TYPE_ONES_NAME = "Enemy1";

    static
    {
        idToName[TYPE_ONES] = TYPE_ONES_NAME;
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
