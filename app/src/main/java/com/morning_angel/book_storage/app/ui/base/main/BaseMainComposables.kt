package com.morning_angel.book_storage.app.ui.base.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.morning_angel.book_storage.app.util.getBookPreviewImage
import com.morning_angel.book_storage.data.ui.entityes.BookUI
import com.morning_angel.book_storage.data.ui.entityes.NavItem
import java.io.File


@Composable
fun MainNavDrawer(
    navItems: List<NavItem>,
    drawerState: DrawerState,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        content = content,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Navigation panel", modifier = Modifier.padding(16.dp))
                Divider()
                val navItemModifier = Modifier.padding(horizontal = 5.dp, vertical = 4.dp)

                navItems.forEach { navItem ->
                    NavigationDrawerItem(
                        modifier = navItemModifier,
                        label = {
                            Row {
                                Icon(navItem.imageVector, null)
                                Text(text = navItem.title)
                            }
                        },
                        selected = navItem.selected,
                        onClick = { navItem.onClick.invoke() }
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SavedBooksList(
    allBookList: List<BookUI>,
    paddingValues: PaddingValues,
    onClick: (book: BookUI) -> Unit,
    onLongClick: (book : BookUI) -> Unit,
    onDeleteClick: (book: BookUI) -> Unit,
    onShareClick: (book: BookUI) -> Unit,
    density: Density
) {
    if (allBookList.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            allBookList.forEach { item ->
                item {
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 6.dp, vertical = 4.dp)
                            .combinedClickable(
                                onClick = { onClick.invoke(item) },
                                onLongClick = { onLongClick.invoke(item) }
                            )
                    ) {
                        Row {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f),
                                painter = getBookPreviewImage(File(item.filePath)),
                                contentDescription = "Book cover"
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                            ) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .align(Alignment.TopStart),
                                    fontSize = 18.sp,
                                    text = item.title
                                )
                            }
                        }
                        println("== wdkajgduh AnimatedVisibility visible == ${item}")
                        AnimatedVisibility(
                            visible = item.expanded,
                            enter = slideInVertically { with(density) { -40.dp.roundToPx() } } +
                                    expandVertically(expandFrom = Alignment.Top) +
                                    fadeIn(initialAlpha = 0.3f),
                            exit = slideOutVertically() + shrinkVertically() + fadeOut())
                        {
                            Row {
                                val btnModifier = Modifier.weight(1f).padding(horizontal = 5.dp)
                                Button(
                                    modifier = btnModifier,
                                    onClick = { onDeleteClick.invoke(item) },
                                ) { Text(text = "Delete") }
                                Button(
                                    modifier = btnModifier,
                                    onClick = { onShareClick.invoke(item) },
                                ) { Text(text = "Share") }
                            }

                        }
                    }
                }
            }
        }
    } else Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "You have no added books"
        )
    }
}

@Composable
fun FloatingButton(
    buttonDescription: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = { onClick.invoke() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .height(90.dp)
                .padding(16.dp)
                .semantics {
                    contentDescription = buttonDescription//"Add book"
                },
        ) {
            Icon(
                imageVector = icon,
                modifier = Modifier.padding(6.dp),
                contentDescription = buttonDescription
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListTopBar(
    onNavButtonClick: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("Book List") },
                navigationIcon = {
                    IconButton(onClick = { onNavButtonClick.invoke() }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "Navigation")
                    }
                }
            )
        },
    ) { innerPadding ->
        content(innerPadding)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarTitle(
    title: String,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(title) },
            )
        },
    ) { innerPadding ->
        content(innerPadding)
    }
}

