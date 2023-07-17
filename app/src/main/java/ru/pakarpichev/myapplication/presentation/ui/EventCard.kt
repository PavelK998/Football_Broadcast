package ru.pakarpichev.myapplication.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import ru.pakarpichev.myapplication.domain.model.EventsModel
import ru.pakarpichev.myapplication.presentation.navigation.Screens
import ru.pakarpichev.myapplication.R

@Composable
fun EventCard(model: EventsModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), elevation = 10.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 7.dp, bottom = 7.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 7.dp, end = 7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .height(35.dp)
                        .width(35.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        modifier = Modifier.padding(start = 3.dp),
                        model = model.league_logo,
                        contentDescription = ""
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 3.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    text = model.league_name
                )
                Spacer(modifier = Modifier.weight(1f, true))
                Text(
                    modifier = Modifier.padding(end = 3.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    text = model.country_name
                )
                Box(
                    modifier = Modifier
                        .height(35.dp)
                        .width(35.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        modifier = Modifier.padding(end = 3.dp),
                        model = model.country_logo,
                        contentDescription = ""
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 19.dp, end = 19.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    fontWeight = FontWeight.Bold, fontSize = 16.sp, text = model.match_hometeam_name
                )
                Text(
                    fontWeight = FontWeight.Bold, fontSize = 16.sp, text = model.match_awayteam_name
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    modifier = Modifier.padding(top = 12.dp),
                    model = model.team_home_badge,
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier.padding(start = 14.dp, end = 14.dp),
                    fontSize = 25.sp,
                    text = "${model.match_hometeam_score} - ${model.match_awayteam_score}"
                )
                AsyncImage(model = model.team_away_badge, contentDescription = "")
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                modifier = Modifier.padding(start = 10.dp),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.stadium) + " ${model.match_stadium}\n" + stringResource(
                    id = R.string.referee
                ) + " ${model.match_referee}"
            )
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 14.dp, end = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${model.match_time}  ${model.match_date}")
                Text(text = stringResource(id = R.string.status) + " ${model.match_status}")
            }
        }
    }
}