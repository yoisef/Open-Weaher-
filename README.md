# Open-Weaher-
This is android  impemention of   https://openweathermap.org/api  api 
-------------------------------------------------------------------
app scenario :- user open app then type city in top bar text field  which he looking for it's daily forecast hit the api to get days forecast
1-if api run correctly show city data and saved city in local database 
2-else look for the city in local database if exsist show old data with not accurate data notify
3-else if not found in local database show error message and buton to retry
using :-
------------
code language -> Kotlin

architecture component -> Data Binding/ Live Data / View Model

design architecture -> Mvvm

Web Service -> Retrofit+okhttp+Moshi+Coroutine 

Local Tests and instrumented working on it
