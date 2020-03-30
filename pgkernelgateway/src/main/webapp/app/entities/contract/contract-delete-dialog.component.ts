import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContract } from 'app/shared/model/contract.model';
import { ContractService } from './contract.service';

@Component({
  selector: 'jhi-contract-delete-dialog',
  templateUrl: './contract-delete-dialog.component.html'
})
export class ContractDeleteDialogComponent {
  contract: IContract;

  constructor(protected contractService: ContractService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.contractService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'contractListModification',
        content: 'Deleted an contract'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-contract-delete-popup',
  template: ''
})
export class ContractDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contract }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ContractDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.contract = contract;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/contract', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/contract', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
