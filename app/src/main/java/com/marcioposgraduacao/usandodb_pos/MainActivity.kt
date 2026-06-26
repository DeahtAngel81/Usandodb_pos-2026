package com.marcioposgraduacao.usandodb_pos

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log.e
import android.view.View
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.marcioposgraduacao.usandodb_pos.database.DatabaseHandler
import com.marcioposgraduacao.usandodb_pos.databinding.ActivityMainBinding
import com.marcioposgraduacao.usandodb_pos.entity.Cadastro

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var banco: DatabaseHandler

    val db = Firebase.firestore

    @SuppressLint("SuspiciousIndentation")
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

        if (intent.getIntExtra("id", 0) > 0) {
            binding.etCod.setText(intent.getIntExtra("id", 0).toString())
            binding.etNome.setText(intent.getStringExtra("nome"))
            binding.etTelefone.setText(intent.getStringExtra("telefone"))

        } else {
            binding.btExcluir.visibility = View.GONE
            binding.btPesquisar.visibility = View.GONE
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
    }

    private fun alterar() {

        val cadastro: HashMap<String, String> = hashMapOf(
            "nome" to binding.etNome.text.toString(),
            "telefone" to binding.etTelefone.text.toString(),

        )

        db
            .collection("Cadastramento")
            .document(binding.etCod.text.toString())
            .set(cadastro)
            .addOnSuccessListener {
                makeText(
                    this, "Operação efetuada com sucesso!",
                    Toast.LENGTH_LONG
                ).show()

                finish()

            }
            .addOnFailureListener { e ->
                Toast.makeText(this,
                    "Erro ${e.message}", Toast.LENGTH_LONG).show()
            }




        /*val id = binding.etCod.text.toString().toIntOrNull()

        if (id == null) {
            val cadastro = Cadastro(
                0,
                binding.etNome.text.toString(),
                binding.etTelefone.text.toString()
            )
            banco.incluir(cadastro)
        } else {

            val cadastro = Cadastro(
                id,
                binding.etNome.text.toString(),
                binding.etTelefone.text.toString()
            )
            banco.alterar(cadastro)
        }*/


    }

    private fun excluir() {

        db
            .collection("Cadastramento")
            .document(binding.etCod.text.toString())
            .delete()
            .addOnSuccessListener {

                makeText(
                    this, "Operação efetuada com sucesso!",
                    Toast.LENGTH_LONG
                ).show()

                finish()

            }
            .addOnFailureListener { e ->
                Toast.makeText(this,
                    "Erro ${e.message}", Toast.LENGTH_LONG).show()

            }


        /*val id = binding.etCod.text.toString().toIntOrNull()

        if (id == null) {
            binding.etCod.error = "Digite Código válido"
            return
        }

        banco.excluir(id)

        makeText(
            this,
            "Exclusão efetuada com sucesso!",
            Toast.LENGTH_LONG
        ).show()*/

        finish()
    }

    private fun pesquisar() {

        val etCodPesquisa = EditText(this)
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Insira o código de Pesquisa")
        dialog.setCancelable(false)
        dialog.setNegativeButton("fechar", null)
        dialog.setPositiveButton("Pesquisar", { dialog, which ->

            val id: Int? = etCodPesquisa.text.toString().toIntOrNull()

            if (id == null) {
                makeText(
                    this, "Código deve ser informado...",
                    Toast.LENGTH_LONG
                ).show()

            } else {

                db
                    .collection("Cadastramento")
                    .document(id.toString())
                    .get()
                    .addOnSuccessListener { result ->
                        binding.etCod.setText(etCodPesquisa.text.toString())
                        binding.etNome.setText(result.get("nome").toString())
                        binding.etTelefone.setText(result.get("telefone").toString())
                    }
                    .addOnFailureListener { e ->
                        makeText(
                            this,
                            "Registro não encontrado!",
                            Toast.LENGTH_LONG
                        ).show()

                    }
               /* val cadastro = banco.pesquisar(id)

                if (cadastro != null) {
                    binding.etCod.setText(etCodPesquisa.text.toString())
                    binding.etNome.setText(cadastro.nome)
                    binding.etTelefone.setText(cadastro.telefone)
                } else {

                    makeText(
                        this,
                        "Registro não encontrado!",
                        Toast.LENGTH_LONG
                    ).show()*/

            }

        }
        )

        dialog.setView(etCodPesquisa)
        dialog.show()


    }

}
