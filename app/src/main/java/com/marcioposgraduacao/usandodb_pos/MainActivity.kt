package com.marcioposgraduacao.usandodb_pos

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.marcioposgraduacao.usandodb_pos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var banco: SQLiteDatabase

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

        banco = SQLiteDatabase.openOrCreateDatabase(
            this.getDatabasePath(DATABASE_NAME), null
        )

        banco.execSQL(
            "CREATE TABLE IF NOT EXISTS" +
                    " ${TABLE_NAME}( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " nome TEXT, telefone TEXT) "
        )

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

        val registro = ContentValues()
        registro.put("nome", binding.etNome.text.toString())
        registro.put("telefone", binding.etTelefone.text.toString())

        banco.insert(TABLE_NAME, null, registro)
        Toast.makeText(
            this, "Inclusão efetuada com sucesso!",
            Toast.LENGTH_LONG
        ).show()

    }

    private fun alterar() {

        val registro = ContentValues()
        registro.put("nome", binding.etNome.text.toString())
        registro.put("telefone", binding.etTelefone.text.toString())

        banco.update(

            TABLE_NAME,
            registro,
            "_id = ${binding.etCod.text}",
            null

        )

        Toast.makeText(
            this, "Alteração efetuada com sucesso!",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun excluir() {

        banco.delete(
            TABLE_NAME,
            "_id = ${binding.etCod.text}",
            null
        )

        Toast.makeText(
            this, "Exclusão efetuada com sucesso!",
            Toast.LENGTH_LONG
        ).show()

    }

    private fun pesquisar() {

        val registros: Cursor = banco.query(
            TABLE_NAME,
            null,
            "_id = ${binding.etCod.text}",
            null,
            null,
            null,
            null
        )

        if (registros.moveToNext()) {
            binding.etNome.setText(registros.getString(NOME))
            binding.etTelefone.setText(registros.getString(TELEFONE))
        } else {

            Toast.makeText(
                this,
                "Registro não encontrado!",
                Toast.LENGTH_LONG
            ).show()

            registros.close()
        }
    }

    private fun listar() {

        val registros: Cursor = banco.query(
            TABLE_NAME,
            /*Para selecionar especifíco dado da coluna, o código seria
             listOf("nome", etc) Porém com o null
             coletamos todas as informações*/
            null,
            null,
            null,
            null,
            null,
            null
        )

        val saida = StringBuilder()

        while (registros.moveToNext()) {
            saida.append(registros.getString(NOME))
            saida.append("\n")
        }
        Toast.makeText(
            this,
            saida.toString(),
            Toast.LENGTH_LONG
        ).show()

        registros.close()
    }

    companion object {
        private const val DATABASE_NAME = "banco.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "cadastro"
        private const val ID = 0
        private const val NOME = 1
        private const val TELEFONE = 2
    }


}