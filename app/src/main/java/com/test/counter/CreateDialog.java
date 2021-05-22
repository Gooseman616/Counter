package com.test.counter;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.jetbrains.annotations.NotNull;

public class CreateDialog extends AppCompatDialogFragment {

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View addDialog = LayoutInflater.from(getContext())
                .inflate(R.layout.d_add_counter, null, false);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        builder.setTitle(R.string.add_counter);
        builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss());
        builder.setPositiveButton(getString(R.string.add), (dialog, which) -> {
            String inputName = ((EditText) addDialog.findViewById(R.id.textinput_counter_name))
                    .getText()
                    .toString();
            if (inputName.isEmpty()) {
                inputName = getString(R.string.default_counter_name);
            }
            Repository.getInstance(getContext()).addCounter(inputName);
            Toast.makeText(getContext(), String.format("%s added", inputName), Toast.LENGTH_SHORT).show();
        });
        builder.setView(addDialog);
        Dialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;
    }
}
