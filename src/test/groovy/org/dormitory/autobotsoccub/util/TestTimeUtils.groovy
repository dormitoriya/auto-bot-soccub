package org.dormitory.autobotsoccub.util

import java.time.Instant

class TestTimeUtils {

    static boolean isNotBefore(Instant start, Instant end) {
        return start.compareTo(end) >= 0
    }

    static boolean isNotAfter(Instant start, Instant end) {
        return start.compareTo(end) <= 0
    }
}
