package utils.constants;

public class TileTypes
{

    private static String[] idToName = new String[8];
    
    public static final int PLANE = 0;
    public static final int LANE_STRAIGHT = 1;
    public static final int LANE_CORNER = 2;
    public static final int LANE_CROSS = 3;
    public static final int LANE_RISE = 4;
    public static final int CLIFF_STRAIGHT = 5;
    public static final int CLIFF_CORNER = 6;
    public static final int CLIFF_INNER_CORNER = 7;
    public static final String PLANE_NAME = "Plane";
    public static final String LANE_STRAIGHT_NAME = "LaneStraight";
    public static final String LANE_CORNER_NAME = "LaneCorner";
    public static final String LANE_CROSS_NAME = "LaneCross";
    public static final String LANE_RISE_NAME = "LaneRise";
    public static final String CLIFF_STRAIGHT_NAME = "CliffStraight";
    public static final String CLIFF_CORNER_NAME = "CliffCorner";
    public static final String CLIFF_INNER_CORNER_NAME = "CliffInnerCorner";

    static
    {
        idToName[PLANE] = PLANE_NAME;
        idToName[LANE_STRAIGHT] = LANE_STRAIGHT_NAME;
        idToName[LANE_CORNER] = LANE_CORNER_NAME;
        idToName[LANE_CROSS] = LANE_CROSS_NAME;
        idToName[LANE_RISE] = LANE_RISE_NAME;
        idToName[CLIFF_STRAIGHT] = CLIFF_STRAIGHT_NAME;
        idToName[CLIFF_CORNER] = CLIFF_CORNER_NAME;
        idToName[CLIFF_INNER_CORNER] = CLIFF_INNER_CORNER_NAME;
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
