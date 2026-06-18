package com.marcioposgraduacao.usandodb_pos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.marcioposgraduacao.usandodb_pos.R

import com.marcioposgraduacao.usandodb_pos.entity.Cadastro
import java.lang.reflect.RecordComponent

class ElementoListaAdapter(val context: Context, val registros: MutableList<Cadastro>): BaseAdapter() {

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
        componenteOrigem: View?,
        rootComponent: ViewGroup?
    ): View? {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.elemento_lista, null)

        val tvNomeElementoLista: TextView = v.findViewById(R.id.tvNomeElementoLista)
        val tvTelefoneElementoLista: TextView = v.findViewById(R.id.tvTelefoneElementoLista)
        val imageView: ImageView = v.findViewById(R.id.imageView)

        tvNomeElementoLista.text = registros[pos].nome
        tvTelefoneElementoLista.text = registros[pos].telefone

        if (pos % 2 == 0) {
            imageView.setImageResource(android.R.drawable.presence_online)
        }
        else {
            imageView.setImageResource(android.R.drawable.presence_offline)
        }

        return v
    }
}