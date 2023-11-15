package dompoo.predictAttendanceNumber.service;

import dompoo.predictAttendanceNumber.repository.NumberRepository;
import dompoo.predictAttendanceNumber.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RandomService {

    private final NumberRepository numberRepository;
    private final UserRepository userRepository;
}
