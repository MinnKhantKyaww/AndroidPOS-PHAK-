package com.team.androidpos.ui;

import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.ShapePath;
import com.google.android.material.shape.TriangleEdgeTreatment;

public class EdgeArc extends EdgeTreatment {

    private float size;

    public EdgeArc(float size) {
        this.size = size;
    }

    @Override
    public void getEdgePath(float length, float interpolation, ShapePath shapePath) {
        float center = length / 2f;
        //shapePath.reset(length, center);
        shapePath.lineTo(length / 2.0F - (this.size * 2) + 9, 0.0F);
        shapePath.quadToPoint(center, 20.0f * 2.5f - 8, center + 22.2f, 0f);
        shapePath.lineTo(length / 2.0F + (this.size * 2) + 9, 0.0F);
    }
}
