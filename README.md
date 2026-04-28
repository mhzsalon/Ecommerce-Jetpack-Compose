# Ecomm

`Ecomm` is a modern Android e-commerce sample app built with Kotlin and Jetpack Compose. It supports guest product browsing, Firebase-based authentication, local cart storage, and profile management in a clean feature-based architecture.

## Features

- Browse products from a remote API
- View detailed product information
- Guest-friendly navigation
- Login and registration with Firebase Authentication
- Protected cart and profile flows
- Add to cart with local persistence using Room
- Edit profile data stored in Firebase Firestore
- Session persistence with DataStore

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- Hilt
- Room
- DataStore
- Retrofit
- OkHttp
- Firebase Authentication
- Firebase Firestore
- Coroutines
- Arrow

## Architecture

The project follows a feature-based structure with separate layers for:

- `data`
- `domain`
- `presentation`
- `di`

Core shared code lives under `core`, while product areas such as `auth`, `products`, `cart`, and `profile` are grouped under `features`.

## Project Structure

```text
app/src/main/java/com/example/ecomm
|
+-- core
|   +-- components
|   +-- di
|   +-- navigation
|   +-- localDB
|   +-- network
|   +-- theme
|
+-- features
|   +-- auth
|   +-- cart
|   +-- products
|   +-- profile
|   +-- shared
|
+-- MainActivity.kt
+-- EcommApp.kt
```

## Authentication Flow

This app is designed as a guest-first shopping experience:

- Users can open the app and browse products without logging in
- Login is required for:
  - adding items to cart
  - opening the cart
  - opening the profile

## Data Sources

- Product catalog: [DummyJSON](https://dummyjson.com/)
- Authentication: Firebase Auth
- Profile data: Firebase Firestore
- Cart data: local Room database

## Getting Started

### Prerequisites

- Android Studio
- JDK 11
- Android SDK 31+
- A Firebase project

### Setup

1. Clone the repository.

```bash
git clone https://github.com/mhzsalon/Ecommerce-Jetpack-Compose.git
cd ecomm
```

2. Open the project in Android Studio.

3. Add your Firebase configuration file:

- place `google-services.json` inside the `app/` directory

4. Sync Gradle.

5. Run the app on an emulator or device.

## Build

Debug unit tests:

```bash
./gradlew testDebugUnitTest
```

On Windows:

```powershell
.\gradlew.bat testDebugUnitTest
```

## Notes

- The app currently uses `dummyjson.com` as a sample product API.
- Cart persistence is local to the device.
- Firebase is required for authentication and profile storage.

## Future Improvements

- Checkout backend integration
- Order history
- Search and filtering enhancements
- Better test coverage
- Improved error handling and offline behavior
- CI/CD pipeline setup

## License

This project is available for learning and personal use. Add a license file if you plan to publish it publicly for reuse.
