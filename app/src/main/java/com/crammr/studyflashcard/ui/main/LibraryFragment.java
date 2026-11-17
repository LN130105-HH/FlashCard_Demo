package com.crammr.studyflashcard.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.crammr.studyflashcard.data.local.entity.FlashcardCollection;
import com.crammr.studyflashcard.data.repository.FlashcardRepository;
import com.crammr.studyflashcard.databinding.FragmentLibraryBinding;
import com.crammr.studyflashcard.ui.adapter.CollectionAdapter;
import com.crammr.studyflashcard.ui.library.AddEditCollectionActivity;
import com.crammr.studyflashcard.ui.library.CollectionDetailActivity;
import com.crammr.studyflashcard.ui.library.ManageCategoriesActivity;

import java.util.List;

public class LibraryFragment extends Fragment {

    private FragmentLibraryBinding binding;
    private FlashcardRepository repository;
    private CollectionAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLibraryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        repository = new FlashcardRepository(requireActivity().getApplication());

        setupViews();
        loadCollections("");
    }

    private void setupViews() {
        adapter = new CollectionAdapter(collection -> {
            Intent intent = new Intent(requireContext(), CollectionDetailActivity.class);
            intent.putExtra("collection_id", collection.getId());
            startActivity(intent);
        });

        binding.rvCollections.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvCollections.setAdapter(adapter);

        binding.fabAddCollection.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), AddEditCollectionActivity.class));
        });

        binding.btnCreateFirstCollection.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), AddEditCollectionActivity.class));
        });

        binding.btnManageCategories.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), ManageCategoriesActivity.class));
        });

        binding.etSearchCollections.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadCollections(s.toString().trim());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void loadCollections(String query) {
        if (query.isEmpty()) {
            repository.getAllCollections().observe(getViewLifecycleOwner(), this::updateList);
        } else {
            repository.searchCollections(query).observe(getViewLifecycleOwner(), this::updateList);
        }
    }

    private void updateList(List<FlashcardCollection> list) {
        if (list != null && !list.isEmpty()) {
            adapter.setCollections(list);
            binding.rvCollections.setVisibility(View.VISIBLE);
            binding.layoutEmptyLibrary.setVisibility(View.GONE);
        } else {
            binding.rvCollections.setVisibility(View.GONE);
            binding.layoutEmptyLibrary.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
