package com.example.jorav.examenjordi;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.jorav.examenjordi.Model.Post;
import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    Post[] posts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerPost);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Creamos el AsyncTask que conectara con la Url proporcionada
        new  conectarURLAsync().execute("https://jsonplaceholder.typicode.com/posts?userId=1");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //Esta clase controlara el item seleccionado en el NavigationDrawer. Segun el intem elegido se lanzara una notificación con el nombre del item del ND
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.post_inicio) {
            Utils.mostrarNotificacion(getApplicationContext(),"Inicio");
        } else if (id == R.id.post_fotos) {
            Utils.mostrarNotificacion(getApplicationContext(),"Fotos");
        } else if (id == R.id.post_peliculas) {
            Utils.mostrarNotificacion(getApplicationContext(),"Peliculas");
        } else if (id == R.id.post_notificaciones) {
            Utils.mostrarNotificacion(getApplicationContext(),"Notificaciones");
        } else if (id == R.id.post_configuracion) {
            Utils.mostrarNotificacion(getApplicationContext(),"Configuración");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    //Async que sirve para conectar a la URL que se le pase
    public class conectarURLAsync extends AsyncTask<String,Void,String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Iniciamos el progressDialog antes de que se inicie el Async
            progressDialog = ProgressDialog.show(MainActivity.this,"","Cargando...",true);
        }

        @Override
        protected String doInBackground(String... strings) {
            //Parseamos la URL con Gson. Metemos los objetos Post en un array de Post
            Gson gson = new Gson();
             posts =  gson.fromJson(Utils.conectaURL(strings[0]),Post[].class);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Cuando finaliza el Async le pasamos al AdapterPost el Array de Post
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            AdapterPost adapterPost = new AdapterPost(posts);
            recyclerView.setAdapter(adapterPost);
            //Finalizamos el ProgessDialog cuando acaba de ejecutarse el Async
            progressDialog.dismiss();
        }
    }
}
