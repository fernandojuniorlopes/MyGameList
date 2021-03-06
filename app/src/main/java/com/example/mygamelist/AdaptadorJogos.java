package com.example.mygamelist;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorJogos extends RecyclerView.Adapter<AdaptadorJogos.ViewHolderJogos> {
    private Cursor cursor;
    private Context context;
    public static final String ID_JOGO = "ID_JOGO";

    public void setCursor(Cursor cursor) {
        if (this.cursor != cursor) {
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

    public AdaptadorJogos(Context context)
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
    public ViewHolderJogos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemJogo;
        if(context instanceof JogosActivity) {
            itemJogo = LayoutInflater.from(context).inflate(R.layout.item_jogo, parent, false);

        }else{
            itemJogo = LayoutInflater.from(context).inflate(R.layout.item_jogo_main_activity, parent, false);
        }
        return new ViewHolderJogos(itemJogo);
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
    public void onBindViewHolder(@NonNull ViewHolderJogos holder, int position) {
        cursor.moveToPosition(position);
        Jogo jogo = Jogo.fromCursor(cursor);
        holder.setJogo(jogo);
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

    public Jogo getJogoSelecionado(){
        if (viewHolderJogoSelecionado == null) return null;

        return viewHolderJogoSelecionado.jogo;
    }

    private static ViewHolderJogos viewHolderJogoSelecionado = null;

    public class ViewHolderJogos extends RecyclerView.ViewHolder implements  View.OnClickListener{
        private TextView textViewTitulo;
        private TextView textViewAtividade;
        private ImageView IconFavorito;
        private  ImageView imagem;

        private Jogo jogo;

        public ViewHolderJogos (@NonNull View itemView) {
            super(itemView);

            textViewTitulo = (TextView)itemView.findViewById(R.id.textViewNomeItem);
            textViewAtividade = (TextView)itemView.findViewById(R.id.textViewAtividadeItem);
            IconFavorito = itemView.findViewById(R.id.imageViewFavoritos);
            imagem = itemView.findViewById(R.id.imageViewFoto);

            itemView.setOnClickListener(this);
        }

        public  void setJogo(Jogo jogo){
            this.jogo = jogo;

            if(context instanceof JogosActivity) {
                textViewTitulo.setText(jogo.getNome());
                textViewAtividade.setText(jogo.getAtividade());

                byte[] imagemByte = jogo.getImagem();

                Bitmap bitmap = BitmapFactory.decodeByteArray(imagemByte, 0, imagemByte.length);

                imagem.setImageBitmap(bitmap);


                if (jogo.getFavorito() == 1) {
                    IconFavorito.setColorFilter(IconFavorito.getContext().getResources().getColor(R.color.colorAmarelo));
                } else {
                    IconFavorito.setColorFilter(IconFavorito.getContext().getResources().getColor(R.color.colorCinzento));
                }

                if (jogo.getAtividade().equals("Não jogado")) {
                    textViewAtividade.setTextColor(textViewAtividade.getContext().getResources().getColor(R.color.colorCinzento));
                } else if (jogo.getAtividade().equals("A jogar")) {
                    textViewAtividade.setTextColor(textViewAtividade.getContext().getResources().getColor(R.color.colorVerde));
                } else {
                    textViewAtividade.setTextColor(textViewAtividade.getContext().getResources().getColor(R.color.colorAzul));
                }
            }else {
                    byte[] imagemByte = jogo.getImagem();

                    Bitmap bitmap = BitmapFactory.decodeByteArray(imagemByte, 0, imagemByte.length);

                    imagem.setImageBitmap(bitmap);
            }
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {

            if(context instanceof JogosActivity) {
                if (viewHolderJogoSelecionado != null) {
                    viewHolderJogoSelecionado.desSeleciona();
                }

                viewHolderJogoSelecionado = this;

                ((JogosActivity) context).atualizaOpcoesMenu();

                seleciona();
            }else{

                Intent intent = new Intent((context), DetalhesJogoActivity.class);
                intent.putExtra(ID_JOGO, jogo.getId());
                context.startActivity(intent);
            }
        }

        private void desSeleciona() {
            itemView.setBackgroundResource(R.color.colorItemBackground);
            textViewTitulo.setTextColor(textViewTitulo.getContext().getResources().getColor(R.color.colorCinzento));
        }

        private void seleciona() {
            textViewTitulo.setTextColor(textViewTitulo.getContext().getResources().getColor(R.color.colorAmarelo));
            itemView.setBackgroundResource(R.drawable.border_item_view);
        }
    }
}
