# MapTheCity

Small showcase application that loads 200k rows of cities from a JSON file in the assets folder and presents them to the user.
The cities contain name, country code and coordinates and can be located on a map.

The app has been tested on:

- Google Pixel 3 XL API 29
- Google Nexus 9 API 25

Possible improvements:

- Use DI framework (Dagger 2, Koin) as a replacement for the `ServiceLocator` pattern used.
- Use a DB framework such as Room.
- Create data sources for the repository for a better separation of concerns, as recommended in [Google's Guide to app architecture](https://developer.android.com/jetpack/docs/guide#recommended-app-arch).
- Create a backend service to fetch the list of cities from a server, instead of from the assets folder.
- Improve (or remote) the `About` button and its functionality.
- Augment unit test coverage of the app in general.

![](https://github.com/noloman/MapTheCity/blob/master/art/portrait_list.png)
![](https://github.com/noloman/MapTheCity/blob/master/art/portrait_map.png)
![](https://github.com/noloman/MapTheCity/blob/master/art/landscape.png)
