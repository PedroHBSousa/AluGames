data class Game (val title: String, val poster: String) {
    var description:String? = null
    override fun toString(): String {
        return "Game(title='$title', poster='$poster', description='$description')"
    }
}