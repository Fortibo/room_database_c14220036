package eric.c14220036.room_database_c14220036

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import eric.c14220036.room_database_c14220036.adapterDaftar.OnItemClickCallback
import eric.c14220036.room_database_c14220036.database.daftarBelanja
import eric.c14220036.room_database_c14220036.database.historyBarang
import eric.c14220036.room_database_c14220036.database.historyBarangDB

class adapterHistory (private val historyBarang: MutableList<historyBarang>) : RecyclerView.Adapter<adapterHistory.ListViewHolder> () {
    private lateinit var onItemClickCallback : OnItemClickCallback

    interface OnItemClickCallback{
        fun delData (dtHistory: historyBarang)

    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback= onItemClickCallback
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapterHistory.ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(
            R.layout.history_list,parent,false
        )
        return ListViewHolder(view)
    }

    class ListViewHolder (itemView: View) :RecyclerView.ViewHolder(itemView) {
        var _tvItemText = itemView.findViewById<TextView>(R.id.itemText)
        var _tvItembarang= itemView.findViewById<TextView>(R.id.itemBarang)
        var _tvItemJumlah = itemView.findViewById<TextView>(R.id.itemJumlah)


//        var _btEdit = itemView.findViewById<ImageView>(R.id.btnEdit)
        var _btDelete = itemView.findViewById<ImageView>(R.id.btnDelete)
//        var _btnSelesai = itemView.findViewById<Button>(R.id.btnSelesai)
    }

    override fun onBindViewHolder(holder: adapterHistory.ListViewHolder, position: Int) {
        var history = historyBarang[position]
        holder._tvItemText.setText(history.tanggal)
        holder._tvItembarang.setText(history.item)
        holder._tvItemJumlah.setText(history.jumlah)

        holder._btDelete.setOnClickListener {
            onItemClickCallback.delData(history)
        }

    }

    override fun getItemCount(): Int {
        return historyBarang.size
    }
    fun isiData (history: List<historyBarang>){
        historyBarang.clear()
        historyBarang.addAll(history)
        notifyDataSetChanged()
    }
}