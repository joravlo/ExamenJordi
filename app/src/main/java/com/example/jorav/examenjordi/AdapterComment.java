package com.example.jorav.examenjordi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jorav.examenjordi.Model.Comment;

/**
 * Created by jorav on 02/02/2017.
 */

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.AdapterViewHolder> {

    private Comment[] comments;

    //Racibimos un array del objeto Comment
    public AdapterComment(Comment[] comments) {
        this.comments = comments;
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_comment, parent, false);
        return new AdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {
        //Asingamos a cada TextView los datos necesarios
        holder.name.setText("Nombre: "+comments[position].getName());
        holder.id.setText("Id: "+comments[position].getId().toString());
        holder.email.setText("Email: "+comments[position].getEmail());
        holder.body.setText("Body: "+comments[position].getBody());
    }

    @Override
    public int getItemCount() {
        return comments.length;
    }


    public class AdapterViewHolder extends RecyclerView.ViewHolder{
        TextView name, body, id, email;
        public AdapterViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txtNameComment);
            body = (TextView) itemView.findViewById(R.id.txtBodyComment);
            id = (TextView) itemView.findViewById(R.id.txtIdComment);
            email = (TextView) itemView.findViewById(R.id.txtEmailComment);
        }
    }
}
