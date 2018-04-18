package fr.eni.jcannas2017.projet_lokacar.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import fr.eni.jcannas2017.projet_lokacar.R;
import fr.eni.jcannas2017.projet_lokacar.beans.Vehicule;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setText(String content) {
        EditText edit = getView().findViewById(R.id.etModele);
        edit.setText(content);
    }

    public void chargeDetail(Vehicule vehicule) {

        Log.i("TAG", "marque : " + vehicule.getMarque());
        Log.i("TAG", "modele : " + vehicule.getModele());
        Log.i("TAG", "boite : " + vehicule.getBoiteVitesse());
        Log.i("TAG", "conso : " + String.valueOf(vehicule.getConsomation()));
        Log.i("TAG", "carburant : " + vehicule.getCarburant());
        Log.i("TAG", "immat : " + vehicule.getImmatriculation());
        Log.i("TAG", "prix : " + String.valueOf(vehicule.getTarif()));
        Log.i("TAG", "type : " + String.valueOf(vehicule.getType()));
        Log.i("TAG", "puissance : " + vehicule.getPuissanceAdmin());


        EditText marque = getView().findViewById(R.id.etMarque);
        marque.setText(vehicule.getMarque());
        marque.setEnabled(false);

        EditText modele = getView().findViewById(R.id.etModele);
        modele.setText(vehicule.getModele());
        modele.setEnabled(false);

        EditText puissance = getView().findViewById(R.id.etPuissance);
        puissance.setText(String.valueOf(vehicule.getPuissanceAdmin()));
        puissance.setEnabled(false);

        CheckBox boite = getView().findViewById(R.id.cbBoite);
        boite.setEnabled(false);
        if (vehicule.getBoiteVitesse().equals("automatique")){
            boite.setChecked(true);
        }else boite.setChecked(false);

        EditText conso = getView().findViewById(R.id.etConso);
        conso.setText(String.valueOf(vehicule.getConsomation()));
        conso.setEnabled(false);

        EditText carburant = getView().findViewById(R.id.etCarburant);
        carburant.setText(vehicule.getCarburant());
        carburant.setEnabled(false);

        EditText immat = getView().findViewById(R.id.etImmat);
        immat.setText(vehicule.getImmatriculation());
        immat.setEnabled(false);

        EditText prix = getView().findViewById(R.id.etPrix);
        prix.setText(String.valueOf(vehicule.getTarif()));
        prix.setEnabled(false);

        EditText type = getView().findViewById(R.id.etType);
        type.setText(String.valueOf(vehicule.getType()));
        type.setEnabled(false);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
