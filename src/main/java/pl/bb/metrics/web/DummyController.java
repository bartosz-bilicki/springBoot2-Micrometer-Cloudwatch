package pl.bb.metrics.web;

import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Timed
@RestController("/")
class DummyController {

    @GetMapping
    String dummy() {

        return "dummy";
    }
}
