package com.rondi.indonesianspecialfood.ui.screen.about

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.rondi.indonesianspecialfood.R
import com.rondi.indonesianspecialfood.ui.theme.IndonesianSpecialFoodTheme

@Composable
fun AboutScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = navigateBack,
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    tint = Color.Black,
                    contentDescription = stringResource(R.string.back),
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.about_me),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )

        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(24.dp)
                .align(alignment = Alignment.CenterHorizontally)

        ) {
            AsyncImage(
                model = "https://tifrp20a.my.id/13/archive/profil_rondi.jpg",
                contentDescription = "about_image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
                    .clip(CircleShape)
            )

            Text(
                text = stringResource(R.string.my_name),
                color = Color.Black,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                modifier = modifier.padding(top = 16.dp)

            )
            Text(
                text = stringResource(R.string.my_email),
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    IndonesianSpecialFoodTheme {
        AboutScreen(
            navigateBack = {},
        )
    }
}