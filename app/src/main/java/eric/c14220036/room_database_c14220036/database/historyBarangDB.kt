package eric.c14220036.room_database_c14220036.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database (entities = [historyBarang::class], version = 2)
    abstract class historyBarangDB :RoomDatabase() {
    abstract fun funhistoryBelanjaDAO() : historyBelanjaDAO

    companion object{
        @Volatile
        private var INSTANCE : historyBarangDB? = null

        @JvmStatic
        fun getDatabase(context: Context) : historyBarangDB{
            if(INSTANCE == null){
                synchronized(daftarBelanjaDB::class.java){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,historyBarangDB::class.java,"historyBarang_db"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE as historyBarangDB
        }
    }
}