package com.electron.mfs.pg.account.web.rest;

import com.electron.mfs.pg.account.service.AccountBalanceService;
import com.electron.mfs.pg.account.web.rest.errors.BadRequestAlertException;
import com.electron.mfs.pg.account.service.dto.AccountBalanceDTO;

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
 * REST controller for managing {@link com.electron.mfs.pg.account.domain.AccountBalance}.
 */
@RestController
@RequestMapping("/api")
public class AccountBalanceResource {

    private final Logger log = LoggerFactory.getLogger(AccountBalanceResource.class);

    private static final String ENTITY_NAME = "accountManagerAccountBalance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountBalanceService accountBalanceService;

    public AccountBalanceResource(AccountBalanceService accountBalanceService) {
        this.accountBalanceService = accountBalanceService;
    }

    /**
     * {@code POST  /account-balances} : Create a new accountBalance.
     *
     * @param accountBalanceDTO the accountBalanceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountBalanceDTO, or with status {@code 400 (Bad Request)} if the accountBalance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-balances")
    public ResponseEntity<AccountBalanceDTO> createAccountBalance(@Valid @RequestBody AccountBalanceDTO accountBalanceDTO) throws URISyntaxException {
        log.debug("REST request to save AccountBalance : {}", accountBalanceDTO);
        if (accountBalanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountBalance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountBalanceDTO result = accountBalanceService.save(accountBalanceDTO);
        return ResponseEntity.created(new URI("/api/account-balances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /account-balances} : Updates an existing accountBalance.
     *
     * @param accountBalanceDTO the accountBalanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountBalanceDTO,
     * or with status {@code 400 (Bad Request)} if the accountBalanceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountBalanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-balances")
    public ResponseEntity<AccountBalanceDTO> updateAccountBalance(@Valid @RequestBody AccountBalanceDTO accountBalanceDTO) throws URISyntaxException {
        log.debug("REST request to update AccountBalance : {}", accountBalanceDTO);
        if (accountBalanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountBalanceDTO result = accountBalanceService.save(accountBalanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountBalanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /account-balances} : get all the accountBalances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountBalances in body.
     */
    @GetMapping("/account-balances")
    public List<AccountBalanceDTO> getAllAccountBalances() {
        log.debug("REST request to get all AccountBalances");
        return accountBalanceService.findAll();
    }

    /**
     * {@code GET  /account-balances/:id} : get the "id" accountBalance.
     *
     * @param id the id of the accountBalanceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountBalanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-balances/{id}")
    public ResponseEntity<AccountBalanceDTO> getAccountBalance(@PathVariable Long id) {
        log.debug("REST request to get AccountBalance : {}", id);
        Optional<AccountBalanceDTO> accountBalanceDTO = accountBalanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountBalanceDTO);
    }

    /**
     * {@code DELETE  /account-balances/:id} : delete the "id" accountBalance.
     *
     * @param id the id of the accountBalanceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-balances/{id}")
    public ResponseEntity<Void> deleteAccountBalance(@PathVariable Long id) {
        log.debug("REST request to delete AccountBalance : {}", id);
        accountBalanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
