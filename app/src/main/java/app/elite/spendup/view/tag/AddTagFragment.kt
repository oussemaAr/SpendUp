package app.elite.spendup.view.tag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import app.elite.spendup.data.local.AppDatabase
import app.elite.spendup.data.local.entities.TagEntity
import app.elite.spendup.databinding.AddTagLayoutBinding
import app.elite.spendup.repository.TagRepository
import app.elite.spendup.utils.stringText
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class AddTagFragment : BottomSheetDialogFragment() {

    private lateinit var binding: AddTagLayoutBinding
    private val viewModel: AddTagViewModel by viewModels {
        AddTagViewModelFactory(TagRepository(AppDatabase.getInstance(requireContext())))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddTagLayoutBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(false)

        binding.addTag.setOnClickListener {
            viewModel.insertTag(
                TagEntity(
                    name = binding.name.stringText().capitalize(Locale.getDefault()),
                    thumbnail = "ic_others"
                )
            ).run {
                findNavController().popBackStack()
            }
        }
    }

}