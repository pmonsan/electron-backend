package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.AgentTypeService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.AgentTypeDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.AgentType}.
 */
@RestController
@RequestMapping("/api")
public class AgentTypeResource {

    private final Logger log = LoggerFactory.getLogger(AgentTypeResource.class);

    private static final String ENTITY_NAME = "agentType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgentTypeService agentTypeService;

    public AgentTypeResource(AgentTypeService agentTypeService) {
        this.agentTypeService = agentTypeService;
    }

    /**
     * {@code POST  /agent-types} : Create a new agentType.
     *
     * @param agentTypeDTO the agentTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agentTypeDTO, or with status {@code 400 (Bad Request)} if the agentType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/agent-types")
    public ResponseEntity<AgentTypeDTO> createAgentType(@Valid @RequestBody AgentTypeDTO agentTypeDTO) throws URISyntaxException {
        log.debug("REST request to save AgentType : {}", agentTypeDTO);
        if (agentTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new agentType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgentTypeDTO result = agentTypeService.save(agentTypeDTO);
        return ResponseEntity.created(new URI("/api/agent-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /agent-types} : Updates an existing agentType.
     *
     * @param agentTypeDTO the agentTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agentTypeDTO,
     * or with status {@code 400 (Bad Request)} if the agentTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agentTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/agent-types")
    public ResponseEntity<AgentTypeDTO> updateAgentType(@Valid @RequestBody AgentTypeDTO agentTypeDTO) throws URISyntaxException {
        log.debug("REST request to update AgentType : {}", agentTypeDTO);
        if (agentTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AgentTypeDTO result = agentTypeService.save(agentTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agentTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /agent-types} : get all the agentTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agentTypes in body.
     */
    @GetMapping("/agent-types")
    public List<AgentTypeDTO> getAllAgentTypes() {
        log.debug("REST request to get all AgentTypes");
        return agentTypeService.findAll();
    }

    /**
     * {@code GET  /agent-types/:id} : get the "id" agentType.
     *
     * @param id the id of the agentTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agentTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/agent-types/{id}")
    public ResponseEntity<AgentTypeDTO> getAgentType(@PathVariable Long id) {
        log.debug("REST request to get AgentType : {}", id);
        Optional<AgentTypeDTO> agentTypeDTO = agentTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agentTypeDTO);
    }

    /**
     * {@code DELETE  /agent-types/:id} : delete the "id" agentType.
     *
     * @param id the id of the agentTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/agent-types/{id}")
    public ResponseEntity<Void> deleteAgentType(@PathVariable Long id) {
        log.debug("REST request to delete AgentType : {}", id);
        agentTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
