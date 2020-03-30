import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBeneficiary } from 'app/shared/model/beneficiary.model';
import { BeneficiaryService } from './beneficiary.service';

@Component({
  selector: 'jhi-beneficiary-delete-dialog',
  templateUrl: './beneficiary-delete-dialog.component.html'
})
export class BeneficiaryDeleteDialogComponent {
  beneficiary: IBeneficiary;

  constructor(
    protected beneficiaryService: BeneficiaryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.beneficiaryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'beneficiaryListModification',
        content: 'Deleted an beneficiary'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-beneficiary-delete-popup',
  template: ''
})
export class BeneficiaryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ beneficiary }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BeneficiaryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.beneficiary = beneficiary;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/beneficiary', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/beneficiary', { outlets: { popup: null } }]);
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
