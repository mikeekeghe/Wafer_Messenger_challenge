echo "# WAFER MESSENGER CHALLENGE
--------------------------------------------------
an Android application that:

1. Fetches json data through endpoint https://restcountries.eu/rest/v2/all
2. Display json data as listview with following elements parsed from json

a. “name”  ->  this is Country Name
b  “currencies” -> ”name" -> this is currency name, if more than 1 currency is present, first currency name is to be displayed
c. “languages” -> “name”  -> this is language name, if more than 1 language is present first language is to be used

3. On the displayed data, add a custom "Swipe to delete" with the following characteristics:
• Row background color -  white

the solution
---------------
the solution was created using Android studio and comprises of six activities

1. Main Activity
-------------------
This is the first activity that loads for the user, it displays the formated result of the consumed JSON web service

2. CustomAdapter
-----------------------
serves as a link between the country_item and the main activity

3. NetworkUtils
---------------------
connection utilitie class for consuming json api

4. RecycleItemTouchHelper
------------------------------------
serve as a helper class for the main activity to provide necessary methods for swipe and touch activities

5. CountryListAdapter
-----------------------------
serves as a link between the country_item and the swipe functionalities in main activity

6. Item
----------
Entity class for each item" >> README.md
