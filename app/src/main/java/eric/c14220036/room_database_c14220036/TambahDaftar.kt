package eric.c14220036.room_database_c14220036

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import eric.c14220036.room_database_c14220036.database.daftarBelanja
import eric.c14220036.room_database_c14220036.database.daftarBelanjaDB
import eric.c14220036.room_database_c14220036.helper.DateHelper.getCurrentDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlin.math.tan

class TambahDaftar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_daftar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var iId :Int =0
        var iAddEdit : Int = 0

        iId = intent.getIntExtra("id",0)
        iAddEdit = intent.getIntExtra("addEdit",0)

        var DB = daftarBelanjaDB.getDatabase(this)
        var tanggal = getCurrentDate()
        val _etItem = findViewById<EditText>(R.id.etItem)
        val _etJumlah = findViewById<EditText>(R.id.etJumlah)
        val _btnTambh = findViewById<Button>(R.id.btnTambah)
        val _btnEdit = findViewById<Button>(R.id.btnEdit)

        if(iAddEdit == 0){
            _btnTambh.visibility = View.VISIBLE
            _btnEdit.visibility = View.GONE
            _etItem.isEnabled = true
        }
        else{
            _btnTambh.visibility = View.GONE
            _btnEdit.visibility = View.VISIBLE
            _etItem.isEnabled = false

            CoroutineScope(Dispatchers.IO).async {
                val item = DB.fundaftarBelanjaDAO().getItem(iId)
                _etItem.setText(item.item)
                _etJumlah.setText(item.jumlah)
            }
        }
        _btnTambh.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.fundaftarBelanjaDAO().insert(
                    daftarBelanja(
                        tanggal = tanggal,
                        item = _etItem.text.toString(),
                        jumlah = _etJumlah.text.toString(),
                    )
                )
            }
            finish()
        }
        _btnEdit.setOnClickListener {
            var item = _etJumlah.text.toString()
            CoroutineScope(Dispatchers.IO).async {
                DB.fundaftarBelanjaDAO().update(
                    isi_tanggal = tanggal,
                    isi_item = _etItem.text.toString(),
                    isi_jumlah = _etJumlah.text.toString(),
                    isi_status = 0,
                    pilih_id = iId
                )
            }
            finish()
        }
    }
}