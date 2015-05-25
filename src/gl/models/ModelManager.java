package gl.models;

import gl.models.transforms.TransformModel;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.constants.TileTypes;
import utils.exceptions.Msg;

public class ModelManager
{

    private static final HashMap<String, TransformModel> map = new HashMap<>();

    public static void loadAllTiles() throws IOException
    {
//        loadPlanes();
//        String[] modelNames = TileTypes.getAllNames();
//
//        for (String model : modelNames)
//        {
//            add(model);
//            System.out.println("Added model" + model);
//        }
//        map.put("robot", new ExternalModelManager().load("robot_normal.obj"));
//            map.put("fence", new ExternalModelManager().load("Fence.obj"));
//            map.put("scene", new ExternalModelManager().load("SampleScene.obj"));
//            map.put("scene2", new ExternalModelManager().load("SampleScene2.obj"));
    }

    private static void add(String modelName) throws IOException
    {
        map.put(modelName, new ExternalModelManager().load(modelName + ".obj"));
    }

    public static TransformModel getFromTileId(int id)
    {
        String path = TileTypes.idToName(id);
        return getModel(path);
    }

    public static TransformModel getModel(String path)
    {
        if (map.containsKey(path) ==false)
        {
            try
            {
                add(path);
            }
            catch (IOException ex)
            {
                Msg.error(path, ex);
            }
        }
        
        return map.get(path);
    }

    private static void loadPlanes()
    {
        final int[] elements =
        {
            2, 1, 0,
            0, 3, 2
        };


        final float[] vertices =
        {
            //Position         Texcoords         Normals
            -0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, // Top-left
            0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f,// Top-right
            0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f,// Bottom-right
            -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f// Bottom-left
        };

        TransformModel model = new TransformModel(vertices, elements);
        map.put("tile", model);
    }
}
