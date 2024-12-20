package vn.edu.usth.connect.StudyBuddy.SB_RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.connect.R;

public class Rcm_UserAdapter extends RecyclerView.Adapter<Rcm_UserViewHolder>{

    Context context;
    List<Rcm_UserItem> items;

    public Rcm_UserAdapter(Context context, List<Rcm_UserItem> item){
        this.context = context;
        this.items = item;
    }

    @NonNull
    @Override
    public Rcm_UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Rcm_UserViewHolder(LayoutInflater.from(context).inflate(R.layout.rcm_user_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Rcm_UserViewHolder holder, int position) {
        Rcm_UserItem item = items.get(position);

        holder.name.setText(item.getName());
        holder.gender.setText(item.getGender());
        holder.major.setText(item.getMajor());
        holder.looking_for.setText(item.getLooking_for());
        holder.image.setImageResource(item.getImage());

//        holder.itemView.setOnClickListener(view -> {
//            Intent i = new Intent(context, UserProfileActivity.class);
//            i.putExtra("Name", item.getName());
//            context.startActivity(i);
//        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
