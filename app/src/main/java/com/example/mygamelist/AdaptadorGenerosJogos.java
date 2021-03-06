package com.example.mygamelist;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorGenerosJogos extends RecyclerView.Adapter<AdaptadorGenerosJogos.ViewHolderGenerosJogos>{
    private Cursor cursor;
    private Context context;
    ArrayList<Long> listaIds = new ArrayList<>();
    int x;

    public void setCursor(Cursor cursor) {
        if (this.cursor != cursor) {
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

    public AdaptadorGenerosJogos(Context context)
    {
        this.context = context;
    }
    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public ViewHolderGenerosJogos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemGeneroJogo = LayoutInflater.from(context).inflate(R.layout.item_genero_jogo, parent, false);

        return new AdaptadorGenerosJogos.ViewHolderGenerosJogos(itemGeneroJogo);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderGenerosJogos holder, int position) {
        cursor.moveToPosition(position);
        Genero genero = Genero.fromCursor(cursor);
        holder.setGenero(genero);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (cursor == null) return 0;

        return cursor.getCount();
    }
    private static ViewHolderGenerosJogos viewHolderGeneroJogoSelecionado = null;

    public Genero getGeneroJogoSelecionado(){
        if ( viewHolderGeneroJogoSelecionado == null) return null;
        return viewHolderGeneroJogoSelecionado.genero;
    }

    public class ViewHolderGenerosJogos extends RecyclerView.ViewHolder implements  View.OnClickListener{
        private Genero genero;
        private TextView textViewNomeGenero;

        public ViewHolderGenerosJogos(@NonNull View itemView) {
            super(itemView);
            textViewNomeGenero  = itemView.findViewById(R.id.textViewGeneroItem);
            itemView.setOnClickListener(this);
        }

        public void setGenero(Genero genero){
            this.genero= genero;
            textViewNomeGenero.setText(genero.getNome());

        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {

            viewHolderGeneroJogoSelecionado = this;
            seleciona();

            x=0;
            if(listaIds.size()==0) {
                listaIds.add(viewHolderGeneroJogoSelecionado.genero.getId());

            }else{
                for (int i = 0; i < listaIds.size(); i++) {
                    if (viewHolderGeneroJogoSelecionado.genero.getId() == listaIds.get(i)) {
                        x = 1;
                        listaIds.remove(viewHolderGeneroJogoSelecionado.genero.getId());
                        desSeleciona();
                    }
                }
                if (x == 0) {
                    listaIds.add(viewHolderGeneroJogoSelecionado.genero.getId());
                }
            }
        }
        private void desSeleciona() {
            itemView.setBackgroundResource(android.R.color.white);
            textViewNomeGenero.setTextColor(textViewNomeGenero.getContext().getResources().getColor(R.color.colorCinzento));
        }

        private void seleciona() {
            itemView.setBackgroundResource(R.drawable.border_item_view);
            textViewNomeGenero.setTextColor(textViewNomeGenero.getContext().getResources().getColor(android.R.color.white));
        }
    }
    public ArrayList<Long> lista(){
        return listaIds;
    }
}

