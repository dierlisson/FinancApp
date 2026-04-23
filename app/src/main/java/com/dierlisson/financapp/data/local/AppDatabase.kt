package com.dierlisson.financapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [ContaEntity::class, CategoriaEntity::class, TransacaoEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transacaoDao(): TransacaoDao
    // abstract fun contaDao(): ContaDao
    // abstract fun categoriaDao(): CategoriaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE transacoes ADD COLUMN observacao TEXT")
            }
        }

        // NOVO: Callback para popular o banco de dados na primeira vez que ele é criado
        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Inserindo uma Conta padrão (Isso será o ID 1)
                db.execSQL("INSERT INTO contas (nome, saldoAtual) VALUES ('Carteira Principal', 0.0)")

                // Inserindo Categorias padrão (Isso serão os IDs 1, 2 e 3)
                db.execSQL("INSERT INTO categorias (nome, tipo, icone) VALUES ('Alimentação', 'DESPESA', 0)")
                db.execSQL("INSERT INTO categorias (nome, tipo, icone) VALUES ('Salário', 'RECEITA', 0)")
                db.execSQL("INSERT INTO categorias (nome, tipo, icone) VALUES ('Lazer', 'DESPESA', 0)")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "controle_financeiro_db"
                )
                    .addMigrations(MIGRATION_1_2)
                    .addCallback(roomCallback) // Registrando nosso Callback aqui
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}