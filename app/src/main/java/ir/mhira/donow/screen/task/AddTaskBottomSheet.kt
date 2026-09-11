package ir.mhira.donow.screen.task

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ir.mhira.donow.R
import ir.mhira.donow.components.DatePickerModal
import ir.mhira.donow.model.TaskModel
import ir.mhira.donow.data.database.DBManager
import ir.mhira.donow.utility.getTomorrowLang
import ir.mhira.donow.utility.longToDateLongForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskBottomSheet(taskId: Int = 0, onDismiss: () -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    var taskTitle by remember { mutableStateOf("") }
    var taskDate by remember { mutableLongStateOf(getTomorrowLang()) }
    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(taskId) {
        if (taskId != 0) {
            val task = DBManager.getTaskById(taskId)

            taskTitle = task.title
            taskDate = task.date
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                value = taskTitle,
                onValueChange = { taskTitle = it },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge,
                shape = RoundedCornerShape(16.dp),
                label = { Text(stringResource(R.string.add_task_title_field)) },
                colors = OutlinedTextFieldDefaults.colors().copy(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                    unfocusedTextColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = MaterialTheme.colorScheme.outline,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    disabledLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                )
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(16.dp)
                    ), colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                onClick = {
                    showDatePicker = true
                }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.padding(10.dp),
                        painter = painterResource(R.drawable.calendar_blue_64),
                        contentDescription = "task date"
                    )
                    Text(
                        longToDateLongForm(taskDate),
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }

            Row(modifier = Modifier.padding(16.dp)) {
                if (taskId != 0) {
                    Button(
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors().copy(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = Color.White
                        ),
                        onClick = {
                            DBManager.deleteTask(DBManager.getTaskById(taskId))
                            onDismiss()
                        }) {
                        Text(
                            stringResource(R.string.delete_task_btn_text),
                            modifier = Modifier.padding(4.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                }

                Button(
                    modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors().copy(
                        containerColor = MaterialTheme.colorScheme.onSurface,
                        contentColor = MaterialTheme.colorScheme.surface
                    ), onClick = {
                        val task = TaskModel(
                            taskId,
                            taskTitle,
                            if (taskId != 0) DBManager.getTaskById(taskId).isFinished else false,
                            taskDate
                        )
                        if (taskId == 0) {
                            DBManager.addTask(task)
                        } else {
                            DBManager.updateTask(task)
                        }

                        onDismiss()
                    }) {
                    Text(
                        if (taskId == 0) stringResource(R.string.add_task_btn_text) else stringResource(
                            R.string.edit_task_btn_text
                        ),
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }

    if (showDatePicker) {
        DatePickerModal(
            initialDate = taskDate,
            onDateSelected = { date ->
                taskDate = date!!
                showDatePicker = false
            },
            onDismiss = {
                showDatePicker = false
            }
        )
    }
}

