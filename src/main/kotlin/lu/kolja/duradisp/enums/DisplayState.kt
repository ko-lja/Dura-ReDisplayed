package lu.kolja.duradisp.enums

enum class DisplayState {
    DISABLED,
    ENABLED_PERCENTAGE,
    ENABLED_NUMBER,
    ENABLED_SCIENTIFIC;

    companion object {
        private var index = 0

        fun getNext(): DisplayState {
            index = (index + 1) % entries.size
            return entries[index]
        }
    }
}