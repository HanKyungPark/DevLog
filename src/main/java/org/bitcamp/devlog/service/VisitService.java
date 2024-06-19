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

    public List<Visit> findAllByAccountId(String accountId, HttpSession session) {

        accountId= session.getId();
        List<Visit> visits = visitMapper.findAllByAccountId(accountId);

        return visits;
    };
    public Long findByAccountIdAndVisitDate(HttpSession session, String visitDate){
        Map<String,Object> map=new HashMap<>();
        map.put("accountId",session.getAttribute("accountId").toString());
        map.put("visitDate",visitDate);
        long visits= visitMapper.findByAccountIdAndVisitDate(map);

        return visits;
    };

}
