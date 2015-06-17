package com.jldes.jldes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Noticias extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {


    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */

    private RecyclerView recView;

    private ArrayList<Titular> datos;
    private CharSequence mTitle;
    private List<Noticia> noticias;
    private AdaptadorTitulares adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        datos = new ArrayList<Titular>();
        recView = (RecyclerView) findViewById(R.id.RecView);
        recView.setHasFixedSize(true);
        adaptador = new AdaptadorTitulares(datos);
        recView.setAdapter(adaptador);


        recView.setLayoutManager(new LinearLayoutManager(Noticias.this, LinearLayoutManager.VERTICAL, false));
        //recView.setLayoutManager(new GridLayoutManager(this,3));

//        recView.addItemDecoration(
//                new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));

        recView.setItemAnimator(new DefaultItemAnimator());
        if (estaConectado()) {
            CargarXmlTask tarea = new CargarXmlTask();
            tarea.execute("http://blog.jldes.net/feeds/posts/default?alt=rss");
        }

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }
    private boolean estaConectado() {
        if (conectadoWifi()) {
            return true;
        } else {
            if (conectadoRedMovil()) {
                return true;
            } else {
                showAlertDialog(Noticias.this, "Conexion a Internet",
                        "Tu Dispositivo no tiene Conexion a Internet.", false);
                return false;
            }
        }
    }

    protected Boolean conectadoWifi() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    protected Boolean conectadoRedMovil() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(title);

        alertDialog.setMessage(message);


        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alertDialog.show();
    }
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Intent intent;
        // update the main content by replacing fragments
        switch (position){
            case 0:
                break;
            default:
                break;
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.noticias, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_noticias, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Noticias) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
    private class CargarXmlTask extends AsyncTask<String, Integer, Boolean> {

        protected Boolean doInBackground(String... params) {

            RssParserSax2 saxparser =
                    new RssParserSax2(params[0]);

            noticias = saxparser.parse();

            return true;
        }

        protected void onPostExecute(Boolean result) {

            //Tratamos la lista de noticias
            //Por ejemplo: escribimos los títulos en pantalla
            for (Noticia noticia : noticias.subList(0, 6)) {
                Spanned spanned = Html.fromHtml(noticia.getDescripcion(), getImageHTML(), null);
                datos.add(new Titular(noticia.getTitulo(), "" + spanned));
            }
            adaptador = new AdaptadorTitulares(datos);

            recView.setAdapter(adaptador);
            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(Noticias.this, NoticiaCompleta.class);
//                    intent.putExtra("Noticia", noticias.get(recView.getChildPosition(v)));
//                    startActivity(intent);
                }
            });

        }

        public Html.ImageGetter getImageHTML() {
            Html.ImageGetter ig = new Html.ImageGetter() {
                public Drawable getDrawable(String source) {
                    Log.d("Source",source);
                    try {
                        Drawable d = Drawable.createFromStream(new URL(source).openStream(), "src name");
                        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                        return d;
                    } catch (IOException e) {
                        Log.v("IOException", e.getMessage());
                        return null;
                    }
                }
            };
            return ig;
        }
    }
}
