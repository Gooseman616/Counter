package com.test.counter;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.jetbrains.annotations.NotNull;

public class CreateDialog extends AppCompatDialogFragment {

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View addDialog = LayoutInflater.from(getContext())
                .inflate(R.layout.d_add_counter, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(R.string.add_counter);
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = ((EditText) addDialog.findViewById(R.id.textinput_counter_name))
                    .getText()
                    .toString();
            if (!name.equals("")) {
                Repository.getInstance(getContext()).addCounter(name);
                Toast.makeText(getContext(), String.format("%s added", name), Toast.LENGTH_SHORT).show();
            } else Repository.getInstance(getContext()).addCounter("Untitled");
        });
        builder.setView(addDialog);
        return builder.create();
    }
}
