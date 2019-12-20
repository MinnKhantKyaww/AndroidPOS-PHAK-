package com.team.androidpos.ui.product;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.team.androidpos.R;
import com.team.androidpos.databinding.ProductEditBinding;
import com.team.androidpos.model.entity.Category;
import com.team.androidpos.model.entity.Product;
import com.team.androidpos.ui.Dismissible;
import com.team.androidpos.ui.MainActivity;
import com.team.androidpos.ui.OnBackPressed;
import com.team.androidpos.util.FileUtil;
import com.team.androidpos.util.PermissionUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class ProductEditFragment extends Fragment implements OnBackPressed {

    static final String KEY_PRODUCT_ID = "product_id";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PICK_IMAGE = 3;

    private ProductEditViewModel viewModel;
    private ProductEditBinding binding;
    private String currentPhotoFilePath;

    private View background;

    //https://github.com/adrianseraspi12/Android-Tutorials/tree/master/Start%20new%20activity%20with%20circular%20reveal%20transition
    //https://github.com/damanpreetsb?tab=repositories
    private final ChipGroup.OnCheckedChangeListener chipCheckListener = (chipGroup, id) -> {
        if (getView() == null) return;

        Chip chip = getView().findViewById(id);

        Product product = viewModel.product.getValue();

        if (chip == null) {
            product.setCategoryId(0);
            return;
        }

        product.setCategoryId((Integer) chip.getTag());
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        MainActivity activity = (MainActivity) requireActivity();
        activity.switchToggle(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null) {

        viewModel = ViewModelProviders.of(this).get(ProductEditViewModel.class);

        viewModel.categories.observe(this, categories -> {
            View view = getView();
            background = view.findViewById(R.id.productEditFragmemnt);
            if (view == null) return;
            background.setVisibility(View.INVISIBLE);
            /*;
            final ViewTreeObserver viewTreeObserver = background.getViewTreeObserver();
            if(viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        background.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }*/
            circularRevalFragment();

            ChipGroup categoryGroup = view.findViewById(R.id.chipGroupCategories);
            categoryGroup.removeAllViews();

            Product product = viewModel.product.getValue();

            for (Category c : categories) {
                Chip chip = new Chip(view.getContext());
                chip.setCheckable(true);
                chip.setText(c.getName());
                chip.setTag(c.getId());

                if (product.getCategoryId() == c.getId()) {
                    chip.setChecked(true);
                }

                categoryGroup.addView(chip);
            }

            categoryGroup.invalidate();
            categoryGroup.setOnCheckedChangeListener(chipCheckListener);

        });
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        binding = ProductEditBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        fragmentTransaction.addToBackStack(null);

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            viewModel.save();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton btnScan = view.findViewById(R.id.btnScan);
        btnScan.setOnClickListener(v -> {
            IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
            integrator.setOrientationLocked(false);
            integrator.initiateScan();
        });

        ImageButton btnTakePhoto = view.findViewById(R.id.btnTakePhoto);
        btnTakePhoto.setOnClickListener(v -> {
            BottomSheetDialog dialog = new BottomSheetDialog(requireContext());

            View bottomSheetView = getLayoutInflater().inflate(R.layout.layout_take_picture_action, null);
            TextView tvTakePhoto = bottomSheetView.findViewById(R.id.tvTakePhoto);
            TextView tvPickImage = bottomSheetView.findViewById(R.id.tvChooseGallery);

            tvTakePhoto.setOnClickListener(tv -> {
                dialog.dismiss();
                dispatchTakePictureIntent();
            });

            tvPickImage.setOnClickListener(tv -> {
                dialog.dismiss();
                dispatchChoosePictureIntent();
            });

            dialog.setContentView(bottomSheetView);
            dialog.show();
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.operation.observe(this, op -> {
            if (op) requireActivity().onBackPressed();
        });

        int id = getArguments() != null ? getArguments().getInt(KEY_PRODUCT_ID) : 0;
        viewModel.init(id);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && currentPhotoFilePath != null) {
            binding.btnTakePhoto.setImageURI(Uri.parse(currentPhotoFilePath));
            viewModel.product.getValue().setImage(currentPhotoFilePath);

        } else if(requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
            Uri photoURI = data.getData();
            try {
                Bitmap bitmap = FileUtil.writeImage(requireContext(), photoURI, createImageFile());
                 binding.btnTakePhoto.setImageBitmap(bitmap);
                viewModel.product.getValue().setImage(currentPhotoFilePath);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else if(result != null) {
            if (result.getContents() != null) {
                Product product = viewModel.product.getValue();
                product.setBarcode(result.getContents());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtil.PERMISSION_CAMERA) {
            if (grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MainActivity activity = (MainActivity) requireActivity();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        activity.switchToggle(true);
        activity.hideKeyboard();
        onBackPressed();
    }


    private void dispatchTakePictureIntent() {
        if (!PermissionUtil.hasCameraPermission(this)) {
            return;
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireContext().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
                // TODO show error message
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(requireContext(),
                        "com.team.androidpos.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    //choose from gallery
    private void dispatchChoosePictureIntent() {

        Intent contentIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentIntent.setType("image/*");
        //implict intent
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        Intent chooserIntent = Intent.createChooser(contentIntent, "Select Image Browser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickImageIntent});

        startActivityForResult(chooserIntent, REQUEST_PICK_IMAGE);
    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File image = File.createTempFile(imageFileName, ".jpg",
                requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        currentPhotoFilePath = image.getAbsolutePath();
        return image;
    }

    private void circularRevalFragment() {

        int hypotenuse = (int) Math.hypot(background.getWidth(), background.getHeight());

        int cx = background.getRight() - getDips(44);
        int cy = background.getBottom() - getDips(44);

        float finalRadius = Math.max(background.getWidth(), background.getHeight());

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                background,
                cx,
                cy,
                0,
                hypotenuse);

        circularReveal.setDuration(2000);

        circularReveal.setInterpolator(new FastOutSlowInInterpolator());

        background.setVisibility(View.VISIBLE);
        circularReveal.start();

    }

    private int getDips(int dps) {
        Resources resources = getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dps,
                resources.getDisplayMetrics());
    }

    private void startColorAnimation(final View view, final int startColor, final int endColor, int duration) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(startColor, endColor);
        valueAnimator.setEvaluator(new ArgbEvaluator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setBackgroundColor((Integer) animation.getAnimatedValue());
            }
        });

        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = background.getWidth() - getDips(44);
            int cy = background.getBottom() - getDips(44);

            float finalRadius = Math.max(background.getWidth(), background.getHeight());

            int hypotenuse = (int) Math.hypot(background.getWidth(), background.getHeight());

            Animator circularReveal = ViewAnimationUtils.createCircularReveal(background, cx, cy, hypotenuse, 0);

            circularReveal.setInterpolator(new FastOutSlowInInterpolator());

            circularReveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    background.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            circularReveal.setDuration(3000);
            circularReveal.start();
        } else {
            backFragments();
        }

    }

    private void backFragments() {
        List<Fragment> fragments = getFragmentManager().getFragments();
        for(Fragment f : fragments){
            if(f != null && f instanceof ProductEditFragment)
                ((ProductEditFragment)f).onBackPressed();
        }
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
