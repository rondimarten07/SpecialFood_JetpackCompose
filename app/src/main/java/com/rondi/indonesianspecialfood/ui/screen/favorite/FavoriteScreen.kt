package com.rondi.indonesianspecialfood.ui.screen.favorite

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rondi.indonesianspecialfood.model.Foods
import com.rondi.indonesianspecialfood.ui.common.UiState
import com.rondi.indonesianspecialfood.ui.component.EmptyContent
import com.rondi.indonesianspecialfood.ui.screen.home.ListFoods
import com.rondi.indonesianspecialfood.R
import com.rondi.indonesianspecialfood.ui.theme.IndonesianSpecialFoodTheme

@Composable
fun FavoriteScreen(
    moveToAboutPage: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoriteFoods()
            }
            is UiState.Success -> {
                FavoriteContent(
                    listFoods = uiState.data,
                    moveToAboutPage = moveToAboutPage,
                    navigateToDetail = navigateToDetail,
                    onFavoriteIconClicked = { id, newState ->
                        viewModel.updateFoods(id, newState)
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavoriteContent(
    listFoods: List<Foods>,
    moveToAboutPage: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopBarContent(moveToAboutPage = moveToAboutPage)
        },
        modifier = modifier
    ) {
        if (listFoods.isNotEmpty()) {
            ListFoods(
                listFoods = listFoods,
                onFavoriteIconClicked = onFavoriteIconClicked,
                contentPaddingTop = 16.dp,
                navigateToDetail = navigateToDetail
            )
        } else {
            EmptyContent(
                contentText = stringResource(R.string.empty_favorite)
            )
        }
    }
}

@Composable
fun TopBarContent(
    moveToAboutPage: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.favorite))
        },
        elevation = 0.dp,
        actions = {
            IconButton(
                onClick = moveToAboutPage,
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "about_page"
                )
            }
        },
        backgroundColor = MaterialTheme.colors.surface,
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun FavoriteContentPreview() {
    IndonesianSpecialFoodTheme {
        FavoriteContent(
            listFoods = emptyList(),
            moveToAboutPage = {},
            navigateToDetail = {},
            onFavoriteIconClicked = { _, _ ->  }
        )
    }
}
