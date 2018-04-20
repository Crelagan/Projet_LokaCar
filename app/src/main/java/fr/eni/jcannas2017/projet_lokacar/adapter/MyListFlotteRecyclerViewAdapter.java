package fr.eni.jcannas2017.projet_lokacar.adapter;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.eni.jcannas2017.projet_lokacar.beans.Vehicule;
import fr.eni.jcannas2017.projet_lokacar.fragment.ListFlotteFragment.OnListFragmentInteractionListener;
import fr.eni.jcannas2017.projet_lokacar.R;
import fr.eni.jcannas2017.projet_lokacar.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyListFlotteRecyclerViewAdapter extends RecyclerView.Adapter<MyListFlotteRecyclerViewAdapter.ViewHolder> {

    private final List<Vehicule> lstVehicule;
    private final OnListFragmentInteractionListener mListener;

    public MyListFlotteRecyclerViewAdapter(List<Vehicule> items, OnListFragmentInteractionListener listener) {
        lstVehicule = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_listflotte, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Resources res = holder.mView.getContext().getResources();

        holder.mItem = lstVehicule.get(position);

        holder.mModele.setText(lstVehicule.get(position).getMarque() + " " + lstVehicule.get(position).getModele());
        holder.mCarburant.setText(lstVehicule.get(position).getCarburant());
        holder.mEtatLoc.setText(lstVehicule.get(position).getEtatLoc() == 1 ? "Loué" : "Libre");
        holder.mPrix.setText(String.valueOf(lstVehicule.get(position).getTarif()));
        holder.mPuissance.setText(String.valueOf(lstVehicule.get(position).getPuissanceAdmin()));
        holder.mType.setText(lstVehicule.get(position).getType());
        if ( holder.mEtatLoc.getText() == "Loué" )
        {
            holder.card.setCardBackgroundColor(res.getColor(R.color.red));
        }else {
            holder.card.setCardBackgroundColor(res.getColor(R.color.green));
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstVehicule.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mPrix;
        public final TextView mModele;
        public final TextView mPuissance;
        public final TextView mCarburant;
        public final TextView mEtatLoc;
        public final TextView mType;
        public final CardView card;
        public Vehicule mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPrix = (TextView) view.findViewById(R.id.tvPrix);
            mModele = (TextView) view.findViewById(R.id.content);
            mPuissance = (TextView) view.findViewById(R.id.tvPuissance);
            mCarburant = (TextView) view.findViewById(R.id.tvCarburant);
            mEtatLoc = (TextView) view.findViewById(R.id.tvStatut);
            mType = (TextView) view.findViewById(R.id.tvType);
            card = (CardView) view.findViewById(R.id.card);
        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "mView=" + mView +
                    ", mPrix=" + mPrix +
                    ", mModele=" + mModele +
                    ", mPuissance=" + mPuissance +
                    ", mCarburant=" + mCarburant +
                    ", mEtatLoc=" + mEtatLoc +
                    ", mType=" + mType +
                    ", mItem=" + mItem +
                    '}';
        }
    }
}
