package com.globapp.globapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.globapp.globapp.classes.Me;
import com.globapp.globapp.classes.News;
import com.globapp.globapp.classes.NewsRecognition;
import com.globapp.globapp.classes.Recognition;
import com.globapp.globapp.classes.User;
import com.globapp.globapp.fragmentlogin.FragmentLogin;
import com.globapp.globapp.fragmentmain.FragmentMain;
import java.util.ArrayList;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    public static final String DATA      = "DATA";
    public static final String DARK_MODE = "DARK_MODE";

    public FragmentMain  fragmentMain;
    public FragmentLogin fragmentLogin;

    public GifImageView animationContainer;

    public Boolean isDarkMode;
    public Boolean isEnglish;

    // Temporal data
    public Me me;
    public ArrayList<User> users;
    public ArrayList<News> news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createData();
        loadData();
        animationContainer = findViewById(R.id.animation_container);
        isEnglish          = true;

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
            moveTaskToBack(true);
        } else {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(DATA, MODE_PRIVATE);
        isDarkMode = sharedPreferences.getBoolean(DARK_MODE, false);
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
        users.add(new User("1", "Samanta de Jesús Echeverri", "Dirigente encargada del Departamento de Manufactura", 2, 2));
        users.add(new User("2", "José Hernesto Pérez", "Dirigente encargado del Departamento de Tecnología y Software", 3, 3));
        users.add(new User("3", "Cristiano Ronaldo", "Máximo ponente en Relaciones Internacionales", 4, 4));
        users.add(new User("4", "Julián Arboleda Bermudez", "Alto mando de la Unidad de Control de Finanzas", 5, 5));
        users.add(new User("5", "José Castro Peláez", "Director Ejecutivo del Departamento de Logistica", 6, 6));
        users.add(new User("6", "Leonel Álvares Arias", "Dirigente personal del aseo", 7, 7));

        me = new Me("0", "Sebastian Samuel Castro Martínez",
                "Encargado de la división de Sistemas Técnicos de la sede de Ayacucho",
                22, 215, 1, 1);

        me.addMeRecognitions(new Recognition("Mejor empleado de la temporada 2020-2021", 1));
        me.addMeRecognitions(new Recognition("Empleado más participativo de la semana 27", 2));
        me.addMeRecognitions(new Recognition("Mayor tiempo dedicado al proyecto 'EX-APP'", 3));
        me.addMeRecognitions(new Recognition("Participación destacada en la ultima investigacion del DANE", 4));
        me.addMeRecognitions(new Recognition("Reciclador del mes Agosto", 5));

        me.addMeNews(new NewsRecognition("0", "¡Excelente trabajo allá en Ibiza! Muy bien manejada esa conferencia.", null, me, users.get(0)));
        me.addMeNews(new NewsRecognition("1", "Bienvenido a la empresa compañero, que la pases muy bien.", null, me, users.get(1)));
        me.addMeNews(new NewsRecognition("2", "Gran experiencia la llevada contigo en el evento de la FECODE!!!", null, me, users.get(2)));

        news = new ArrayList<>();
        news.add(new NewsRecognition("0", "Excelente trabajo allá en Cartagena! Muy bien manejada esa conferencia.", null, users.get(1), me));
        news.add(new NewsRecognition("0", "Dios te bendiga y muchos exitos.", null, users.get(2), me));
        news.add(new News("0", "Amo trabajar acá :).", null, users.get(3)));
        news.add(new News("0", "Espero nunca salir de acá.", null, users.get(1)));
        news.add(new NewsRecognition("0", "Merecido el reconocimiento mi hermano, muy buen desepeño", null, me, users.get(5)));
        news.add(new NewsRecognition("0", "Nos fuimos a Italia gracias a tu entrega!!!", null, me, users.get(4)));
        news.add(new News("0", "Mañana la empresa se expandirá a Italia gente", null, users.get(2)));

    }
}