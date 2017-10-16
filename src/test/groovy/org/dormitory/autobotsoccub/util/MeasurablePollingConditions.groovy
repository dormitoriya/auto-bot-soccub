package org.dormitory.autobotsoccub.util

import spock.util.concurrent.PollingConditions

import java.time.Duration
import java.time.Instant

class MeasurablePollingConditions extends PollingConditions {

    static DEFAULT_PRECISION = Duration.ofSeconds(1)

    private PollingConditions pollingConditions

    private MeasurablePollingConditions(Map properties) {
        this.pollingConditions = new PollingConditions(properties)
    }

    static MeasurablePollingConditions with(Map properties) {
        return new MeasurablePollingConditions(properties)
    }

    void eventuallyTakes(Duration duration, Closure<?> conditions) {
        eventuallyTakes(duration, DEFAULT_PRECISION, conditions)
    }

    void eventuallyTakes(Duration duration, Duration precision, Closure<?> conditions) {
        def startTime = Instant.now()
        pollingConditions.eventually(conditions)
        def endTime = Instant.now()
        def spentTime = Duration.between(startTime, endTime)
        assert spentTime.compareTo(duration.minus(precision)) >= 0
        assert spentTime.compareTo(duration.plus(precision)) <= 0
    }
}
