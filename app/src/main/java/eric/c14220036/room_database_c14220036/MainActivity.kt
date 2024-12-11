package eric.c14220036.room_database_c14220036

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import eric.c14220036.room_database_c14220036.database.daftarBelanja
import eric.c14220036.room_database_c14220036.database.daftarBelanjaDB
import eric.c14220036.room_database_c14220036.database.historyBarangDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var DB :daftarBelanjaDB
    private lateinit var DBH :historyBarangDB
    private lateinit var adapterDaftar : adapterDaftar
    private lateinit var adapterHistory : adapterDaftar
    private var arDaftar : MutableList<daftarBelanja> = mutableListOf()
    private var arHistory : MutableList<daftarBelanja> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        adapterDaftar = adapterDaftar(arDaftar)
        adapterHistory = adapterDaftar(arHistory)

        val _rvDaftar  = findViewById<RecyclerView>(R.id.rvDaftar)
        val _rvHistory = findViewById<RecyclerView>(R.id.rvHistory)

        DBH = historyBarangDB.getDatabase(this)
        DB = daftarBelanjaDB.getDatabase(this)

        val _fbAdd = findViewById<FloatingActionButton>(R.id.fbAdd)
        _fbAdd.setOnClickListener{
            startActivity(Intent(this,TambahDaftar::class.java))
        }
        _rvDaftar.layoutManager = LinearLayoutManager(this)
        _rvDaftar.adapter = adapterDaftar

        _rvHistory.layoutManager = LinearLayoutManager(this)
        _rvHistory.adapter = adapterHistory


        adapterDaftar.setOnItemClickCallback(
            object :adapterDaftar.OnItemClickCallback{
                override fun delData(dtBelanja: daftarBelanja) {
                    CoroutineScope(Dispatchers.IO).async{
                        DB.fundaftarBelanjaDAO().delete(dtBelanja)
                        val daftar = DB.fundaftarBelanjaDAO().selectAll()
                        withContext(Dispatchers.Main){
                            adapterDaftar.isiData(daftar)
                        }
                    }
                }

                override fun selesai(dtBelanja: daftarBelanja) {
                    CoroutineScope(Dispatchers.IO).async{
                        DB.fundaftarBelanjaDAO().delete(dtBelanja)
                        DBH.funhistoryBelanjaDAO().insert(dtBelanja)
                        val daftar = DB.fundaftarBelanjaDAO().selectAll()
                        val history = DBH.funhistoryBelanjaDAO().selectAll()
                        withContext(Dispatchers.Main){
                            adapterDaftar.isiData(daftar)
                            adapterHistory.isiData(history)
                        }
                    }
                }

            }
        )


    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).async {
            val daftarBelanja = DB.fundaftarBelanjaDAO().selectAll()
            val historyBarang = DBH.funhistoryBelanjaDAO().selectAll()
            adapterDaftar.isiData(daftarBelanja)
            adapterHistory.isiData(historyBarang)
            Log.d("data ROOM", daftarBelanja.toString())
            Log.d("data ROOM", historyBarang.toString())
        }
    }
}