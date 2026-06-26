package com.marcioposgraduacao.usandodb_pos.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import android.widget.Toast.makeText
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.marcioposgraduacao.usandodb_pos.entity.Cadastro
import kotlinx.coroutines.tasks.await

class DatabaseHandler(context: Context) {

    private val banco = Firebase.firestore

    companion object {
        private const val TABLE_NAME = "cadastro"
        private const val ID = "id"
        private const val NOME = 1
        private const val TELEFONE = 2
    }

    suspend fun incluir(cadastro: Cadastro) {

        val registro = hashMapOf(
            NOME to cadastro.nome,
            TELEFONE to cadastro.telefone
        )

        banco
            .collection("Cadastramento")
            .document(cadastro.id.toString())
            .set(registro)
            .await()

    }

    suspend fun alterar(cadastro: Cadastro) {
        val registro = hashMapOf(
            NOME to cadastro.nome,
            TELEFONE to cadastro.telefone
        )

        banco
            .collection(TABLE_NAME)
            .document(cadastro.id.toString())
            .set(registro)
            .await()

    }
    suspend fun excluir(id: Int) {

        banco
            .collection("Cadastramento")
            .document(id.toString())
            .delete()
            .await()
    }
    suspend fun pesquisar(id: Int): Cadastro? {

        val documento: DocumentSnapshot = banco
            .collection(TABLE_NAME)
            .document(id.toString())
            .get()
            .await()

        if (documento.exists()) {
            val cadastro = Cadastro(
                id,
                documento.get(NOME).toString(),
                documento.get(TELEFONE).toString()
            )
            return cadastro
        } else {
            return null
        }
    }
    suspend fun listar(): MutableList<Cadastro> {

        val documentos: QuerySnapshot = banco
            .collection(TABLE_NAME)
            .get()
            .await()

        val lista: MutableList<Cadastro> = mutableListOf<Cadastro>()

        for (documento: QueryDocumentSnapshot in documentos){
            val cadastro = Cadastro(
                documento.id.toInt(),
                documento.get("nome").toString(),
                documento.get("telefone").toString()
            )
            lista.add(cadastro)
        }

        return lista
    }

} // Fim DatabaseHandler