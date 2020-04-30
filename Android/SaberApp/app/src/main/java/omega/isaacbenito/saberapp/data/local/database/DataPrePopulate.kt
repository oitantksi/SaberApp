package omega.isaacbenito.saberapp.data.local.database

import omega.isaacbenito.saberapp.data.entities.Materia
import omega.isaacbenito.saberapp.data.entities.Pregunta
import omega.isaacbenito.saberapp.data.entities.User
import java.util.*

/**
 * Dades per a desar a la base de dades en la primera arrencada de l'aplicació en cas que sigui
 * necessàri per a realitzar els testos.
 *
 * @author Isaac Benito
 **/
class DataPrePopulate {


    companion object {

        private val cal = Calendar.getInstance()

        fun getPrepopulateMateries(): List<Materia> {
            return listOf(
                Materia(1, "Geografia"),
                Materia(2, "Història"),
                Materia(3, "Art"),
                Materia(4, "Matemátiques")
            )
        }

        fun getPrepopulatePreguntes(): List<Pregunta> {
            return listOf(
                Pregunta(
                    1,
                    "País amb més habitants del món",
                    "EEUU",
                    "India",
                    "Xina",
                    "Russia",
                    3,
                    cal.apply { add(Calendar.DATE, 0) }.time,
                    1
                ),
                Pregunta(
                    2,
                    "Com s'anomenen les línies imaginàries que tallen perpendicularment l'eix de rotació de la Terra",
                    "Paral·lels",
                    "Latitud",
                    "Hemisferis",
                    "Meridians",
                    1,
                    cal.apply { add(Calendar.DATE, -2) }.time,
                    1
                ),
                Pregunta(
                    4,
                    "Quina és la capital del Marroc?",
                    "Marrakech",
                    "Casablanca",
                    "Fez",
                    "Rabat",
                    4,
                    cal.apply { add(Calendar.DATE, +7) }.time,
                    1
                ),
                Pregunta(
                    5,
                    "Els tres bens natural Patrimoni de la Humanitat d'Espanya són...",
                    "Garajonay, Teide i Doñana",
                    "Roquenublo, Cabo de Gata i Monte Perdido",
                    "Doñana, Muniellos y Cabañeros",
                    "Illa de El Hierro, Tablas de Daimiel y Teide",
                    1,
                    cal.apply { add(Calendar.DATE, +11) }.time,
                    1
                ),
                Pregunta(
                    6,
                    "Cuantes llengúes oficials té Espanya?",
                    "1",
                    "3",
                    "4",
                    "5",
                    4,
                    cal.apply { add(Calendar.DATE, -4) }.time,
                    1
                ),
                Pregunta(
                    7,
                    "Com s'anomena el riu més llarg de la península ibèrica?",
                    "Guadiana",
                    "Tajo",
                    "Ebre",
                    "Guadalquivir",
                    2,
                    cal.apply { add(Calendar.DATE, -7) }.time,
                    1
                ),
                Pregunta(
                    8,
                    "On es troba el principal jaciment d'hominids de la peninsula ibèrica?",
                    "Santimamiñe",
                    "Atapuerca",
                    "Torralba",
                    "Ambrona",
                    2,
                    cal.apply { add(Calendar.DATE, +1) }.time,
                    2
                ),
                Pregunta(
                    9,
                    "Quin general cartaginès va envair Italia durant la 2a Guerra Púnica, fins la seva derrota definitiva l'any 201 a.c.?",
                    "Anibal",
                    "Atila",
                    "Alexandre Magne",
                    "Genghis Khan",
                    1,
                    cal.apply { add(Calendar.DATE, -3) }.time,
                    2
                ),
                Pregunta(
                    10,
                    "Quina va ser la última faraona d'Egipte, que va governar fins l'any 30 a.c.?",
                    "Nefertiti",
                    "Isis",
                    "Eneida",
                    "Cleopatra",
                    4,
                    cal.apply { add(Calendar.DATE, +4) }.time,
                    2
                ),
                Pregunta(
                    11,
                    "Quins nom van posar els romans als deus gracs Zeus, Afrodita i Ares?",
                    "Jupiter, Minerva i Hades",
                    "Júpiter, Venus i Mart",
                    "Saturn, Venus i Hades",
                    "Saturn, Minerva i Mart",
                    2,
                    cal.apply { add(Calendar.DATE, +10) }.time,
                    2
                ),
                Pregunta(
                    12,
                    "En quin any va tindre lloc la batalla de Trafalgar?",
                    "1795",
                    "1590",
                    "1610",
                    "1805",
                    4,
                    cal.apply { add(Calendar.DATE, -6) }.time,
                    2
                ),
                Pregunta(
                    13,
                    "Quins són els 8 primers elements de la successió de Fibonacci?",
                    "0, 2, 1, 4, 3, 6, 5, 8",
                    "1, 3, 5, 7, 9, 11, 13, 15",
                    "0, -1, 1, -2, 2, -3, 3, -4",
                    "0, 1, 1, 2, 3, 5, 8, 13",
                    4,
                    cal.apply { add(Calendar.DATE, +2) }.time,
                    4
                ),
                Pregunta(
                    14,
                    "Quin és el cosinus d'un angle de 90 graus",
                    "0",
                    "1/2",
                    "1",
                    "-1",
                    1,
                    cal.apply { add(Calendar.DATE, -1) }.time,
                    4
                ),
                Pregunta(
                    15,
                    "Quin és el valor aproximat del número \"e\"?",
                    "5,445361",
                    "1,142565",
                    "2,718282",
                    "3,141598",
                    3,
                    cal.apply { add(Calendar.DATE, +6) }.time,
                    4
                ),
                Pregunta(
                    16,
                    "Amb 39 litres de gasolina el marcador d'un cotxe senyala 3/4 de dipòsit. Quina és la capacitat total del dipòsit?",
                    "49 litres",
                    "54 litres",
                    "52 litres",
                    "55 litres",
                    3,
                    cal.apply { add(Calendar.DATE, +8) }.time,
                    4
                ),
                Pregunta(
                    3,
                    "Què diu el teorema del sinus?",
                    "En els tiangles equilaters, la longitud dels seus costats és proporcional als sinus dels angles oposats",
                    "En tot triangle, la longitud dels seus costats és proporcional als sinus dels angles oposats",
                    "En tot triangle, la longitud dels seus costats és proporcional als cosinus dels angles oposats",
                    "En els triagles rectangles, la longitud dels seus costats és proporcional als cosinus dels angles oposats",
                    2,
                    cal.apply { add(Calendar.DATE, -5) }.time,
                    4
                )
            )
        }


        fun getPrepopulateUsers(): List<User> {
            return listOf(
                User(5, "Joan", "Test User", "joan", "joan@omega.org", "IES JOAN MARAGALL", 'A'),
                User(6, "Maria", "Test User", "maria", "maria@omega.org", "IES JOAN MARAGALL", 'A'),
                User(7, "Carla", "Test User", "carla", "carla@omega.org", "IES JOAN MARAGALL", 'A'),
                User(8, "Josep Maria", "Test User", "jm", "jm@omega.org", "IES JOAN MARAGALL", 'A'),
                User(9, "Nora", "Test User", "nora", "nora@omega.org", "IES JOAN MARAGALL", 'A'),
                User(10, "Aura", "Test User", "aura", "aura@omega.org", "IES JOAN MARAGALL", 'A'),
                User(
                    11,
                    "Albert",
                    "Test User",
                    "albert",
                    "albert@omega.org",
                    "IES JOAN MARAGALL",
                    'A'
                ),
                User(12, "Lara", "Test User", "lara", "lara@omega.org", "IES JOAN MARAGALL", 'A'),
                User(13, "marc", "Test User", "marc", "marc@omega.org", "IES JOAN MARAGALL", 'A'),
                User(14, "agnes", "Test User", "agnes", "agnes@omega.org", "IOC", 'A'),
                User(15, "marta", "Test User", "marta", "marta@omega.org", "IOC", 'A'),
                User(16, "ignasi", "Test User", "ignasi", "ignasi@omega.org", "IOC", 'A'),
                User(17, "Jordina", "Test User", "jordina", "jordina@omega.org", "IOC", 'A'),
                User(18, "adria", "Test User", "adria", "adria@omega.org", "IOC", 'A'),
                User(19, "laila", "Test User", "laila", "laila@omega.org", "IOC", 'A'),
                User(20, "patricia", "Test User", "patricia", "patricia@omega.org", "IOC", 'A'),
                User(21, "manel", "Test User", "manel", "manel@omega.org", "IOC", 'A'),
                User(22, "dani", "Test User", "dani", "dani@omega.org", "IOC", 'A'),
                User(23, "oscar", "Test User", "oscar", "oscar@omega.org", "IOC", 'A'),
                User(24, "kathy", "Test User", "kathy", "kathy@omega.org", "IES JAUME BALMES", 'A'),
                User(
                    25,
                    "montse",
                    "Test User",
                    "montse",
                    "montse@omega.org",
                    "IES JAUME BALMES",
                    'A'
                ),
                User(26, "anna", "Test User", "anna", "anna@omega.org", "IES JAUME BALMES", 'A'),
                User(
                    27,
                    "miquel",
                    "Test User",
                    "miquel",
                    "miquel@omega.org",
                    "IES JAUME BALMES",
                    'A'
                ),
                User(28, "david", "Test User", "david", "david@omega.org", "IES JAUME BALMES", 'A'),
                User(
                    29,
                    "agusti",
                    "Test User",
                    "agusti",
                    "agusti@omega.org",
                    "IES JAUME BALMES",
                    'A'
                ),
                User(
                    30,
                    "esther",
                    "Test User",
                    "esther",
                    "esther@omega.org",
                    "IES JAUME BALMES",
                    'A'
                ),
                User(31, "pere", "Test User", "pere", "pere@omega.org", "IES JAUME BALMES", 'A'),
                User(32, "homer", "Test User", "homer", "homer@omega.org", "IES JAUME BALMES", 'A'),
                User(33, "mar", "Test User", "mar", "mar@omega.org", "IES JAUME BALMES", 'A')
            )
        }

        //    fun getPrepopulateAnswers(): List<Resposta> {
        //        return listOf(
        //            Resposta(1, 4, 2, 3),
        //            Resposta(2, 10, 2, 2),
        //            Resposta(3, 4, 3, 2),
        //            Resposta(4, 11, 14, 1),
        //            Resposta(5, 12, 12, 2),
        //            Resposta(6, 10, 9, 4)
        //        )
        //    }
    }
}