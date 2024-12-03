package eric.c14220036.room_database_c14220036.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface daftarBelanjaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(daftar : daftarBelanja)

    @Query("UPDATE daftarBelanja SET tanggal=:isi_tanggal, item=:isi_item, status=:isi_status, jumlah=:isi_jumlah WHERE id=:pilih_id")
    fun update(isi_tanggal: String, isi_item: Int, isi_jumlah: String, isi_status: Int, pilih_id: Int)

    @Delete
    fun delete(daftar: daftarBelanja)

    @Query("SELECT * FROM daftarBelanja ORDER BY id asc")
    fun selectAll() : MutableList<daftarBelanja>
}
