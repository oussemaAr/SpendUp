package app.elite.spendup.view.tag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.elite.spendup.data.local.entities.TagEntity
import app.elite.spendup.repository.TagRepository
import kotlinx.coroutines.launch

class AddTagViewModel(private val tagRepository: TagRepository) : ViewModel() {

    fun insertTag(tagEntity: TagEntity) = viewModelScope.launch {
        tagRepository.addTag(tagEntity)
    }
}