package it.flowing.workshop.model

data class User(@JvmField val id: UserId, @JvmField val name: String) {
    fun withId(id: UserId): User {
        return User(id, name)
    }

    fun withName(name: String): User {
        return User(id, name)
    }
}