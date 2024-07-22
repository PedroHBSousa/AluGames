import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.util.*

fun main() {
    val reader = Scanner(System.`in`)
    println("Digite um código para buscar: ")
    val search = reader.nextLine()

    val address = "https://www.cheapshark.com/api/1.0/games?id=$search"


    val client: HttpClient = HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder()
        .uri(URI.create(address))
        .build()
    val response = client.send(request, BodyHandlers.ofString())
    val gameDetails = response.body()

    var myGame:Game? = null

    val result = runCatching {
        val gson = Gson()
        val myGameInfo = gson.fromJson(gameDetails, GameInfo::class.java)
        myGame = Game(myGameInfo.info.title, myGameInfo.info.thumb)
    }

    result.onFailure{
        println("not found")
    }
    result.onSuccess {
        println("Deseja inserir uma descrição personalizada? S/N")
        val option = reader.nextLine()

        if (option.equals("s", true)) {
            println("insira a descrição a personalizada para o jogo: ")
            val description = reader.nextLine()
            myGame?.description = description
        } else {
            myGame?.description = myGame?.title
        }
        println(myGame)
    }

}