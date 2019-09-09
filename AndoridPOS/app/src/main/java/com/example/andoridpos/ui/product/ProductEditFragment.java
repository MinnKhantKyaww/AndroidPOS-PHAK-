package com.example.andoridpos.ui.product;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.andoridpos.R;
import com.example.andoridpos.databinding.ProductEditBinding;
import com.example.andoridpos.model.entity.Category;
import com.example.andoridpos.model.entity.Product;
import com.example.andoridpos.ui.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class ProductEditFragment extends Fragment {


    static final String KEY_PRODUCT_ID = "product_id";

    private static final int REQUEST_IMAEG_CAPTURE = 1;

    private static final int PERMISSION_TAKE_PHOTO = 2;

    private ProductsEditViewModel viewModel;
    private ProductEditBinding binding;

    private String currentPhotoFilePath;

    private final ChipGroup.OnCheckedChangeListener chipCheckListener = (chipGroup, id) -> {
        if(getView() == null) return;
        Chip chip = getView().findViewById(id);
        Product product = viewModel.product.getValue();

        if(chip == null) {
            product.setCategoryId(0);
            return;
        }
        product.setCategoryId((Integer) chip.getTag());
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        MainActivity mainActivity = (MainActivity) requireActivity();
        mainActivity.switchToggle(false);
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = ViewModelProviders.of(this).get(ProductsEditViewModel.class);

        viewModel.categories.observe(this, categories -> {
            View view = getView();

            if(view == null) {

            }

            ChipGroup categoryGroup = view.findViewById(R.id.chipGroupCategories);
            categoryGroup.removeAllViews();

            Product product = viewModel.product.getValue();

            for(Category c : categories) {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = ProductEditBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_save) {

            viewModel.save();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton btnScan = view.findViewById(R.id.btnScan);

        ImageButton btnTakePhoto = view.findViewById(R.id.btnTakePhoto);

        btnScan.setOnClickListener(v -> {
          IntentIntegrator intentIntegrator =  IntentIntegrator.forSupportFragment(this);
          intentIntegrator.setOrientationLocked(false);
          intentIntegrator.initiateScan();
        });

        /*btnTakePhoto.setOnClickListener(v -> {

            BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
            View bottomsheetView = getLayoutInflater().inflate(R.layout.layout_take_picture_action, null);
            TextView tvTakePhoto = bottomsheetView.findViewById(R.id.tvTakePhoto);
            //TextView tvChooseGallery = bottomsheetView.findViewById(R.id.tvChooseGallery);

            tvTakePhoto.setOnClickListener(tv -> {
                dialog.dismiss();
                dispatchTakePictureIntent();
            });

            dialog.setContentView(bottomsheetView);
            dialog.show();
        });*/
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

        if(requestCode == REQUEST_IMAEG_CAPTURE && resultCode == RESULT_OK) {
         /*   Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");*/
            binding.btnTakePhoto.setImageURI(Uri.parse(currentPhotoFilePath));
            viewModel.product.getValue().setImage(currentPhotoFilePath);
            
        } else if(result != null) {

            if(result.getContents() != null) {
                Product product = viewModel.product.getValue();
                product.setBarcode(result.getContents());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if( requestCode == PERMISSION_TAKE_PHOTO) {

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MainActivity mainActivity = (MainActivity) requireActivity();
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mainActivity.switchToggle(true);
        mainActivity.hideKeyboard();
    }

    //ExplictIntent_Camera
    private void dispatchTakePictureIntent() {


        if(ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.CAMERA) !=
        PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    requireActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_TAKE_PHOTO);

            return;
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePictureIntent.resolveActivity(requireContext().getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }


            if(photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(requireContext(), "com.example.andoridpos.fileprovider", photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAEG_CAPTURE);
            }

        }
    }

    //create_temp_img_file
    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File image = File.createTempFile(imageFileName, ".jpg", requireContext()
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        currentPhotoFilePath = image.getAbsolutePath();
        return image;
    }
}
