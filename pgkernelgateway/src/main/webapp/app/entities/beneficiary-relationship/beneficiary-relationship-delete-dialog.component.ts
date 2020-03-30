import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBeneficiaryRelationship } from 'app/shared/model/beneficiary-relationship.model';
import { BeneficiaryRelationshipService } from './beneficiary-relationship.service';

@Component({
  selector: 'jhi-beneficiary-relationship-delete-dialog',
  templateUrl: './beneficiary-relationship-delete-dialog.component.html'
})
export class BeneficiaryRelationshipDeleteDialogComponent {
  beneficiaryRelationship: IBeneficiaryRelationship;

  constructor(
    protected beneficiaryRelationshipService: BeneficiaryRelationshipService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.beneficiaryRelationshipService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'beneficiaryRelationshipListModification',
        content: 'Deleted an beneficiaryRelationship'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-beneficiary-relationship-delete-popup',
  template: ''
})
export class BeneficiaryRelationshipDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ beneficiaryRelationship }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BeneficiaryRelationshipDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.beneficiaryRelationship = beneficiaryRelationship;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/beneficiary-relationship', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/beneficiary-relationship', { outlets: { popup: null } }]);
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
