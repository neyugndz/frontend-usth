package vn.edu.usth.connect.StudyBuddy.Profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.connect.R;

public class InterestCardAdapter extends RecyclerView.Adapter<InterestCardAdapter.InterestCardViewHolder> {
    private List<CardItem> cardItemList;

    public InterestCardAdapter(List<CardItem> cardItemList) {
        this.cardItemList = cardItemList;
    }

    @Override
    public InterestCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false); // Create view from your card_item.xml
        return new InterestCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InterestCardViewHolder holder, int position) {
        CardItem cardItem = cardItemList.get(position);
        holder.textView.setText(cardItem.getText());
    }

    @Override
    public int getItemCount() {
        return cardItemList.size();
    }

    public static class InterestCardViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public InterestCardViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.cardText); // The TextView inside card_item.xml
        }
    }
}
