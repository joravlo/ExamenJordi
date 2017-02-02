package com.example.jorav.examenjordi;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jorav.examenjordi.Model.Post;

/**
 * Created by jorav on 02/02/2017.
 */

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.AdapterViewHolder> {

    private Post[] posts;

    //Recibimos un array de objetos Post
    public AdapterPost(Post[] posts){
        this.posts = posts;
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_post, parent, false);
        return new AdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, final int position) {
        holder.title.setText("Título: "+posts[position].getTitle());
        holder.body.setText("Body: "+posts[position].getBody());

        //Si se pulsa la vista se mostrara un AlertDialog.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final View viewClase = view;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Se van a cargar los comentarios del post "+posts[position].getId()).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Si pulsa Si nos llevara a otra pantalla a la cual le pasaremos una Id
                        Intent intent = new Intent(viewClase.getContext(), ActivityComment.class);
                        intent.putExtra("Id",posts[position].getId());
                        viewClase.getContext().startActivity(intent);
                        dialog.dismiss();
                    }
                })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Si pulsa Cancelar se cerrar el dialog
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.length;
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder{
        TextView title, body;
        public AdapterViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.txtTitlePost);
            body = (TextView) itemView.findViewById(R.id.txtBodyPost);

        }
    }
}
