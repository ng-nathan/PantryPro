package com.pantrypro.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pantrypro.R;
import com.pantrypro.logic.ProfileTabHandler;
import com.pantrypro.objects.UserProfile;

/**
 * A fragment representing the user's profile.
 */
public class ProfileFragment extends Fragment {

    private TextView profileName, profileEmail, profileUsername, profilePassword, profileFeatureText;
    private TextView titleName, titleUsername;
    private UserProfile curUser;
    private boolean isActiveUser;
    private boolean isToggle = false;
    private Button deleteAcc;

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        if(isActiveUser){
            inflater.inflate(R.menu.view_profile_menu, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logoutProfile){
                showWarningDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFragment();
            }
        });

        profileName = view.findViewById(R.id.profileName);
        profileUsername = view.findViewById(R.id.profileUsername);
        profilePassword = view.findViewById(R.id.profilePassword);
        profileEmail = view.findViewById(R.id.profileEmail);
        titleName = view.findViewById(R.id.titleName);
        titleUsername = view.findViewById(R.id.titleUsername);

        curUser = ((MainActivity)requireActivity()).currentUser();
        isActiveUser = !ProfileTabHandler.isGuestUser(curUser.getUsername(), curUser.getPassword());

        initializeProfileInfo(view);
        setHasOptionsMenu(true);

        deleteAcc = view.findViewById(R.id.deleteAccount);

        if(!isActiveUser){
            profileFeatureText = view.findViewById(R.id.profileFeatureText);
            profileFeatureText.setText("Please log in to ultilize all PantryPros' features");
            deleteAcc.setText("Create an Account");
            deleteAcc.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.greenPastel));
        } else {
            deleteAcc.setText("Delete Account");
            deleteAcc.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.darkRed));
        }

        deleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isActiveUser){
                    AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
                    alert.setView(R.layout.warning_delete_account);
                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                            reference.child(curUser.getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        snapshot.getRef().removeValue();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.e("UserDatabase", "Failed to delete value.", error.toException());
                                }
                            });

                            Toast.makeText(requireContext(), "Account Deleted", Toast.LENGTH_SHORT).show();
                            requireActivity().finish();
                        }
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();

                    alertDialog.getWindow().setLayout(800, 400);

                    Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    Button btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
                    layoutParams.weight = 10;
                    btnPositive.setLayoutParams(layoutParams);
                    btnPositive.setTextColor(ContextCompat.getColor(requireContext(), R.color.darkRed));
                    btnPositive.setTextSize(15);
                    btnNegative.setLayoutParams(layoutParams);
                    btnNegative.setTextSize(15);
                } else {
                    Intent intent = new Intent(requireActivity(), SignupActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    requireActivity().finish();
                }
            }
        });

        return view;
    }

    private void initializeProfileInfo(View view){
        profileName.setText(curUser.getName());
        profileUsername.setText(curUser.getUsername());
        profileEmail.setText(curUser.getEmail());
        titleUsername.setText(curUser.getUsername());
        titleName.setText(curUser.getName());


        String maskedPass = ProfileTabHandler.maskedPassword(curUser.getPassword());
        profilePassword.setText(maskedPass);
        ImageView showPass = view.findViewById(R.id.profileShowPass);
        showPass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isToggle = !isToggle;
                if(isToggle){
                    profilePassword.setText(curUser.getPassword());
                    showPass.setColorFilter(ContextCompat.getColor(requireContext(), R.color.darkRed));
                } else {
                    profilePassword.setText(maskedPass);
                    showPass.setColorFilter(ContextCompat.getColor(requireContext(), R.color.greenPastel));
                }
            }
        });
    }



    private void showWarningDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
        alert.setView(R.layout.warning_logout);
        alert.setPositiveButton("YES, LET ME LEAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(requireContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                requireActivity().finish();
            }
        }).setNegativeButton("NO, I WANT TO STAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();

        alertDialog.getWindow().setLayout(800, 400);

        Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
        layoutParams.weight = 10;
        btnPositive.setLayoutParams(layoutParams);
        btnPositive.setTextColor(ContextCompat.getColor(requireContext(), R.color.darkRed));
        btnPositive.setTextSize(15);
        btnNegative.setLayoutParams(layoutParams);
        btnNegative.setTextSize(15);
    }

    private void closeFragment(){
        ((MainActivity)requireContext()).replaceFragment(new HomeFragment());
    }
}