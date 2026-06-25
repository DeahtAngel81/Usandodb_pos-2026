package com.marcioposgraduacao.usandodb_pos.adapter

import android.content.Context
import android.content.Intent
import android.media.Image
import android.sax.RootElement
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.marcioposgraduacao.usandodb_pos.MainActivity
import com.marcioposgraduacao.usandodb_pos.R
import com.marcioposgraduacao.usandodb_pos.entity.Cadastro

class ElementoListaAdapter(val context: Context, val registros: MutableList<Cadastro>) : BaseAdapter() {

    override fun getCount(): Int {
       return registros.size
    }

    override fun getItem(pos: Int): Any? {
        val cadastro = Cadastro(
            registros[pos].id,
            registros[pos].nome,
            registros[pos].telefone
        )
        return cadastro
    }

    override fun getItemId(pos: Int): Long {
        return registros[pos].id.toLong()
    }

    override fun getView(
        pos: Int,
        componentOrigem: View?,
        rootElement: ViewGroup?
    ): View? {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.elemento_lista, null)

        val tvNomeElementoLista: TextView = v.findViewById(R.id.tvNomeElementoLista)
        val tvTelefoneElementoLista: TextView = v.findViewById(R.id.tvTelefoneElementoLista)
        val imageView: ImageView = v.findViewById(R.id.imageView)
        val btEditarElementoLista: ImageButton = v.findViewById(R.id.btEditarElementoLista)

        tvNomeElementoLista.text = registros[pos].nome
        tvTelefoneElementoLista.text = registros[pos].telefone

        if(pos % 2 ==0) {
            imageView.setImageResource(android.R.drawable.star_big_on)
        }
        else{
            imageView.setImageResource(android.R.drawable.star_big_off)
        }

        btEditarElementoLista.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("id", registros[pos].id)
            intent.putExtra("nome", registros[pos].nome)
            intent.putExtra("telefone", registros[pos].telefone)
            context.startActivity(intent)

        }

        return v
    }


    /*override fun getView(pos: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        val view: View

        if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.elemento_lista, parent, false)

            holder = ViewHolder()
            holder.tvNome = view.findViewById(R.id.tvNomeElementoLista)
            holder.tvTelefone = view.findViewById(R.id.tvTelefoneElementoLista)
            holder.imageView = view.findViewById(R.id.imageView)
            val btEditarElementoLista: ImageButton = view.findViewById(R.id.btEditarElementoLista)

            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val registro = registros[pos]

        holder.tvNome?.text = registro.nome ?: "Sem Nome"
        holder.tvTelefone?.text = registro.telefone ?: "Sem Telefone"

        if (pos % 2 == 0) {
            holder.imageView?.setImageResource(android.R.drawable.star_big_on)
        } else {
            holder.imageView?.setImageResource(android.R.drawable.star_big_off)
        }

        holder.btEditar?.setOnClickListener {
            Toast.makeText(
                context, "Editar: ${registro.nome}",
                Toast.LENGTH_SHORT
            )
                .show()

        }

    return view
}*/


    private class ViewHolder {
        var tvNome: TextView? = null
        var tvTelefone: TextView? = null
        var imageView: ImageView? = null
        var btEditar: View? = null
    }

}

