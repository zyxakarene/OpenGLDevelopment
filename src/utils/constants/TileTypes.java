package utils.constants;

import java.util.HashMap;

public class TileTypes
{

    private static final HashMap<String, Integer> nameToId = new HashMap<>();
    private static final String[] idToName = new String[8];
    
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

    public static boolean isLane(int id)
    {
        return id >= LANE_STRAIGHT && id <= LANE_RISE;
    }
    
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
        
        nameToId.put(PLANE_NAME, PLANE);
        nameToId.put(LANE_STRAIGHT_NAME, LANE_STRAIGHT);
        nameToId.put(LANE_CORNER_NAME, LANE_CORNER);
        nameToId.put(LANE_CROSS_NAME, LANE_CROSS);
        nameToId.put(LANE_RISE_NAME, LANE_RISE);
        nameToId.put(CLIFF_STRAIGHT_NAME, CLIFF_STRAIGHT);
        nameToId.put(CLIFF_CORNER_NAME, CLIFF_CORNER);
        nameToId.put(CLIFF_INNER_CORNER_NAME, CLIFF_INNER_CORNER);
    }
    
    public static String[] getAllNames()
    {
        return idToName;
    }

    public static int nameToId(String name)
    {
        return nameToId.get(name);
    }
    
    public static String idToName(int id)
    {
        return idToName[id];
    }
}
