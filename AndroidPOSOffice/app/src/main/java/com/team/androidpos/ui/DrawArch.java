package com.team.androidpos.ui;

import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.shape.CornerTreatment;
import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.google.android.material.shape.ShapePath;
import com.google.android.material.shape.TriangleEdgeTreatment;
import com.team.androidpos.R;

public class DrawArch extends CornerTreatment {

    private final float size;
  //  private final boolean inside;

    public DrawArch(float size) {
        this.size = size;
       // this.inside = inside;
    }

    @Override
    public void getCornerPath(float angle, float interpolation, ShapePath shapePath) {
        super.getCornerPath(angle, interpolation, shapePath);
        shapePath.reset(0.0f, size * interpolation);
        shapePath.lineTo(size * interpolation, size * interpolation);
        shapePath.lineTo(size * interpolation, 0f);
    }

    /*@Override
    public void getEdgePath(float length, float interpolation, ShapePath shapePath) {
        super.getEdgePath(length, interpolation, shapePath);
        shapePath.quadToPoint(length / 2f,this.size * interpolation * 2, length, 0f);

    }*/

}
