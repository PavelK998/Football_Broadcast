package ru.pakarpichev.myapplication.domain.repository
import retrofit2.http.GET
import retrofit2.http.Query
import ru.pakarpichev.myapplication.domain.model.EventsModel
interface RetrofitRepository {
    @GET("https://apiv3.apifootball.com/")
    suspend fun getJsonRequest(
        @Query("action") action: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("league_id") id: String,
        @Query("APIkey") apiKey: String,
    ): List<EventsModel>
}