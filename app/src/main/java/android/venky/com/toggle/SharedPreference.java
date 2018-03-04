package android.venky.com.toggle;

/**
 * Created by saurabh on 3/5/2016.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.gson.Gson;

public class SharedPreference {

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "devices";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<Device> favorites) {
        Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, Device product) {
        List<Device> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Device>();
        favorites.add(product);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Device product) {
        ArrayList<Device> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(product);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<Device> getFavorites(Context context) {
        SharedPreferences settings;
        List<Device> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Device[] favoriteItems = gson.fromJson(jsonFavorites,
                    Device[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Device>(favorites);
        } else
            return null;

        return (ArrayList<Device>) favorites;
    }
}