package ru.pakarpichev.myapplication.domain.model

data class EventsModel(
    val country_name: String,
    val league_name: String,
    val match_date: String,
    val match_status: String,
    val match_time: String,
    val match_hometeam_name: String,
    val match_hometeam_score: String,
    val match_awayteam_name: String,
    val match_awayteam_score: String,
    val match_stadium: String,
    val team_home_badge: String,
    val team_away_badge: String,
    val league_logo: String,
    val country_logo: String,
    val match_hometeam_system: String,
    val match_awayteam_system: String,
    val match_referee: String,
)
