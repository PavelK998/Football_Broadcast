package ru.pakarpichev.myapplication.presentation.useCase

import ru.pakarpichev.myapplication.domain.repository.RetrofitRepository
import javax.inject.Inject

class MainUseCase @Inject constructor(private val repository: RetrofitRepository) {
    suspend fun getData(
        action: String, from: String, to: String, id:String, key: String
    ) = repository.getJsonRequest(action, from, to, id, key)
}