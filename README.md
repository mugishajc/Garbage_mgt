# Garbage Management App

An Android app that coordinates waste collection between residents, collection drivers and an administrator.

## Features

- **Residents** register, report full bins and file complaints, and follow up on them.
- **Drivers** have their own profile, see the bins and complaints assigned to them, and update their status.
- **Admin** manages users and drivers, registers bins, and reviews bin and complaint reports, with PDF export of reports.
- Login, registration and password reset.

## Stack

- Android (Java), Material components, ViewPager with fragments
- Firebase Authentication, Realtime Database, Firestore and Storage
- FirebaseUI for list adapters
- iText for PDF generation, Android PDF Viewer for in-app viewing

## Project layout

```
GarbageMan-master/app/src/main/java/com/example/garbagemanagement/
├── Activities/   login, registration, admin, driver and user screens
├── Adapters/     RecyclerView adapters for bins, drivers, users and complaints
└── Fragments/    dashboard tabs (main, drivers, complaints, profile, settings)
```

## Running

Open `GarbageMan-master/` in Android Studio, connect it to your own Firebase project (replace `app/google-services.json`) and run it on a device or emulator (minSdk 21).
