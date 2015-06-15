/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.devGui;

import game.world.basic.GameEntity;

/**
 *
 * @author Rene
 */
public class ControlGui extends javax.swing.JFrame
{

    private GameEntity entity;

    public ControlGui(GameEntity plane)
    {
        this.entity = plane;

        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        pitchSlide = new javax.swing.JSlider();
        yawSlide = new javax.swing.JSlider();
        rollSlide = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        zSlide = new javax.swing.JSlider();
        ySlide = new javax.swing.JSlider();
        xSlide = new javax.swing.JSlider();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pitchSlide.setMaximum(360);
        pitchSlide.setMinimum(-360);
        pitchSlide.setValue(0);
        pitchSlide.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseDragged(java.awt.event.MouseEvent evt)
            {
                pitchSlideMouseDragged(evt);
            }
        });

        yawSlide.setMaximum(360);
        yawSlide.setMinimum(-360);
        yawSlide.setPaintLabels(true);
        yawSlide.setPaintTicks(true);
        yawSlide.setValue(0);
        yawSlide.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseDragged(java.awt.event.MouseEvent evt)
            {
                yawSlideMouseDragged(evt);
            }
        });

        rollSlide.setMaximum(360);
        rollSlide.setMinimum(-360);
        rollSlide.setPaintLabels(true);
        rollSlide.setPaintTicks(true);
        rollSlide.setValue(0);
        rollSlide.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseDragged(java.awt.event.MouseEvent evt)
            {
                rollSlideMouseDragged(evt);
            }
        });

        jLabel1.setText("Pitch");

        jLabel2.setText("Yaw");

        jLabel3.setText("Roll");

        zSlide.setMaximum(1000);
        zSlide.setMinimum(-1000);
        zSlide.setValue(0);
        zSlide.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseDragged(java.awt.event.MouseEvent evt)
            {
                zSlideMouseDragged(evt);
            }
        });

        ySlide.setMaximum(1000);
        ySlide.setMinimum(-1000);
        ySlide.setValue(0);
        ySlide.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseDragged(java.awt.event.MouseEvent evt)
            {
                ySlideMouseDragged(evt);
            }
        });

        xSlide.setMaximum(1000);
        xSlide.setMinimum(-1000);
        xSlide.setValue(0);
        xSlide.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseDragged(java.awt.event.MouseEvent evt)
            {
                xSlideMouseDragged(evt);
            }
        });

        jLabel4.setText("x");

        jLabel5.setText("y");

        jLabel6.setText("z");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(53, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rollSlide, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                            .addComponent(pitchSlide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(yawSlide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(zSlide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(xSlide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ySlide, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pitchSlide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yawSlide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rollSlide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(xSlide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ySlide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(zSlide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(93, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pitchSlideMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_pitchSlideMouseDragged
    {//GEN-HEADEREND:event_pitchSlideMouseDragged
        entity.setPitch(pitchSlide.getValue());
    }//GEN-LAST:event_pitchSlideMouseDragged

    private void yawSlideMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_yawSlideMouseDragged
    {//GEN-HEADEREND:event_yawSlideMouseDragged
        entity.setYaw(yawSlide.getValue());
    }//GEN-LAST:event_yawSlideMouseDragged

    private void rollSlideMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_rollSlideMouseDragged
    {//GEN-HEADEREND:event_rollSlideMouseDragged
        entity.setRoll(rollSlide.getValue());
        System.out.println(rollSlide.getValue());
    }//GEN-LAST:event_rollSlideMouseDragged

    private void zSlideMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_zSlideMouseDragged
    {//GEN-HEADEREND:event_zSlideMouseDragged
        entity.setZ((float) zSlide.getValue() / 100);
    }//GEN-LAST:event_zSlideMouseDragged

    private void ySlideMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ySlideMouseDragged
    {//GEN-HEADEREND:event_ySlideMouseDragged
        entity.setY((float) ySlide.getValue() / 100);
    }//GEN-LAST:event_ySlideMouseDragged

    private void xSlideMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_xSlideMouseDragged
    {//GEN-HEADEREND:event_xSlideMouseDragged
        entity.setX((float) xSlide.getValue() / 100);
    }//GEN-LAST:event_xSlideMouseDragged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSlider pitchSlide;
    private javax.swing.JSlider rollSlide;
    private javax.swing.JSlider xSlide;
    private javax.swing.JSlider ySlide;
    private javax.swing.JSlider yawSlide;
    private javax.swing.JSlider zSlide;
    // End of variables declaration//GEN-END:variables
}
