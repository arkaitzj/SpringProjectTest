package com.test.health;

import org.springframework.stereotype.Component;

@Component
public class GeneralHealthService {
    public boolean areYouOk() {
        return true;
    }
}
