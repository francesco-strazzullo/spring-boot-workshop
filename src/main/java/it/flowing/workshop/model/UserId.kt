package it.flowing.workshop.model

import java.util.*

data class UserId private constructor(private val value: UUID) {
    companion object {
        @JvmStatic
        fun create(value: String): UserId {
            return UserId(UUID.fromString(value))
        }
    }

    override fun toString(): String {
        return value.toString();
    }
}