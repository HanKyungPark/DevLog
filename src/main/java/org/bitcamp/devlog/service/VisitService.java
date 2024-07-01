package org.bitcamp.devlog.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Visit;
import org.bitcamp.devlog.mapper.VisitMapper;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class VisitService {

    private  final VisitMapper visitMapper;


}
