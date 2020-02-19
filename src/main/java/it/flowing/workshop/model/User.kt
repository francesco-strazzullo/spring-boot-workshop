package it.flowing.workshop.model

data class User(@JvmField val id: UserId, @JvmField val name: String) {
    fun withId(id: UserId): User {
        return copy(id = id)
    }

    fun withName(name: String): User {
        return copy(name = name)
    }
}