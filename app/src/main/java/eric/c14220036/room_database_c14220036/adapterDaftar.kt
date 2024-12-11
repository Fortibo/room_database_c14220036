package eric.c14220036.room_database_c14220036

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import eric.c14220036.room_database_c14220036.database.daftarBelanja

class adapterDaftar (private val daftarBelanja: MutableList<daftarBelanja>) : RecyclerView.Adapter<adapterDaftar.ListViewHolder> () {
    private lateinit var onItemClickCallback : OnItemClickCallback

    interface OnItemClickCallback{
        fun delData (dtBelanja: daftarBelanja)
        fun selesai(dtBelanja: daftarBelanja)
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback= onItemClickCallback
    }
    class ListViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvItemText = itemView.findViewById<TextView>(R.id.itemText)
        var _tvItembarang= itemView.findViewById<TextView>(R.id.itemBarang)
        var _tvItemJumlah = itemView.findViewById<TextView>(R.id.itemJumlah)


        var _btEdit = itemView.findViewById<ImageView>(R.id.btnEdit)
        var _btDelete = itemView.findViewById<ImageView>(R.id.btnDelete)
        var _btnSelesai = itemView.findViewById<Button>(R.id.btnSelesai)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):adapterDaftar.ListViewHolder{
        val view : View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list,parent,false
        )
        return ListViewHolder(view)
    }


    override fun getItemCount(): Int {
        return  daftarBelanja.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var daftar = daftarBelanja[position]
        holder._tvItemText.setText(daftar.tanggal)
        holder._tvItembarang.setText(daftar.item)
        holder._tvItemJumlah.setText(daftar.jumlah)
        holder._btEdit.setOnClickListener {
            val intent = Intent(it.context, TambahDaftar::class.java)
            intent.putExtra("id",daftar.id)
            intent.putExtra("addEdit",1)
            it.context.startActivity(intent)
            notifyDataSetChanged()
        }
        holder._btDelete.setOnClickListener {
            onItemClickCallback.delData(daftar)
        }
        holder._btnSelesai.setOnClickListener {
            onItemClickCallback.selesai(daftar)
        }

    }
    fun isiData (daftar: List<daftarBelanja>){
        daftarBelanja.clear()
        daftarBelanja.addAll(daftar)
        notifyDataSetChanged()
    }
}