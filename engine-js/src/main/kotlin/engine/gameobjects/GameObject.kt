package engine.gameobjects

abstract class GameObject

class Sugar : GameObject()
class Apple : GameObject()
data class AntGameObject(val playerId: String, val id: String) : GameObject()
class Bug : GameObject()
