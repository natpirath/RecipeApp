# Recipe App Read-Me File

Welcome to the Recipe App! This app allows you to search for meals based on different areas such as Canadian, Mexican, Chinese, and more. You can view meal details, save your favorite meals, and access them later. This read-me file provides an overview of the app's features and instructions on how to use them effectively.

# Features

1. Search for Meals by Area: You can search for meals based on different areas like Canadian, Mexican, Chinese, etc. The app utilizes The Meal DB API to retrieve a list of meals that match the specified area.

2. View Meal List: After performing a search, the app displays a list of meal titles that match the search area. You can scroll through the list to find meals that interest you.

3. Meal Details: When you select a meal from the list, you will be taken to a details page. The details page provides information about the selected meal, including the title, image, and URL. Clicking on the URL will open a web page showing the photo of the meal.

4. Save Favorite Meals: You have the option to save a meal into a list of favorites. The saved meals are stored in a local database for easy access. To save a meal, navigate to the details page and click the "Save as Favorite" button.

5. View Favorite Meals: The app provides a "Favorites" button that allows you to view a list of saved meals. Selecting a meal title from the favorites list will display the saved details of that meal, including the title, image, and URL.

6. Remove from Favorites: In the saved details page, you will find a delete button. Clicking on this button will remove the meal from the list of saved meals as well as from the saved meals database.

7. Persistent Search History: The app utilizes SharedPreferences to save the last meal that was searched. The next time you launch the application, the previously searched meal will be displayed, making it easy to resume your recipe exploration.

# Getting Started

To get started with the Recipe App, follow these steps:

1. Make sure you have an active internet connection as the app requires internet access to retrieve data from The Meal DB API.
2. Launch the app on your device.
3. On the main screen, you will find a search bar. Enter the area you want to search for, such as "Canadian," "Mexican," or "Chinese."
4. Press the search button or hit enter to perform the search. The app will retrieve a list of meals that match the specified area.
5. Scroll through the list to find meals that interest you. Click on a meal to view its details, including the title, image, and URL.
6. To save a meal as a favorite, navigate to the details page and click the "Save as Favorite" button.
7. To access your saved favorite meals, click on the "Favorites" button on the main screen. You will see a list of saved meals.
8. Selecting a meal title from the favorites list will display the saved details of that meal.
9. To remove a meal from your favorites list, go to the saved details page and click the delete button.
10. The app will automatically save the last searched meal using SharedPreferences. The next time you launch the application, the previously searched meal will be displayed.

# API Documentation

The Recipe App utilizes The Meal DB API to retrieve meal data. You can find the API documentation [here](https://www.themealdb.com/api.php). The API provides a developer test key '1' for accessing it. The format for accessing the API is `www.themealdb.com/api/json/v1/1/filter.php?a=Canadian`.


# Contributors to the project:

AbdulMajeed Chowdhury
Caner Altun
Hamza Khan
Natalia Mellilo Pirath
