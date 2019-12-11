package com.team.androidpos.ui.sale;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.shape.CutCornerTreatment;
import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.google.android.material.shape.ShapePath;
import com.google.android.material.shape.ShapePathModel;
import com.google.android.material.shape.TriangleEdgeTreatment;
import com.team.androidpos.R;
import com.team.androidpos.databinding.SaleReceiptBinding;
import com.team.androidpos.model.entity.SaleProduct;
import com.team.androidpos.ui.DrawArch;
import com.team.androidpos.ui.MainActivity;

public class SaleReceiptFragment extends Fragment {
    static final String KEY_SALE_ID = "sale_id";
    static final String KEY_NAV_BACk = "nav_back";

    static final int NAV_SALE_PRODUCT = 1;
    static final int NAV_SALE_HISTORY = 2;

    private SaleReceiptBinding saleReceiptBinding;
    private SaleReceiptViewModel viewModel;

    private ShapePathModel shapePathModel = new ShapePathModel();
    private ShapePath shapePath = new ShapePath();

    private DrawArch drawArch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        viewModel = ViewModelProviders.of(this).get(SaleReceiptViewModel.class);
        int cornerSize = getResources().getDimensionPixelSize(R.dimen.corner_size);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        int nav = getArguments() != null ? getArguments().getInt(KEY_NAV_BACk, 1) : 1;
        if (nav == NAV_SALE_PRODUCT) {
            inflater.inflate(R.menu.menu_finish, menu);
            MainActivity activity = (MainActivity) requireActivity();
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_finish) {
            Navigation.findNavController(getView()).popBackStack(R.id.saleProductFragment, false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        saleReceiptBinding = SaleReceiptBinding.inflate(inflater, container, false);
        saleReceiptBinding.setLifecycleOwner(this);
        saleReceiptBinding.setViewModel(viewModel);


        return saleReceiptBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MaterialShapeDrawable shapeDrawable = new MaterialShapeDrawable(shapePathModel);
        MaterialCardView materialCardView = getView().findViewById(R.id.recepit_card);
        EdgeTreatment edgeTreatment = new EdgeTreatment();
        TriangleEdgeTreatment triangleEdgeTreatment = new TriangleEdgeTreatment(0f, true);
        BottomAppBarTopEdgeTreatment topEdgeTreatment = new BottomAppBarTopEdgeTreatment(2f, 2f, 2f);


        RectF rectRight = new RectF(materialCardView.getWidth() - 50,
                materialCardView.getHeight() - 50,
                materialCardView.getWidth() + 3 * 5f / 4,
                materialCardView.getHeight() + 50);

        //triangleEdgeTreatment.getEdgePath(5f, 0f, shapePath);
        //drawArch = new DrawArch(10f, false, 5f, 3f, rectRight.right, 4f, 0f, 90f, materialCardView);

        drawArch = new DrawArch();
        drawArch.setDrawArcEdge(materialCardView);
        //drawArch.setDrawArcEdge(2f, 3f, 30f, 4f, 0f, 90f, 10f, 10f, shapePath);
        //topEdgeTreatment.getEdgePath(5f, 4f, shapePath);

        //shapePathModel.setAllEdges(new TriangleEdgeTreatment(12f, true));
        //shapePathModel.setTopEdge(new EdgeTreatment().getEdgePath(0.2f, shapeDrawable.getInterpolation(), new shapePath()));
        // shapePathModel.setBottomEdge(new EdgeTreatment());
        shapePathModel.setTopEdge(new TriangleEdgeTreatment(20f, true));
        shapePathModel.setBottomEdge(new TriangleEdgeTreatment(20f, true));
//        shapePathModel.setLeftEdge(new EdgeTreatment().getEdgePath(shapeDrawable.getLevel(),
//                shapeDrawable.getInterpolation(), shapePath));
        //shapePathModel.setLeftEdge(new BottomAppBarTopEdgeTreatment(10f, 5f, 8f));
        shapePathModel.setAllCorners(new RoundedCornerTreatment(15f));
        shapePathModel.setLeftEdge(new TriangleEdgeTreatment(8f, true));
        shapePathModel.setRightEdge(new TriangleEdgeTreatment(8f, true));

        shapeDrawable.setShadowRadius(5);
        shapeDrawable.setShadowEnabled(true);
        shapeDrawable.setShadowElevation(10);

        MainActivity activity = (MainActivity) requireActivity();
        activity.switchToggle(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        long saleId = getArguments() != null ? getArguments().getLong(KEY_SALE_ID, 0) : 0;
        if (saleId > 0) viewModel.saleId.setValue(saleId);

        viewModel.saleProductLiveData.observe(this, list -> {

            saleReceiptBinding.linearLayoutItems.removeAllViews();

            for (SaleProduct sp : list) {
                View itemView = getLayoutInflater().inflate(R.layout.layout_recepit_items, saleReceiptBinding.linearLayoutItems, false);

                TextView tvQty = itemView.findViewById(R.id.tvQty);
                TextView tvItemDesc = itemView.findViewById(R.id.tvItemDesc);
                TextView tvPrice = itemView.findViewById(R.id.tvPrice);

                tvQty.setText(String.valueOf(sp.getQuantity()));
                tvItemDesc.setText(sp.getProductDescription());
                double total = sp.getTotalPrice();
                int abs = (int) total;
                if (total - abs > 0.0) {
                    tvPrice.setText(String.valueOf(total));
                } else {
                    tvPrice.setText(String.valueOf(abs));
                }

                shapeDrawable.setTint(ContextCompat.getColor(this.getContext(), R.color.colorMilk));
                shapeDrawable.setPaintStyle(Paint.Style.FILL);
                shapeDrawable.setShadowColor(Color.parseColor("#8B535353"));
                shapeDrawable.setUseTintColorForShadow(true);
                saleReceiptBinding.linearLayoutItems.addView(itemView);
                materialCardView.setCardElevation(8);
                materialCardView.setRadius(5);
                materialCardView.setBackground(shapeDrawable);
            }

            saleReceiptBinding.linearLayoutItems.invalidate();
        });

    }


}
