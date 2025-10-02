# Android SQL Database Note App

A multiplatform note-taking application built with Android (Kotlin/Java), designed as part of the larger **Haqote-Pad** project - a comprehensive note-taking solution for Linux, Windows, macOS, Android, and iOS.

## Features

- **SQLite Database Integration**: Robust local storage for notes using SQL database
- **Note Management**: Create, read, update, and delete notes with efficient database operations
- **Markdown Support**: Notes are stored and managed as Markdown files
- **Clean Architecture**: Well-structured codebase following Android development best practices

## Project Structure

```
app/
├── src/main/java/com/exersice/sqldatabase/
│   ├── MainActivity.kt          # Main application activity
│   ├── DatabaseHelper.kt        # SQLite database management
│   ├── Note.kt                 # Note data model
│   └── ui/theme/               # Material Design theme components
├── src/main/res/               # Android resources (layouts, strings, etc.)
└── src/test/                   # Unit tests
```

## Key Components

- **DatabaseHelper**: Manages SQLite database operations for note storage and retrieval
- **Note**: Data model representing note entities
- **MainActivity**: Main user interface and app logic
- **Material Design UI**: Modern Android UI following Material Design principles

## Technology Stack

- **Language**: Kotlin
- **Database**: SQLite
- **UI Framework**: Android Jetpack Compose / XML layouts
- **Architecture**: MVVM pattern
- **Build System**: Gradle with Kotlin DSL

## Part of Haqote-Pad Ecosystem

This Android app is part of the larger Haqote-Pad project, which aims to provide:

- Advanced tagging and categorization
- AI-powered search capabilities
- Speech-to-text and text-to-speech functionality
- Multi-model AI assistant integration
- Excel-like table calculations
- Multimedia file support
- Fully customizable UI inspired by Obsidian
- Cross-platform compatibility (Linux, Windows, macOS, Android, iOS)

## Getting Started

1. Clone this repository
2. Open in Android Studio
3. Build and run the project
4. Start creating and managing your notes!

## Contributing

This project is part of an ongoing development effort for a comprehensive note-taking solution. Contributions and suggestions are welcome!
