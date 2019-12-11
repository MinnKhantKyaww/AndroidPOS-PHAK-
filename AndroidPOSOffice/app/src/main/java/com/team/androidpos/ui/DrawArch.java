package com.team.androidpos.ui;

import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.ShapePath;
import com.google.android.material.shape.TriangleEdgeTreatment;
import com.team.androidpos.R;

public class DrawArch extends EdgeTreatment {


    private Path path = new Path();

    /*private final float size;
    private final boolean inside;*/
    private float left;
    private float top;
    private float right;
    private float bottom;
    private float startAngle;
    private float sweepAngle;

   /* public DrawArch(float size, boolean inside) {
        super(size, inside);
        this.size = size;
        this.inside = inside;
    }


    public DrawArch(float size, boolean inside, float left, float top, float right, float bottom, float startAngle,
                    float sweepAngle, View view) {
        super(size, inside);
        MaterialCardView materialCardView = view.findViewById(R.id.recepit_card);

        this.size = size;
        this.inside = inside;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.startAngle = startAngle;
        this.sweepAngle = sweepAngle;
        RectF rectRight = new RectF(materialCardView.getWidth() - 50,
                materialCardView.getHeight() - 50,
                materialCardView.getWidth() + 3 * 5f / 4,
                materialCardView.getHeight() + 50);
        path.arcTo(rectRight, startAngle, sweepAngle);
    }*/



    @Override
    public void getEdgePath(float length, float interpolation, ShapePath shapePath) {
        super.getEdgePath(length, interpolation, shapePath);
    }

    public void setDrawArcEdge(View view) {

        //shapePath.addArc(left, top, right, bottom, startAngle, sweepAngle);
        //shapePath.lineTo( 40f, 50f);

        //path.arcTo(new RectF(left, top, right, bottom), startAngle, sweepAngle);
        //shapePath.lineTo(length, 0.0F);
        // shapePath.lineTo(20f, 20f);
        /*shapePath.lineTo(length / 2.0F - this.size * interpolation, 0.0F);
        shapePath.lineTo(length / 2.0F, this.inside ? this.size * interpolation : -this.size * interpolation);
        shapePath.lineTo(length / 2.0F + this.size * interpolation, 0.0F);
        shapePath.lineTo(length, 0.0F);*/

        MaterialCardView materialCardView = view.findViewById(R.id.recepit_card);

        RectF rectLeftTop = new RectF(-50, -50, 50, 50);
        RectF rectRightTop = new RectF( materialCardView.getWidth() - 50, -50, materialCardView.getWidth() + 50, 50);
        RectF rectLeftBottom = new RectF(-50, materialCardView.getHeight() - 50, 50, materialCardView.getHeight() + 50);
        RectF rectRightBottom = new RectF(materialCardView.getWidth() - 50, materialCardView.getHeight() - 50,
                materialCardView.getWidth() + 50, materialCardView.getHeight() + 50);

        path.arcTo(rectLeftTop, 0, 90, false);
        path.lineTo(0, materialCardView.getHeight() - 50);
        path.arcTo(rectLeftBottom, 270, 90, false);
        path.lineTo(materialCardView.getWidth() - 50, materialCardView.getHeight());
        path.arcTo(rectRightBottom, 180, 90, false);
        path.lineTo(materialCardView.getWidth(), 50);
        path.arcTo(rectRightTop, 90, 90, false);
        path.moveTo(materialCardView.getWidth() - 50, 0);
        path.lineTo(50, 0);
        path.close();
    }
}
