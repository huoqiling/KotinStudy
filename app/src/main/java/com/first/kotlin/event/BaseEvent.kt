package com.first.kotlin.event

/**
 *
 * EvenBus
 */
class BaseEvent {

    private var eventName: String? = ""
    private var objects: Any? = null

    constructor(eventName: String?, objects: Any?) {
        this.eventName = eventName
        this.objects = objects
    }

    fun getEventName(): String {
        return eventName!!
    }

    fun setEventName(eventName: String?) {
        this.eventName = eventName
    }

    fun getObject(): Any? {
        return objects
    }

    fun setObject(objects: Any) {
        this.objects = objects
    }

}