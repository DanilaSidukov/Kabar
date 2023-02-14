package com.sidukov.kabar.ui.news

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.sidukov.kabar.R
import com.sidukov.kabar.data.database.EntityNews
import com.sidukov.kabar.ui.NewsApplication
import com.sidukov.kabar.ui.news.FragmentHome.Companion.tempList
import com.sidukov.kabar.ui.news.newscategory.ActivityArticleNews
import com.sidukov.kabar.ui.news.newscategory.NewsAdapter.Companion.difference
import com.sidukov.kabar.ui.news.newscategory.OnItemNewsClicked
import com.sidukov.kabar.ui.news.newscategory.TabRowItem
import kotlinx.coroutines.launch
import java.io.Serializable

@SuppressLint("ResourceType")
@OptIn(ExperimentalPagerApi::class)
@Composable
@Preview
fun NewsViewPager(list: List<EntityNews> = tempList) {

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val tabRowItem = listOf(
        TabRowItem(
            title = stringResource(id = R.string.all),
            screen = { NewsListItem(list = list, category = " ") }
        ),
        TabRowItem(
            title = stringResource(id = R.string.business),
            screen = { NewsListItem(list = list, category = "business") }
        ),
        TabRowItem(
            title = stringResource(id = R.string.entertainment),
            screen = { NewsListItem(list = list, category = "entertainment") }
        ),
        TabRowItem(
            title = stringResource(id = R.string.health_and_food),
            screen = { NewsListItem(list = list, category = "health") }
        ),
        TabRowItem(
            title = stringResource(id = R.string.politics),
            screen = { NewsListItem(list = list, category = "politics") }
        ),
        TabRowItem(
            title = stringResource(id = R.string.science),
            screen = { NewsListItem(list = list, category = "science") }
        ),
        TabRowItem(
            title = stringResource(id = R.string.sports),
            screen = { NewsListItem(list = list, category = "sports") }
        ), TabRowItem(
            title = stringResource(id = R.string.technology),
            screen = { NewsListItem(list = list, category = "technology") }
        )
    )

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(colorResource(id = R.color.white))
    ) {
        ScrollableTabRow(
            modifier = Modifier
                .background(colorResource(id = R.color.white)),
            selectedTabIndex = pagerState.currentPage,
            divider = { Divider() },
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                    color = colorResource(id = R.color.blue),
                )
            },
        ) {
            tabRowItem.forEachIndexed { index, item ->
                val isSelected = pagerState.currentPage == index
                Tab(
                    selected = pagerState.currentPage == index,
                    modifier = Modifier
                        .background(colorResource(id = R.color.white))
                        .border(0.dp, color = colorResource(id = R.color.white))
                        .height(35.dp),
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = item.title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontStyle = FontStyle(R.font.poppins_regular),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,
                            color = if (isSelected) {
                                colorResource(id = R.color.black_2)
                            } else {
                                colorResource(id = R.color.anthracite)
                            }
                        )
                    }
                )
            }
        }
        CompositionLocalProvider(
            LocalRippleTheme provides ClearRippleTheme
        ) {
        }
        HorizontalPager(
            count = tabRowItem.size,
            state = pagerState,
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Top
        ) {
            tabRowItem[pagerState.currentPage].screen()
        }
    }
}

object ClearRippleTheme : RippleTheme {

    @Composable
    override fun defaultColor() = Transparent

    @Composable
    override fun rippleAlpha() = RippleAlpha(
        draggedAlpha = 0.0f,
        focusedAlpha = 0.0f,
        hoveredAlpha = 0.0f,
        pressedAlpha = 0.0f,
    )
}

@Composable
fun NewsListItem(list: List<EntityNews>, category: String) {

    val newsList =
        if (category == "health") list.filter { it.category == "health" || it.category == "food" }
        else if (category == " ") list
        else list.filter { it.category == category }

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)) {
        items(newsList.size) { index ->
            ItemNews(
                item = newsList[index],
                onItemClicked = {
                    OpenArticleActivity(item = newsList[index])
                }
            )
        }
    }
}

fun OpenArticleActivity(item: EntityNews){
    val context = ActivityGeneral.generalContext
    context.startActivity(
        Intent(context, ActivityArticleNews::class.java).also {
            it.putExtra("item_news", item as Serializable)
        }
    )
}

@Composable
fun ItemNews(
    item: EntityNews = EntityNews("news",
        "all",
        "something",
        null,
        "it's me",
        null,
        323232121212),
    onItemClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(110.dp)
            .fillMaxWidth()
            .clickable(onClick = onItemClicked, enabled = true),
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = item.newsImage),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(corner = CornerSize(6.dp))),
            alignment = Center
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = item.category!!,
                fontSize = 14.sp,
                fontStyle = FontStyle(R.font.poppins_regular),
                color = colorResource(id = R.color.anthracite),
                fontWeight = FontWeight.Normal,
                maxLines = 1
            )
            Text(text = item.title,
                fontSize = 16.sp,
                fontStyle = FontStyle(R.font.poppins_regular),
                color = colorResource(id = R.color.black_2),
                maxLines = 2
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(0.dp, 0.dp, 20.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .width(120.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_pencil_news),
                        modifier = Modifier
                            .size(20.dp, 20.dp)
                            .clip(RoundedCornerShape(corner = CornerSize(6.dp))),
                        contentDescription = null
                    )
                    Text(text = item.author,
                        fontSize = 13.sp,
                        fontStyle = FontStyle(R.font.poppins_semibold),
                        color = colorResource(id = R.color.anthracite),
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .width(80.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp, 14.dp)
                    )
                    Text(text = item.date.difference()!!,
                        fontSize = 13.sp,
                        fontStyle = FontStyle(R.font.poppins_semibold),
                        color = colorResource(id = R.color.anthracite),
                        fontWeight = FontWeight.Normal,
                        maxLines = 1)
                }
            }
        }
    }
}
