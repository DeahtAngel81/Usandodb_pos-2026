package com.marcioposgraduacao.usandodb_pos

import android.database.Cursor
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SimpleCursorAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.marcioposgraduacao.usandodb_pos.adapter.ElementoListaAdapter
import com.marcioposgraduacao.usandodb_pos.database.DatabaseHandler
import com.marcioposgraduacao.usandodb_pos.databinding.ActivityListarBinding
import com.marcioposgraduacao.usandodb_pos.entity.Cadastro

class ListarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListarBinding
    private lateinit var banco: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListarBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.main)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        banco = DatabaseHandler(this)

        val registros: MutableList<Cadastro> = banco.listar()

        val adapter = ElementoListaAdapter(
            this,
            registros)

        binding.lvCadastro.adapter = adapter
    }
}