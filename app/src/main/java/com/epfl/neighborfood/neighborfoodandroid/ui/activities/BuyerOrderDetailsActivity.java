package com.epfl.neighborfood.neighborfoodandroid.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.epfl.neighborfood.neighborfoodandroid.NeighborFoodApplication;
import com.epfl.neighborfood.neighborfoodandroid.R;
import com.epfl.neighborfood.neighborfoodandroid.databinding.ActivityPastOrderDetailsBinding;
import com.epfl.neighborfood.neighborfoodandroid.models.Meal;
import com.epfl.neighborfood.neighborfoodandroid.models.Order;
import com.epfl.neighborfood.neighborfoodandroid.models.OrderStatus;
import com.epfl.neighborfood.neighborfoodandroid.models.User;
import com.epfl.neighborfood.neighborfoodandroid.ui.viewmodels.BuyerOrderDetailsActivityViewModel;
import com.epfl.neighborfood.neighborfoodandroid.ui.viewmodels.factories.BuyerOrderDetailsViewModelFactory;
import com.squareup.picasso.Picasso;


/**
 * This activity displays the details of a selected Past Order.
 */
public class BuyerOrderDetailsActivity extends AppCompatActivity {
    private Order order;
    private Meal meal;
    private User vendor;
    private ActivityPastOrderDetailsBinding binding;
    private BuyerOrderDetailsActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new BuyerOrderDetailsViewModelFactory((NeighborFoodApplication) this.getApplication())).get(BuyerOrderDetailsActivityViewModel.class);

        Intent intent = getIntent();
        String orderId = (String) intent.getSerializableExtra("orderId");
        viewModel.getOrderById(orderId).addOnSuccessListener(orderFetched -> {
            order = orderFetched;
            fetchOrderDetails();
        });

        binding = ActivityPastOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ConfirmationButton.setOnClickListener(e -> viewModel.confirmOrder(order)
                .addOnSuccessListener(t -> {
                    Toast.makeText(this, "the order has been delivered", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(BuyerOrderDetailsActivity.this, BuyerOrdersActivity.class);
                    startActivity(i);
                }));
    }

    private void fetchOrderDetails() {
        viewModel.getMealById(order.getMealId()).addOnSuccessListener(meal -> {
            this.meal = meal;
            updateMealDetails();
        });
        viewModel.getUserById(order.getVendorId()).addOnSuccessListener(user -> {
            this.vendor = user;
            updateVendorDetails();
        });
    }

    private void updateVendorDetails() {

        binding.goVendorProfileId.setText(vendor.getUsername());
        binding.goVendorProfileId.setOnClickListener(e ->
        {
            Intent intentVendor = new Intent(BuyerOrderDetailsActivity.this, VendorProfileActivity.class);
            intentVendor.putExtra("vendorId", vendor.getId());
            startActivity(intentVendor);

        });
    }

    private void updateMealDetails() {
        Picasso.get().load(meal.getImageUri()).into(binding.mealImage);
        binding.mealName.setText(meal.getName());
        binding.mealDesc.setText(meal.getDescription());
    }

}
