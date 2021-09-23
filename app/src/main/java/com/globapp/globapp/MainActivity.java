package com.globapp.globapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.globapp.globapp.classes.News;
import com.globapp.globapp.classes.NewsRecognition;
import com.globapp.globapp.classes.Recognition;
import com.globapp.globapp.classes.User;
import com.globapp.globapp.fragmentlogin.FragmentLogin;
import com.globapp.globapp.fragmentmain.FragmentMain;
import com.globapp.globapp.fragmentmain.fragmentnews.FragmentNews;

import java.util.ArrayList;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    public static final String DATA       = "DATA";
    public static final String DARK_MODE  = "DARK_MODE";
    public static final String IS_ENGLISH = "IS_ENGLISH";

    public FragmentMain  fragmentMain;
    public FragmentLogin fragmentLogin;

    public GifImageView animationContainer;

    public Boolean isDarkMode;
    public Boolean isEnglish;

    // Temporal data
    public User me;
    public ArrayList<User> users;
    public ArrayList<News> news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createData();
        loadData();
        animationContainer = findViewById(R.id.animation_container);

        fragmentLogin      = new FragmentLogin();
        fragmentMain       = new FragmentMain();
        addFragment(fragmentLogin);
    }

    public void enableAnimation(int animationID){
        animationContainer.setImageResource(animationID);
        ((GifDrawable)animationContainer.getDrawable()).start();
        ((GifDrawable)animationContainer.getDrawable()).addAnimationListener(i -> {
            ((GifDrawable)animationContainer.getDrawable()).stop();
            animationContainer.setImageBitmap(null);
        });
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count  == 1) {
            if(getSupportFragmentManager().getFragments().get(0) instanceof FragmentMain ||
               getSupportFragmentManager().getFragments().get(0) instanceof FragmentNews){
                if(fragmentMain.mainViewPager.getCurrentItem() == FragmentMain.NEWS){
                    moveTaskToBack(true);
                } else {
                    fragmentMain.mainViewPager.setCurrentItem(FragmentMain.NEWS);
                }
            } else {
                moveTaskToBack(true);
            }
        } else {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(DATA, MODE_PRIVATE);
        isDarkMode = sharedPreferences.getBoolean(DARK_MODE, false);
        isEnglish  = sharedPreferences.getBoolean(IS_ENGLISH, true);
    }

    public void addFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_up,
                        R.anim.slide_out_down,
                        R.anim.slide_in_down,
                        R.anim.slide_out_up)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    public static class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }

    private void createData(){
        users = new ArrayList<>();
        users.add(new User("1", "Samanta de Jesús Echeverri", "Dirigente encargada del Departamento de Manufactura", getUri(0), null));
        users.add(new User("2", "José Hernesto Pérez", "Dirigente encargado del Departamento de Tecnología y Software", getUri(1), null));
        users.add(new User("3", "Cristiano Ronaldo", "Máximo ponente en Relaciones Internacionales", getUri(2), null));
        users.add(new User("4", "Julián Arboleda Bermudez", "Alto mando de la Unidad de Control de Finanzas", getUri(3), null));
        users.add(new User("5", "José Castro Peláez", "Director Ejecutivo del Departamento de Logistica", getUri(4), null));
        users.add(new User("6", "Leonel Álvares Arias", "Dirigente personal del aseo", getUri(6), null));

        /*
        me.addMeRecognitions(new Recognition("Mejor empleado de la temporada 2020-2021", 1));
        me.addMeRecognitions(new Recognition("Empleado más participativo de la semana 27", 2));
        me.addMeRecognitions(new Recognition("Mayor tiempo dedicado al proyecto 'EX-APP'", 3));
        me.addMeRecognitions(new Recognition("Participación destacada en la ultima investigacion del DANE", 4));
        me.addMeRecognitions(new Recognition("Reciclador del mes Agosto", 5));

         */


        news = new ArrayList<>();
        news.add(new NewsRecognition("0", "¡Excelente trabajo allá en Ibiza! Muy bien manejada esa conferencia.", null, users.get(1), users.get(0)));
        news.add(new NewsRecognition("1", "Bienvenido a la empresa compañero, que la pases muy bien.", null, users.get(2), users.get(1)));
        news.add(new NewsRecognition("2", "Gran experiencia la llevada contigo en el evento de la FECODE!!!", null, users.get(3), users.get(2)));
        news.add(new NewsRecognition("0", "Excelente trabajo allá en Cartagena! Muy bien manejada esa conferencia.", null, users.get(1), users.get(4)));
        news.add(new NewsRecognition("0", "Dios te bendiga y muchos exitos.", null, users.get(2), users.get(5)));
        news.add(new News("0", "Amo trabajar acá :).", null, users.get(3)));
        news.add(new News("0", "Espero nunca salir de acá.", null, users.get(1)));
        news.add(new NewsRecognition("0", "Merecido el reconocimiento mi hermano, muy buen desepeño", null, users.get(0), users.get(5)));
        news.add(new NewsRecognition("0", "Nos fuimos a Italia gracias a tu entrega!!!", null, users.get(5), users.get(4)));
        news.add(new News("0", "Mañana la empresa se expandirá a Italia gente", null, users.get(2)));

    }

    //TEMPORAL
    private Uri getUri(int n){
        switch (n){
            case 0:
                return new Uri.Builder()
                        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                        .authority(getResources().getResourcePackageName(R.drawable.user2))
                        .appendPath(getResources().getResourceTypeName(R.drawable.user2))
                        .appendPath(getResources().getResourceEntryName(R.drawable.user2))
                        .build();
            case 1:
                return new Uri.Builder()
                        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                        .authority(getResources().getResourcePackageName(R.drawable.user3))
                        .appendPath(getResources().getResourceTypeName(R.drawable.user3))
                        .appendPath(getResources().getResourceEntryName(R.drawable.user3))
                        .build();
            case 2:
                return new Uri.Builder()
                        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                        .authority(getResources().getResourcePackageName(R.drawable.user4))
                        .appendPath(getResources().getResourceTypeName(R.drawable.user4))
                        .appendPath(getResources().getResourceEntryName(R.drawable.user4))
                        .build();
            case 3:
                return new Uri.Builder()
                        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                        .authority(getResources().getResourcePackageName(R.drawable.user5))
                        .appendPath(getResources().getResourceTypeName(R.drawable.user5))
                        .appendPath(getResources().getResourceEntryName(R.drawable.user5))
                        .build();
            case 4:
                return new Uri.Builder()
                        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                        .authority(getResources().getResourcePackageName(R.drawable.user6))
                        .appendPath(getResources().getResourceTypeName(R.drawable.user6))
                        .appendPath(getResources().getResourceEntryName(R.drawable.user6))
                        .build();
            case 5:
                return new Uri.Builder()
                        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                        .authority(getResources().getResourcePackageName(R.drawable.user7))
                        .appendPath(getResources().getResourceTypeName(R.drawable.user7))
                        .appendPath(getResources().getResourceEntryName(R.drawable.user7))
                        .build();
            case 6:
                return new Uri.Builder()
                        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                        .authority(getResources().getResourcePackageName(R.drawable.user8))
                        .appendPath(getResources().getResourceTypeName(R.drawable.user8))
                        .appendPath(getResources().getResourceEntryName(R.drawable.user8))
                        .build();
            default:
                return null;
        }

    }
}