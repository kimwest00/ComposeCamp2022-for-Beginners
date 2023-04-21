package com.example.collection

enum class Daypart{
    MORNING,
    AFTERNOON,
    EVENING,
}
data class Event(
    val title: String,
    val description: String? = null,
    val daypart: Daypart,
    val durationInMinutes: Int,
)
val Event.durationOfEvent:String
    get() = if(this.durationInMinutes < 60){
        "short"
    }else{
        "long"
    }
fun main(){
    var eventList = mutableListOf<Event>(
        Event(title = "Wake up", description = "Time to get up", daypart = Daypart.MORNING, durationInMinutes = 0),
        Event(title = "Eat breakfast", daypart = Daypart.MORNING, durationInMinutes = 15),
        Event(title = "Learn about Kotlin", daypart = Daypart.AFTERNOON, durationInMinutes = 30),
        Event(title = "Practice Compose", daypart = Daypart.AFTERNOON, durationInMinutes = 60),
        Event(title = "Watch latest DevBytes video", daypart = Daypart.AFTERNOON, durationInMinutes = 10),
        Event(title = "Check out latest Android Jetpack library", daypart = Daypart.EVENING, durationInMinutes = 45)
    )
    //1. daypart대로 collection 분리
    val groupEvent = eventList.groupBy {
        it.daypart
    }
    groupEvent.forEach{(daypart,event)->
        println("${daypart}:${event.size}")
    }
    //2. 60분보다 짧은 event list 반환및 시간순으로 정렬
    var filterEvent = eventList.filter {
        it.durationInMinutes<60
    }
    filterEvent.sortedBy {
        it.durationInMinutes
    }
    //3. 마지막 인덱스 출력
    print(eventList.last())
    //4. collection 확장하기

    println("Duration of first event of the day: ${eventList.last().durationOfEvent}")

}