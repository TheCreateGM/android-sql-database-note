package com.exersice.sqldatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exersice.sqldatabase.ui.theme.SqlDatabaseTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SqlDatabaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NoteScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen() {
    val context = LocalContext.current
    val db = remember { DatabaseHelper(context) }
    var notes by remember { mutableStateOf(listOf<Note>()) }
    val coroutineScope = rememberCoroutineScope()

    // Function to refresh the notes list from the database
    val refreshNotes = {
        coroutineScope.launch(Dispatchers.IO) {
            val updatedNotes = db.getAllNotes()
            withContext(Dispatchers.Main) {
                notes = updatedNotes
            }
        }
    }

    // LaunchedEffect runs once when the composable enters the screen
    LaunchedEffect(Unit) {
        refreshNotes()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SQLite Notes") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val noteCount = notes.size + 1
                val newNote = Note(title = "Note #$noteCount", content = "This is a new note.")
                coroutineScope.launch(Dispatchers.IO) {
                    db.addNote(newNote)
                    refreshNotes()
                }
            }) {
                Text("+", style = MaterialTheme.typography.headlineMedium)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(notes, key = { it.id }) { note ->
                NoteItem(note = note, onDelete = {
                    coroutineScope.launch(Dispatchers.IO) {
                        db.deleteNote(note.id)
                        refreshNotes()
                    }
                })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun NoteItem(note: Note, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = note.title, style = MaterialTheme.typography.titleLarge)
                Text(text = note.content, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = onDelete) {
                Text("Delete")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SqlDatabaseTheme {
        NoteScreen()
    }
}