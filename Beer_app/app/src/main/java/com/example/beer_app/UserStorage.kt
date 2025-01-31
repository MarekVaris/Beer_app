import android.content.Context
import com.example.beer_app.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader
import java.io.FileWriter

object UserStorage {

    private const val FILE_NAME = "users.json"
    private val gson = Gson()

    private fun getFile(context: Context): File {
        return File(context.filesDir, FILE_NAME)
    }

    fun loadUsers(context: Context): MutableList<User> {
        val file = getFile(context)

        if (!file.exists()) {
            file.createNewFile()
            file.writeText("[]")
        }

        val reader = FileReader(file)
        val userListType = object : TypeToken<MutableList<User>>() {}.type
        val users: MutableList<User> = gson.fromJson(reader, userListType) ?: mutableListOf()
        reader.close()
        return users
    }

    fun saveUsers(context: Context, users: List<User>) {
        val file = getFile(context)
        val writer = FileWriter(file)
        writer.write(gson.toJson(users))
        writer.close()
    }

    fun isUserExists(context: Context, username: String): Boolean {
        val users = loadUsers(context)
        return users.any { it.username == username }
    }

    fun addUser(context: Context, newUser: User): Boolean {
        val users = loadUsers(context)

        if (users.any { it.username == newUser.username }) {
            return false
        }

        users.add(newUser)
        saveUsers(context, users)
        return true
    }
}
