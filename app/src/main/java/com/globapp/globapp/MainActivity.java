package com.globapp.globapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.globapp.globapp.classes.Me;
import com.globapp.globapp.classes.News;
import com.globapp.globapp.classes.Recognition;
import com.globapp.globapp.fragmentlogin.FragmentLogin;
import com.globapp.globapp.fragmentmain.FragmentMain;
import com.globapp.globapp.fragmentmain.fragmentnews.NewsListAdapter;
import com.globapp.globapp.fragmentmain.fragmentnews.NewsPagerAdapter;

import java.nio.channels.NonReadableChannelException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public FragmentMain fragmentMain;
    public FragmentLogin fragmentLogin;

    public Boolean isDarkMode;
    public Boolean isEnglish;

    // Temporal
    public Me me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createData();
        isDarkMode    = false;
        isEnglish     = true;
        fragmentLogin = new FragmentLogin();
        fragmentMain  = new FragmentMain();
        addFragment(fragmentLogin);
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

    public void addFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, fragment)
                .setReorderingAllowed(true)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }

    public static class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }

    // Mientras hacemos la base de datos jajja
    private void createData(){
        Recognition meRecognition1 = new Recognition("Mejor empleado de la temporada 2020-2021", 1);
        Recognition meRecognition2 = new Recognition("Empleado más participativo de la semana 27", 2);
        Recognition meRecognition3 = new Recognition("Mayor tiempo dedicado al proyecto 'EX-APP'", 3);
        Recognition meRecognition4 = new Recognition("Participación destacada en la ultima investigacion del DANE", 4);
        Recognition meRecognition5 = new Recognition("Reciclador del mes Agosto", 5);

        ArrayList<Recognition> meRecognitions = new ArrayList<>();
        meRecognitions.add(meRecognition1);
        meRecognitions.add(meRecognition2);
        meRecognitions.add(meRecognition3);
        meRecognitions.add(meRecognition4);
        meRecognitions.add(meRecognition5);

        News meActivity1 = new News("0", "Ha dado un reconocimiento a Javier Ibarra. '¡Excelente trabajo allá en Ibiza! Muy bien manejada esa conferencia'.", 1, "Sebastian Samuel Castro Martínez", 1);
        News meActivity2 = new News("1", "Ha dado un reconocimiento a Samuel Vanegas. 'Bienvenido a la empresa compañero, que la pases muy bien'.", 2, "Sebastian Samuel Castro Martínez", 1);
        News meActivity3 = new News("2", "Ha dado un reconocimiento a Samantha Ruedas. 'Gran experiencia la llevada contigo en el evento de la FECODE!!!'.", 3, "Sebastian Samuel Castro Martínez", 1);
        ArrayList<News> meActivities = new ArrayList<>();
        meActivities.add(meActivity1);
        meActivities.add(meActivity2);
        meActivities.add(meActivity3);

        me = new Me("0", "Sebastian Samuel Castro Martínez",
                "Encargado de la división de Sistemas Técnicos de la sede de Ayacucho",
                12, 215, 1, 1, meActivities, meRecognitions);
    }
}