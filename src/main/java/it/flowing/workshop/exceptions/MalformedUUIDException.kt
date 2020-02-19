package it.flowing.workshop.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class MalformedUUIDException(cause: Throwable?) : IllegalArgumentException(cause)