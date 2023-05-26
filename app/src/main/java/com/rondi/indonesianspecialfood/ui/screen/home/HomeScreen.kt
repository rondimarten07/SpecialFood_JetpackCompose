package com.rondi.indonesianspecialfood.ui.screen.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.rondi.indonesianspecialfood.R
import com.rondi.indonesianspecialfood.model.Foods
import com.rondi.indonesianspecialfood.ui.common.UiState
import com.rondi.indonesianspecialfood.ui.component.EmptyContent
import com.rondi.indonesianspecialfood.ui.component.ItemFoods
import com.rondi.indonesianspecialfood.ui.component.SearchView
import com.rondi.indonesianspecialfood.ui.theme.IndonesianSpecialFoodTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    moveToAboutPage: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val query by viewModel.query
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.search(query)
            }
            is UiState.Success -> {
                HomeContent(
                    query = query,
                    onQueryChange = viewModel::search,
                    listFoods = uiState.data,
                    onFavoriteIconClicked = { id, newState ->
                        viewModel.updateFoods(id, newState)
                    },
                    navigateToDetail = navigateToDetail,
                    moveToAboutPage = moveToAboutPage
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    query: String,
    onQueryChange: (String) -> Unit,
    listFoods: List<Foods>,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    navigateToDetail: (Int) -> Unit,
    moveToAboutPage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            IconButton(
                onClick = moveToAboutPage,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.Magenta)
                    .shadow(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "about_page",
                    tint = Color.White

                )
            }

            SearchView(
                query = query,
                onQueryChange = onQueryChange
            )
        }
        if (listFoods.isNotEmpty()) {
            ListFoods(
                listFoods = listFoods,
                onFavoriteIconClicked = onFavoriteIconClicked,
                navigateToDetail = navigateToDetail
            )
        } else {
            EmptyContent(
                contentText = stringResource(R.string.empty_data),
                modifier = modifier
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListFoods(
    listFoods: List<Foods>,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPaddingTop: Dp = 0.dp,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
            top = contentPaddingTop
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(listFoods, key = { it.id }) { item ->
            ItemFoods(
                id = item.id,
                photoUrl = item.photoUrl,
                title = item.name,
                comeFrom = item.comeFrom,
                rating = item.rating,
                isFavorite = item.isFavorite,
                onFavoriteIconClicked = onFavoriteIconClicked,
                modifier = Modifier
                    .animateItemPlacement(tween(durationMillis = 200))
                    .clickable { navigateToDetail(item.id) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    IndonesianSpecialFoodTheme {
        HomeContent(
            query = "",
            onQueryChange = {},
            listFoods = emptyList(),
            onFavoriteIconClicked = { _, _ -> },
            navigateToDetail = {},
            moveToAboutPage = {}
        )
    }
}