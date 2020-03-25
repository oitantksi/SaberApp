package omega.isaacbenito.saberapp.authentication.entities

import com.google.gson.annotations.SerializedName

/**
 * Objecte que encapsula les dades d'un usuari per a registrar-se al servidor
 *
 * @author Isaac Benito
 * @property name nom de l'usuari
 * @property surname cognoms de l'usuari
 * @property nickname sobrenom de l'usuari
 * @property email correu electrònic de l'usuari
 * @property password contrassenya de l'usuari
 * @property school centre educatiu de l'usuari
 * @property role rol de l'usuari
 */
data class UserDto(
    var name: String,
    @SerializedName("cognom")
    var surname: String,
    var nickname: String,
    var email: String,
    var password: String,
    @SerializedName("center")
    var school: String,
    @SerializedName("rol")
    var role: Char
)