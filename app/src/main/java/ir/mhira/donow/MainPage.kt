package ir.mhira.donow

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ir.mhira.donow.screen.task.AddTaskBottomSheet
import ir.mhira.donow.screen.task.TaskItem
import ir.mhira.donow.screen.task.TasksGroupItem
import ir.mhira.donow.model.TasksGroupModel
import ir.mhira.donow.screen.task.TasksViewModel
import ir.mhira.donow.utility.getFinishedTask
import ir.mhira.donow.utility.getOpenTask
import ir.mhira.donow.utility.getToDayTask


@SuppressLint("AutoboxingStateCreation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(viewModel: TasksViewModel, darkTheme: Boolean, onThemeChange: () -> Unit) {

    var showAddBottomSheet by remember { mutableStateOf(false) }
    var clickedTaskId by remember { mutableStateOf(0) }

    val tasksList by viewModel.tasks.observeAsState(initial = emptyList())

    var selectedGroup by remember { mutableIntStateOf(0) }
    /*
     0 -> today tasks
     1 -> all tasks
     2 -> finished tasks
     */
    val groupList = ArrayList<TasksGroupModel>()
    groupList.add(
        TasksGroupModel(
            0,
            stringResource(R.string.today_group_title),
            R.drawable.today_64,
            getToDayTask(tasksList).size
        )
    )
    groupList.add(
        TasksGroupModel(
            1,
            stringResource(R.string.all_group_title),
            R.drawable.all_blue_64,
            getOpenTask(tasksList).size
        )
    )
    groupList.add(
        TasksGroupModel(
            2,
            stringResource(R.string.done_group_title),
            R.drawable.done_green_64,
            getFinishedTask(tasksList).size
        )
    )

    val tasks = when (selectedGroup) {
        0 -> getToDayTask(tasksList)
        1 -> tasksList
        else -> getFinishedTask(
            tasksList
        )
    }

    Scaffold(Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.primary,
                actionIconContentColor = MaterialTheme.colorScheme.primary,
                navigationIconContentColor = MaterialTheme.colorScheme.primary
            ), title = {
                Column(modifier = Modifier.padding(horizontal = 4.dp)) {
                    Text(
                        stringResource(R.string.app_name),
                        modifier = Modifier.padding(top = 4.dp),
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Text(
                        stringResource(R.string.emo_message),
                        modifier = Modifier.padding(top = 4.dp),
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }, actions = {
                IconButton(
                    modifier = Modifier.padding(horizontal = 8.dp), onClick = onThemeChange
                ) {
                    Icon(
                        painter = painterResource(
                            if (darkTheme) R.drawable.dark_mode else R.drawable.light_mode
                        ), contentDescription = "app theme"
                    )
                }
            })

    }, floatingActionButton = {
        FloatingActionButton(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary,
            shape = CircleShape,
            onClick = {
                clickedTaskId = 0
                showAddBottomSheet = true
            }) {
            Icon(Icons.Default.Add, contentDescription = "add web site")
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
        ) {
            HorizontalDivider(
                Modifier.padding(horizontal = 12.dp, vertical = 0.dp),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.outline
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(groupList.size) { item ->
                    TasksGroupItem(
                        groupList[item].id == selectedGroup, groupList[item]
                    ) { id ->
                        selectedGroup = id
                    }
                }
            }

            LazyColumn {
                if (selectedGroup != 2) {
                    item {
                        // Show Unfinished Tasks

                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                stringResource(R.string.open_tasks),
                                modifier = Modifier.padding(bottom = 8.dp),
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            HorizontalDivider(
                                modifier = Modifier.padding(start = 4.dp),
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }

                    items(getOpenTask(tasks)) { task ->
                        TaskItem(
                            task,
                            onTaskClick = {
                                clickedTaskId = it
                                showAddBottomSheet = true
                            }
                        )
                    }

                    item {
                        Spacer(Modifier.height(24.dp))
                    }
                }

                if (selectedGroup != 1) {


                    item {
                        // show Finished tasks
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                stringResource(R.string.finished_tasks),
                                modifier = Modifier.padding(bottom = 8.dp),
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            HorizontalDivider(
                                modifier = Modifier.padding(start = 4.dp),
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }

                    items(getFinishedTask(tasks)) { task ->
                        TaskItem(
                            task,
                            onTaskClick = {
                                clickedTaskId = it
                                showAddBottomSheet = true
                            }
                        )
                    }

                }
            }
        }
    }

    if (showAddBottomSheet) {
        AddTaskBottomSheet (taskId = clickedTaskId) {
            showAddBottomSheet = false
        }
    }

}