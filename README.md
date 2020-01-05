# MapTheCity

Small showcase application that loads 200k rows of cities from a JSON file in the assets folder and presents them to the user.
The cities contain name, country code and coordinates and can be located on a map.

The app has been tested on:

- Google Pixel 3 XL API 29
- Google Nexus 9 API 25

Possible improvements:

- Use DI framework (Dagger 2, Koin) as a replacement for the `ServiceLocator` pattern used.
- Use a DB framework such as Room.
- Create a backend service to fetch the list of cities from a server, instead of from the assets folder.

![](https://github.com/noloman/MapTheCity/blob/master/art/portrait_list.png)
![](https://github.com/noloman/MapTheCity/blob/master/art/portrait_map.png)
![](https://github.com/noloman/MapTheCity/blob/master/art/landscape.png)

