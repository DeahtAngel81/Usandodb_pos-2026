package com.marcioposgraduacao.usandodb_pos

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.marcioposgraduacao.usandodb_pos.database.DatabaseHandler
import com.marcioposgraduacao.usandodb_pos.databinding.ActivityMainBinding
import com.marcioposgraduacao.usandodb_pos.entity.Cadastro

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var banco: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.main)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        banco = DatabaseHandler(this)

        binding.btIncluir.setOnClickListener {
            incluir()
        }

        binding.btAlterar.setOnClickListener {
            alterar()
        }

        binding.btExcluir.setOnClickListener {
            excluir()
        }

        binding.btPesquisar.setOnClickListener {
            pesquisar()
        }

        binding.btListar.setOnClickListener {
            listar()
        }
    }

    /*Sintaxe SQL é essa:
    banco.execSQL("INSERT INTO cadastro VALUES (\"maria", "56136137")")
       banco.execSQL("INSERT INTO cadastro VALUES (\"maria", "56136137")")*/
    private fun incluir() {

        val cadastro = Cadastro(
            0,
            binding.etNome.text.toString(),
            binding.etTelefone.text.toString()
        )
        banco.incluir(cadastro)

        Toast.makeText(
            this, "Inclusão efetuada com sucesso!",
            Toast.LENGTH_LONG
        ).show()

    }

    private fun alterar() {

        val id = binding.etCod.text.toString().toIntOrNull()

        if (id == null) {
            binding.etCod.error = "Digite um código válido"
            return
        }

        val cadastro = Cadastro(
            id,
            binding.etNome.text.toString(),
            binding.etTelefone.text.toString()
        )
        banco.alterar(cadastro)

        Toast.makeText(
            this, "Alteração efetuada com sucesso!",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun excluir() {

        val id = binding.etCod.text.toString().toIntOrNull()

        if (id == null) {
            binding.etCod.error = "Digite Código válido"
            return
        }

        Toast.makeText(
            this, "Exclusão efetuada com sucesso!",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun pesquisar() {

        val id = binding.etCod.text.toString().toIntOrNull()

        if (id == null) {
            binding.etCod.error = "Digite um código válido"
            return
        }

        val cadastro = banco.pesquisar(id)

        if (cadastro != null) {
            binding.etNome.setText(cadastro.nome)
            binding.etTelefone.setText(cadastro.telefone)
        } else {

            Toast.makeText(
                this,
                "Registro não encontrado!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun listar() {

        val registros: MutableList<Cadastro> = banco.listar()

        val saida = StringBuilder()

        registros.forEach { cadastro ->
            saida.append(cadastro.nome)
            saida.append("\n")
        }

        Toast.makeText(
            this,
            saida.toString(),
            Toast.LENGTH_LONG
        ).show()
    }
}