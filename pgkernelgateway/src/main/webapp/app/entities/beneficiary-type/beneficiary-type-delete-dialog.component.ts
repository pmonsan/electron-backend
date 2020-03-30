import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBeneficiaryType } from 'app/shared/model/beneficiary-type.model';
import { BeneficiaryTypeService } from './beneficiary-type.service';

@Component({
  selector: 'jhi-beneficiary-type-delete-dialog',
  templateUrl: './beneficiary-type-delete-dialog.component.html'
})
export class BeneficiaryTypeDeleteDialogComponent {
  beneficiaryType: IBeneficiaryType;

  constructor(
    protected beneficiaryTypeService: BeneficiaryTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.beneficiaryTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'beneficiaryTypeListModification',
        content: 'Deleted an beneficiaryType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-beneficiary-type-delete-popup',
  template: ''
})
export class BeneficiaryTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ beneficiaryType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BeneficiaryTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.beneficiaryType = beneficiaryType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/beneficiary-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/beneficiary-type', { outlets: { popup: null } }]);
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
