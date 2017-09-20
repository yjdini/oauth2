package com.oauth.controllers;

import com.oauth.data.entity.RegistedClient;
import com.oauth.data.jpa.RegistedClientRepository;
import com.oauth.util.MapBuilder;
import com.oauth.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by yjdini on 2017/9/7.
 *
 */

@RequestMapping("/api/back/")
@RestController
public class BackController {
    @Autowired
    RegistedClientRepository registedClientRepository;

    @RequestMapping(value = "/client/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map create (@RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        String clientId = (String) body.get("clientId");
        String allowedHosts = (String) body.get("allowedHosts");
        String clientSecret = (String) body.get("clientSecret");

        RegistedClient client = new RegistedClient();
        client.setName(name);
        client.setClientId(clientId);
        client.setAllowedHosts(allowedHosts);
        client.setClientSecret(clientSecret);
        registedClientRepository.save(client);
        return MapBuilder.ok().getMap();
    }

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public Map list () {
        List all = registedClientRepository.findAll();
        return MapBuilder.ok().put("result", all).getMap();
    }

    @RequestMapping(value = "/client/delete/{id}", method = RequestMethod.GET)
    public Map delete (@PathVariable Integer id) {
        registedClientRepository.delete(id);
        return MapBuilder.ok().getMap();
    }

    @RequestMapping(value = "/client/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map update (@RequestBody Map<String, Object> body) {

        String name = (String) body.get("name");
        String allowedHosts = (String) body.get("allowedHosts");
        String clientId = (String) body.get("clientId");
        String clientSecret = (String) body.get("clientSecret");
        Integer id = (Integer) body.get("id");

        RegistedClient client = registedClientRepository.findOne(id);
        client.setName(name);
        client.setClientId(clientId);
        client.setClientSecret(clientSecret);
        client.setAllowedHosts(allowedHosts);
        registedClientRepository.save(client);
        return MapBuilder.ok().getMap();
    }
}
