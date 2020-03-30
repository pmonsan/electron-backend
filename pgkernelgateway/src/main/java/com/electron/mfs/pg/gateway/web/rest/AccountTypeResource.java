package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.AccountTypeService;
import com.electron.mfs.pg.gateway.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.gateway.service.dto.AccountTypeDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.gateway.domain.AccountType}.
 */
@RestController
@RequestMapping("/api")
public class AccountTypeResource {

    private final Logger log = LoggerFactory.getLogger(AccountTypeResource.class);

    private static final String ENTITY_NAME = "accountType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountTypeService accountTypeService;

    public AccountTypeResource(AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    /**
     * {@code POST  /account-types} : Create a new accountType.
     *
     * @param accountTypeDTO the accountTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountTypeDTO, or with status {@code 400 (Bad Request)} if the accountType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-types")
    public ResponseEntity<AccountTypeDTO> createAccountType(@Valid @RequestBody AccountTypeDTO accountTypeDTO) throws URISyntaxException {
        log.debug("REST request to save AccountType : {}", accountTypeDTO);
        if (accountTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountTypeDTO result = accountTypeService.save(accountTypeDTO);
        return ResponseEntity.created(new URI("/api/account-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /account-types} : Updates an existing accountType.
     *
     * @param accountTypeDTO the accountTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountTypeDTO,
     * or with status {@code 400 (Bad Request)} if the accountTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-types")
    public ResponseEntity<AccountTypeDTO> updateAccountType(@Valid @RequestBody AccountTypeDTO accountTypeDTO) throws URISyntaxException {
        log.debug("REST request to update AccountType : {}", accountTypeDTO);
        if (accountTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountTypeDTO result = accountTypeService.save(accountTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /account-types} : get all the accountTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountTypes in body.
     */
    @GetMapping("/account-types")
    public List<AccountTypeDTO> getAllAccountTypes() {
        log.debug("REST request to get all AccountTypes");
        return accountTypeService.findAll();
    }

    /**
     * {@code GET  /account-types/:id} : get the "id" accountType.
     *
     * @param id the id of the accountTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-types/{id}")
    public ResponseEntity<AccountTypeDTO> getAccountType(@PathVariable Long id) {
        log.debug("REST request to get AccountType : {}", id);
        Optional<AccountTypeDTO> accountTypeDTO = accountTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountTypeDTO);
    }

    /**
     * {@code DELETE  /account-types/:id} : delete the "id" accountType.
     *
     * @param id the id of the accountTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-types/{id}")
    public ResponseEntity<Void> deleteAccountType(@PathVariable Long id) {
        log.debug("REST request to delete AccountType : {}", id);
        accountTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
