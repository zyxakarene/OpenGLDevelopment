/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.physics.debug;

import com.bulletphysics.linearmath.DebugDrawModes;
import com.bulletphysics.linearmath.IDebugDraw;
import game.ai.towers.Tower;
import gl.shaders.LineShader;
import javax.vecmath.Vector3f;

/**
 *
 * @author Rene
 */
public class PhysDebugger extends IDebugDraw
{

    
    @Override
    public void drawLine(Vector3f from, Vector3f to, Vector3f color)
    {
//        System.out.println(from + " " + to + " @ " + color);
        LineShader.shader().setLineColor(color.x, color.y, color.z);
        LineModel.setPos(from.x, from.y, from.z, to.x, to.y, to.z);
        LineModel.draw();
    }

    @Override
    public void drawContactPoint(Vector3f PointOnB, Vector3f normalOnB, float distance, int lifeTime, Vector3f color)
    {
//        LineShader.shader().setLineColor(color.x, color.y, color.z);
//        LineModel.setPos(PointOnB.x, PointOnB.y, PointOnB.z, normalOnB.x, normalOnB.y, normalOnB.z);
//        LineModel.draw();
    }

    @Override
    public void reportErrorWarning(String warningString)
    {
        System.out.println(warningString);
    }

    @Override
    public void draw3dText(Vector3f location, String textString)
    {
        System.out.println(textString + " @ " + location);
    }

    @Override
    public void setDebugMode(int debugMode)
    {
    }

    @Override
    public int getDebugMode()
    {
        return DebugDrawModes.DRAW_WIREFRAME;
    }
}
