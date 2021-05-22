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

import java.util.Objects;

public class EditDialog extends AppCompatDialogFragment {

    public static final String ARG_ID = "ARG_ID";

    public static EditDialog create(long counterId) {
        Bundle args = new Bundle();
        args.putLong(ARG_ID, counterId);
        EditDialog dialog = new EditDialog();
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View editDialog = LayoutInflater.from(requireContext())
                .inflate(R.layout.d_edit_counter, null, false);
        EditText editText = editDialog.findViewById(R.id.textinput_edit_counter_name);
        Repository repository = Repository.getInstance(requireContext());
        long counterId = getArguments().getLong(ARG_ID);
        String currentName = repository.getCounter(counterId).name;
        editText.setText(currentName);
        editText.setSelection(currentName.length());

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        builder.setTitle(getString(R.string.edit_counter));
        builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss());
        builder.setPositiveButton(getString(R.string.save), (dialog, which) -> {
            String inputName = editText.getText().toString();
            if (inputName.equals("")) {
                inputName = getString(R.string.default_counter_name);
            }
            repository.setName(counterId, inputName);
            Toast.makeText(getContext(), String.format("%s saved", inputName), Toast.LENGTH_SHORT).show();
        });
        builder.setView(editDialog);
        Dialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;
    }
}
