package lu.kolja.duradisp.enums

enum class DisplayState(val configVal: String) {
    DISABLED("vanilla"),
    ENABLED_PERCENTAGE("percentage"),
    ENABLED_NUMBER("number"),
    ENABLED_SCIENTIFIC("scientific");

    companion object {
        private var index = 0

        fun getNext(): DisplayState {
            index = (index + 1) % entries.size
            return entries[index]
        }
    }
}