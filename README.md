# PDF Scanner App

## Overview

The PDF Scanner App is an Android application designed to create PDF files by scanning documents using the device camera or selecting photos from the gallery. It leverages Jetpack Compose for the UI, ViewModel for managing UI-related data, and Google's ML Kit for image processing.

## Features

- Scan documents using the device camera.
- Import images from the gallery.
- Process and enhance scanned images using ML Kit.
- Create multi-page PDF documents.
- User-friendly interface built with Jetpack Compose.
- Efficient data management using ViewModel.

## Installation

To install and run the app, follow these steps:

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/yourusername/pdf-scanner-app.git
   cd pdf-scanner-app
   ```

2. **Open the Project in Android Studio:**
   - Ensure you have the latest version of Android Studio installed.
   - Open the `pdf-scanner-app` project in Android Studio.

3. **Build the Project:**
   - Sync the project with Gradle files.
   - Build the project by clicking on `Build > Make Project`.

4. **Run the App:**
   - Connect your Android device or start an emulator.
   - Run the app by clicking on the `Run` button or selecting `Run > Run 'app'`.

## Usage

### Scanning Documents

1. Open the app and tap on the "Scan Document" button.
2. Align the document within the camera frame and capture the image.
3. Review and enhance the scanned image using available options.
4. Save the scanned image as a PDF or add more pages.

### Importing from Gallery

1. Open the app and tap on the "Import from Gallery" button.
2. Select the images you want to include in the PDF from your gallery.
3. Review and enhance the selected images.
4. Save the images as a PDF or add more pages.

## Technical Details

### Jetpack Compose

Jetpack Compose is used to create a modern and responsive UI. The main components include:

- **Composable Functions:** For defining UI elements.
- **State Management:** Using `State` and `MutableState` for managing UI states.

### ViewModel

ViewModel is used to manage UI-related data in a lifecycle-conscious way. It ensures that data survives configuration changes such as screen rotations.

- **Data Handling:** All data operations and logic are handled in the ViewModel.
- **LiveData:** To observe and react to data changes in the UI.

### ML Kit

ML Kit is used for image processing, providing functionalities like:

- **Image Enhancement:** Automatic adjustments to improve the quality of scanned images.
- **Edge Detection:** Identifying document edges for precise cropping.

### PDF Creation

The app uses a third-party library for creating PDF documents from images. The process includes:

- Converting images to PDF format.
- Combining multiple images into a single PDF file.

## Dependencies

The app relies on several libraries and tools:

- **Jetpack Compose:** For building the UI.
- **ViewModel and LiveData:** For managing UI-related data.
- **ML Kit:** For image processing and enhancement.


## Contributing

Contributions are welcome! If you have any suggestions, bug reports, or feature requests, please create an issue or submit a pull request.

## License

This project is licensed under the MIT License. See the `LICENSE` file for more details.

## Contact

For any inquiries or support, please contact us at [support@pdfscannerapp.com](mailto:support@pdfscannerapp.com).

---

Thank you for using the PDF Scanner App! We hope it makes your document management easier and more efficient.