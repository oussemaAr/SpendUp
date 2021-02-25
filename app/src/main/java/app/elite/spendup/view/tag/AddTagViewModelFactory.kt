package app.elite.spendup.view.tag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.elite.spendup.repository.TagRepository

class AddTagViewModelFactory(private val tagRepository: TagRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.constructors.first().newInstance(tagRepository) as T
    }

}