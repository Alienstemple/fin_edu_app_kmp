package ru.lct2026.finedu.feature.learn.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.lct2026.finedu.productcore.domain.repository.ContentRepository
import ru.lct2026.finedu.productcore.ui.viewmodel.StatefulViewModel

internal class GlossaryViewModel(private val contentRepository: ContentRepository) :
    StatefulViewModel<GlossaryUiState>(GlossaryUiState.Loading) {

    init {
        viewModelScope.launch {
            setState(GlossaryUiState.Content(contentRepository.content().glossary))
        }
    }
}
