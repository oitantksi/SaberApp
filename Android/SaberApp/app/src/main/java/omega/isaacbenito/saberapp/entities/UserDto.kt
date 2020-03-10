package omega.isaacbenito.saberapp.entities

data class UserDto (
    val name: String,
    val cognom: String,
    val nickname: String,
    val email: String,
    val password: String,
    val center: String,
    val rol: Char
){}