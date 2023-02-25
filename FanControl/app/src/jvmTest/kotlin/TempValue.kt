import kotlin.random.Random

class TempValue {
    companion object {
        private var direction = true

        // emulate natural value
        fun newValue(value: Int): Int {
            val min = 30
            val max = 75
            val delta = Random.nextInt(0, 10)

            return if (direction) {
                (value + delta).let {
                    if (it > max) {
                        direction = false
                        max
                    } else it
                }
            } else {
                (value - delta).let {
                    if (it < min) {
                        direction = true
                        min
                    } else it
                }
            }
        }
    }
}