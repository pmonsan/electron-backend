import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContractOpposition } from 'app/shared/model/contract-opposition.model';
import { ContractOppositionService } from './contract-opposition.service';

@Component({
  selector: 'jhi-contract-opposition-delete-dialog',
  templateUrl: './contract-opposition-delete-dialog.component.html'
})
export class ContractOppositionDeleteDialogComponent {
  contractOpposition: IContractOpposition;

  constructor(
    protected contractOppositionService: ContractOppositionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.contractOppositionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'contractOppositionListModification',
        content: 'Deleted an contractOpposition'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-contract-opposition-delete-popup',
  template: ''
})
export class ContractOppositionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contractOpposition }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ContractOppositionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.contractOpposition = contractOpposition;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/contract-opposition', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/contract-opposition', { outlets: { popup: null } }]);
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
