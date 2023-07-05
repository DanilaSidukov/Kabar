package com.sidukov.kabar.ui.news

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
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
import com.sidukov.kabar.data.theme.KabarTheme
import com.sidukov.kabar.ui.news.newscategory.NewsAdapter.Companion.difference
import kotlinx.coroutines.launch

class NewsItem(
    val name: String,
    val list: List<EntityNews>,
)

@SuppressLint("ResourceType")
@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun NewsViewPager(
    list: List<EntityNews>,
    isDataLoaded: Boolean,
    onItemClicked: (EntityNews) -> Unit,
) {

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    println("all list = $list")

    val tabRowItem = listOf(
        NewsItem(
            stringResource(id = R.string.all), list
        ),
        NewsItem(
            stringResource(id = R.string.business), list.filter { it.category == "business" }
        ),
        NewsItem(
            stringResource(id = R.string.entertainment), list.filter { it.category == "entertainment" }
        ),
        NewsItem(
            stringResource(id = R.string.health_and_food), list.filter { it.category == "health" || it.category == "food" }
        ),
        NewsItem(
            stringResource(id = R.string.politics), list.filter { it.category == "politics" }
        ),
        NewsItem(
            stringResource(id = R.string.science), list.filter { it.category == "science" }
        ),
        NewsItem(
            stringResource(id = R.string.sports), list.filter { it.category == "sports" }
        ),
        NewsItem(
            stringResource(id = R.string.technology), list.filter { it.category == "technology" }
        )
    )

    CompositionLocalProvider(
        LocalOverscrollConfiguration.provides(null)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(KabarTheme.colors.white)
        ) {
            ScrollableTabRow(
                modifier = Modifier
                    .background(KabarTheme.colors.white),
                selectedTabIndex = pagerState.currentPage,
                divider = { Divider() },
                edgePadding = 0.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                        color = KabarTheme.colors.blue,
                    )
                },
            ) {
                tabRowItem.forEachIndexed { index, item ->
                    val isSelected = pagerState.currentPage == index
                    Tab(
                        selected = pagerState.currentPage == index,
                        modifier = Modifier
                            .background(KabarTheme.colors.white)
                            .border(0.dp, color = KabarTheme.colors.white)
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
                                text = item.name,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontStyle = FontStyle(R.font.poppins_regular),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.W400,
                                textAlign = TextAlign.Center,
                                color = if (isSelected) {
                                    KabarTheme.colors.black_2
                                } else {
                                    KabarTheme.colors.anthracite
                                }
                            )
                        }
                    )
                }
            }

            if (isDataLoaded) {
                HorizontalPager(
                    count = tabRowItem.size,
                    state = pagerState,
                    modifier = Modifier.fillMaxHeight()
                        .background(KabarTheme.colors.white),
                    verticalAlignment = Top
                ) {
                    NewsListItem(
                        list = tabRowItem[pagerState.currentPage].list,
                        onItemClicked = onItemClicked
                    )
                }
            } else LoadingCircleBar()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewsListItem(list: List<EntityNews>, onItemClicked: (EntityNews) -> Unit) {
    CompositionLocalProvider(
        LocalOverscrollConfiguration.provides(null)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
        ) {
            items(list.size) { index ->
                ItemNews(
                    item = list[index],
                    onItemClicked = {
                        onItemClicked(list[index])
                    }
                )
            }
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun ItemNews(
    item: EntityNews,
    onItemClicked: () -> Unit,
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
                color = KabarTheme.colors.anthracite,
                fontWeight = FontWeight.Normal,
                maxLines = 1
            )
            Text(text = item.title,
                fontSize = 16.sp,
                fontStyle = FontStyle(R.font.poppins_regular),
                color = KabarTheme.colors.black_2,
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
                        color = KabarTheme.colors.anthracite,
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
                        color = KabarTheme.colors.anthracite,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1)
                }
            }
        }
    }
}

@SuppressLint("ResourceType")
@Preview
@Composable
fun LoadingCircleBar() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.71f)
            .background(KabarTheme.colors.white),

        ) {
        Spacer(modifier = Modifier.height(30.dp))
        CircularProgressIndicator(
            color = KabarTheme.colors.blue,
            modifier = Modifier.size(50.dp)
        )
    }
}


